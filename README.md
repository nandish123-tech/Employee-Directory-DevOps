# Employee Directory REST API – DevOps Pipeline

A production-grade Employee Directory REST API developed with Java 17 and Spring Boot, demonstrating a complete end-to-end DevOps lifecycle covering Continuous Integration (Maven, Jenkins, JUnit 5) and Configuration Management / Container Orchestration (Ansible, Docker, Kubernetes/Minikube).

## Project Overview

- **Course:** DevOps (24CYAEC59)
- **Institution:** M. S. Ramaiah Institute of Technology
- **Repository:** https://github.com/nandish123-tech/Employee-Directory-DevOps.git

## Tech Stack

- **Language & Framework:** Java 17, Spring Boot 3.3.5
- **Build Automation:** Maven 3.9.9 (with Maven Wrapper `mvnw`)
- **Continuous Integration:** Jenkins CI & GitHub Actions
- **Testing:** JUnit 5, MockMvc, AssertJ
- **Configuration Management:** Ansible
- **Containerization:** Multi-stage Dockerfile
- **Orchestration:** Kubernetes (Minikube)

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/` | Application and pod metadata (app name, version, host) |
| `GET` | `/employees` | Retrieve all employee records |
| `GET` | `/employees/{id}` | Retrieve a specific employee by ID |
| `POST` | `/employees` | Add a new employee (validates name, department, email) |
| `GET` | `/actuator/health` | Health and readiness/liveness status |
