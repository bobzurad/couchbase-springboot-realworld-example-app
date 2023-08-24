#!/usr/bin/env bash

sync_gateway_config_file_path=/tmp/config/config-gateway.json
#couchbase_server_url=`cat $sync_gateway_config_file_path | grep '"server":' | grep -o '"http://.*"' | sed 's/"//g'`
couchbase_server_url="http://couchbase:8091"

echo $sync_gateway_config_file_path
echo $couchbase_server_url
echo $couchbase_server_url/pools/default/buckets

while ! { curl -X GET -u"Administrator:password" $couchbase_server_url/pools/default/buckets -H "accept: application/json" -s | grep -q '"status":"healthy"'; }; do
  echo "Waiting...."
  sleep 10s
done

/entrypoint.sh $sync_gateway_config_file_path
