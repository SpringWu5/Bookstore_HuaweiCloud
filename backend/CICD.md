# CI/CD Documentation

This document explains the CI/CD setup for the Campus Mall Backend project.

## Overview

The project uses GitHub Actions for continuous integration and deployment. The following workflows are configured:

1. **CI Workflow** - Builds and tests the application on every push and pull request
2. **Test PR Workflow** - Runs tests with database services for pull requests
3. **Deployment Workflow** - Deploys to production when a tag is created
4. **Docker Workflow** - Builds and pushes Docker images to DockerHub

## Workflows

### 1. CI Workflow (`.github/workflows/ci.yml`)

This workflow runs on:
- Every push to `master`/`main` branch
- Every pull request to `master`/`main` branch

It performs:
- Maven build
- Unit and integration tests
- Test report generation

### 2. Test PR Workflow (`.github/workflows/test-pr.yml`)

This workflow runs on:
- Every pull request to `master`/`main` branch

It includes:
- MySQL and Redis services setup
- Full application tests with database
- Test report generation

### 3. Deployment Workflow (`.github/workflows/deploy.yml`)

This workflow runs on:
- Tag creation (e.g., `v1.0.0`)

It performs:
- Maven packaging
- Deployment to production server via SSH
- Application restart
- Health check
- Slack notifications

### 4. Docker Workflow (`.github/workflows/docker.yml`)

This workflow runs on:
- Tag creation (e.g., `v1.0.0`)

It builds and pushes Docker images to DockerHub.

## Required Secrets

The following secrets need to be configured in GitHub repository settings:

### For Deployment

- `HOST` - Production server hostname/IP
- `USERNAME` - SSH username
- `KEY` - SSH private key
- `SLACK_WEBHOOK_URL` - Slack webhook for notifications (optional)

### For Docker

- `DOCKERHUB_USERNAME` - DockerHub username
- `DOCKERHUB_TOKEN` - DockerHub access token

## Deployment Process

1. Create and push a tag in the format `v*` (e.g., `v1.0.0`)
2. The deployment workflow will automatically trigger
3. The application JAR is uploaded to the server
4. Old application is stopped
5. New application is started
6. Health check is performed
7. Notification is sent (if configured)

## Environment Configuration

The application uses different profiles:
- `dev` - Development environment (default)
- `test` - Test environment (for PR workflows)
- `prod` - Production environment (for deployments)

Configuration is managed through application.yml files in `second-server/src/main/resources/`.

## Docker Configuration

The application can be built as a Docker image using the provided Dockerfile. The image:
- Uses OpenJDK 8 JRE Alpine
- Exposes port 8080
- Includes health check
- Sets timezone to Asia/Shanghai

## Troubleshooting

### Workflow fails during tests
- Check that all required services (MySQL, Redis) are running
- Verify database schema is up-to-date
- Check application configuration

### Deployment fails
- Verify SSH access to the production server
- Check that the server has enough disk space
- Ensure the application has proper permissions to run
- Review application logs in `/home/deployer/campus-mall-app/logs/`

## Best Practices

- Always create pull requests for code changes
- Use semantic versioning for tags (e.g., v1.0.0)
- Test changes in a staging environment when possible
- Monitor application logs after deployments
- Keep secrets secure and rotate them regularly