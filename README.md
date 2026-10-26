# Spring Boot Jenkins Midterm

A small Java 21 / Spring Boot 3.5.7 API demonstrating a Maven executable fat JAR and a Jenkins pipeline sourced from GitHub.

## Build and run

```sh
mvn -B -ntp clean verify
java -jar target/springboot-midterm-1.0.0.jar
curl 'http://localhost:8080/api/greeting?name=Jenkins'
curl http://localhost:8080/api/health
```

Three MVC tests cover the default greeting, a personalized greeting, and the health endpoint. `sh scripts/smoke-test.sh` also starts the packaged JAR on port 18081 and checks its HTTP responses.

## Dockerized Jenkins

```sh
docker compose up -d --build
docker compose exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```

Open http://localhost:8081, complete Jenkins setup, and create an administrator account. The custom Jenkins image includes Maven, curl, Java 21, and the pipeline/Git/GitHub/test-report plugins. The container stores Jenkins data in a named volume. No host Docker socket is mounted. A single Jenkins container executes this small trusted classroom pipeline; production systems should use separate build agents.

## GitHub and pipeline configuration

1. Create a public GitHub repository and push this project's source, including `Jenkinsfile`.
2. Create a Jenkins **Pipeline** job.
3. Under Pipeline, choose **Pipeline script from SCM**; SCM: **Git**.
4. Enter the public repository's HTTPS clone URL; credentials: **none** for public read access.
5. Set Branch Specifier to `*/main`, Script Path to `Jenkinsfile`, and save.
6. Select **Build Now**. Verify Checkout, Test, Build Fat JAR, Smoke Test, and Archive succeed.
7. Open test results and archived `springboot-midterm-1.0.0.jar`.

GitHub integration here uses Jenkins Git SCM checkout of a public repository. Manual Build Now requires no webhook or GitHub token. A webhook is optional and needs an appropriately reachable, authenticated Jenkins setup; do not expose a local Jenkins controller just to demonstrate a webhook.

## Evidence to capture from the actual run

- Public GitHub repository page and commit details.
- `docker compose ps` showing the running Jenkins container.
- Pipeline configuration showing SCM type, repository URL, branch, and script path.
- Successful pipeline stage view and console output showing Spring Boot repackaging.
- Test report and archived JAR; JAR contents showing `BOOT-INF/classes` and `BOOT-INF/lib`.

The report must include the assignment's original questions. Only describe completed actions as completed and use screenshots from the actual environment.
