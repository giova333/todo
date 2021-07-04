#!/bin/bash

set -e

if [ -z "$1" ]
  then
    echo "No task supplied"
  else
    curl --header "Content-Type: application/json" \
    --request POST \
    --data '{"name":"'"$1"'"}' \
     http://localhost:8080/api/task
fi