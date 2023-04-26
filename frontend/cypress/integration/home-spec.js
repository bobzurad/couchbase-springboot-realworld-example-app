// enables intelligent code completion for Cypress commands
// https://on.cypress.io/intelligent-code-completion
/// <reference types="cypress" />
/// <reference types="@testing-library/cypress" />

describe('Home page', () => {
  beforeEach(() => {
    cy.intercept('**/articles?**')
      .as('getAllArticles')
      .intercept('**/tags')
      .as('getAllTags')
      .visit('/');
  });

  it('should show the app name', () => {
    cy.get('.banner').within(() => {
      cy.findByRole('heading', { level: 1, name: /conduit/i }).should(
        'be.visible'
      );

      cy.findByText('A place to share your knowledge.').should('be.visible');
    });
  });

  it('should have a header navbar', () => {
    cy.findByRole('navigation').within(() => {
      cy.findByRole('link', { name: /conduit/i }).should(
        'have.attr',
        'href',
        '/'
      );

      cy.findByRole('link', { name: /home/i }).should('have.attr', 'href', '/');

      cy.findByRole('link', { name: /sign in/i }).should(
        'have.attr',
        'href',
        '/login'
      );
      cy.findByRole('link', { name: /sign up/i }).should(
        'have.attr',
        'href',
        '/register'
      );
    });
  });

  // it('should render the list of articles', () => {
  //   cy.findByRole('button', { name: /global feed/i }).should('be.visible');

  //   cy.wait('@getAllArticles')
  //     .its('response.body')
  //     .then((body) => {
  //       cy.get('.article-preview').should('have.length', body.articles.length);

  //       Cypress._.each(body.articles, (article, index) => {
  //         cy.get('.article-preview')
  //           .eq(index)
  //           .within(() => {
  //             cy.findByRole('img', { name: article.author.username });

  //             cy.findAllByText(article.author.username);

  //             cy.findByRole('heading').should('have.text', article.title);

  //             cy.get('p').should('have.text', article.description);

  //             cy.findByRole('list')
  //               .children()
  //               .should('have.length', article.tagList.length);

  //             cy.findByRole('list').within(() => {
  //               Cypress._.each(article.tagList, (tag) => {
  //                 cy.findAllByText(tag);
  //               });
  //             });
  //           });
  //       });
  //     });
  // });

  it('should render the list of tags', () => {
    cy.wait('@getAllTags')
      .its('response.body')
      .then((body) => {
        cy.get('.sidebar').within(() => {
          cy.findByText('Popular Tags');

          cy.findAllByRole('button').should('have.length', body.tags.length);
        });
      });
  });

});

