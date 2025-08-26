@echo off
echo Spring Boot REST API Template Runner

if "%1"=="" (
    echo Running with default profile
    mvn spring-boot:run
) else if "%1"=="dev" (
    echo Running with development profile
    mvn spring-boot:run -Dspring.profiles.active=dev
) else if "%1"=="prod" (
    echo Running with production profile
    mvn spring-boot:run -Dspring.profiles.active=prod
) else (
    echo Unknown profile: %1
    echo Usage: run.bat [dev|prod]
    exit /b 1
)
