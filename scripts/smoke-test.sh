#!/bin/sh
set -eu
jar_file=target/springboot-midterm-1.0.0.jar
java -jar "$jar_file" --server.port=18081 > target/smoke-test.log 2>&1 &
app_pid=$!
trap 'kill "$app_pid" 2>/dev/null || true' EXIT INT TERM
attempt=0
until curl --fail --silent http://127.0.0.1:18081/api/health > target/health.json; do
    attempt=$((attempt + 1))
    if [ "$attempt" -ge 60 ] || ! kill -0 "$app_pid" 2>/dev/null; then
        cat target/smoke-test.log
        exit 1
    fi
    sleep 1
done
grep '"status":"UP"' target/health.json
curl --fail --silent 'http://127.0.0.1:18081/api/greeting?name=Jenkins' > target/greeting.json
grep 'Hello, Jenkins!' target/greeting.json
printf '\nExecutable fat JAR smoke test passed.\n'
