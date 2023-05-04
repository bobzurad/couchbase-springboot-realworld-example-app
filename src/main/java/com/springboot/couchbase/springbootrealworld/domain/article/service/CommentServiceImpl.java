package com.springboot.couchbase.springbootrealworld.domain.article.service;

import com.springboot.couchbase.springbootrealworld.domain.article.dto.CommentDto;
import com.springboot.couchbase.springbootrealworld.domain.article.entity.ArticleDocument;
import com.springboot.couchbase.springbootrealworld.domain.article.entity.CommentDocument;
import com.springboot.couchbase.springbootrealworld.domain.article.repository.ArticleRepository;
import com.springboot.couchbase.springbootrealworld.domain.article.repository.CommentRepository;
import com.springboot.couchbase.springbootrealworld.domain.profile.dto.ProfileDto;
import com.springboot.couchbase.springbootrealworld.domain.profile.service.ProfileService;
import com.springboot.couchbase.springbootrealworld.domain.user.entity.UserDocument;
import com.springboot.couchbase.springbootrealworld.security.AuthUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ProfileService profileService;


    @Transactional
    @Override
    public CommentDto addCommentsToAnArticle(String slug, CommentDto comment, AuthUserDetails authUserDetails) {
        ArticleDocument articleDocument = articleRepository.findBySlug(slug);
        CommentDocument commentDocument = CommentDocument.builder()
                .id(comment.getId())
                .body(comment.getBody())
                .author(UserDocument.builder()
                        .id(authUserDetails.getId())
                        .build())
                .article(articleDocument)
                .build();
        commentRepository.save(commentDocument);

        return convertToDTO(authUserDetails, commentDocument);
    }

    @Override
    public List<CommentDto> getCommentsBySlug(String slug, AuthUserDetails authUserDetails) {

            String articleId = articleRepository.findBySlug(slug).getId();
            List<CommentDocument> commentEntities = commentRepository.findByArticleIdOrderByCreatedAtDesc(articleId);
            return commentEntities.stream().map(commentEntity -> convertToDTO(authUserDetails, commentEntity)).collect(Collectors.toList());

    }

    @Transactional
    @Override
    public void delete(String commentId, AuthUserDetails authUserDetails) {
        List<CommentDocument> commentEntity = commentRepository.findById(commentId);
        commentRepository.deleteAll(commentEntity);
    }

    private CommentDto convertToDTO(AuthUserDetails authUserDetails, CommentDocument commentDocument) {
        ProfileDto author = profileService.getProfileByUserId(commentDocument.getAuthor().getEmail(), authUserDetails);
        return CommentDto.builder()
                .id(commentDocument.getId())
                .createdAt(commentDocument.getCreatedAt())
                .updatedAt(commentDocument.getUpdatedAt())
                .body(commentDocument.getBody())
                .author(author)
                .build();
    }


    private CommentDto convertToDTOs( CommentDocument commentDocument) {
//        ProfileDto author = profileService.getProfileByUserId(commentDocument.getAuthor().getEmail());
        return CommentDto.builder()
                .id(commentDocument.getId())
                .createdAt(commentDocument.getCreatedAt())
                .updatedAt(commentDocument.getUpdatedAt())
                .body(commentDocument.getBody())
//                .author(author)
                .build();
    }

}
