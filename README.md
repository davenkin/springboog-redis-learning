### Introduction

- This is a simple Spring Boot web project with redis cache using Jackson.

### How to run

- First run `./start-docker-compose.sh` to start redis server
- Then run `./mvnw spring-boot:run` and open `http://localhost:8080/hello-world`
- Or run `main()` in `SpringBootWebApplication` in IDE directly

### Tech stack

- Spring Boot 3.3.2
- Spring Data Redis 3.3.2