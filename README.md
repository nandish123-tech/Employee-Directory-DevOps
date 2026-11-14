# Employee Directory REST API – Complete DevOps Implementation

[![Continuous Integration](https://github.com/nandish123-tech/Employee-Directory-DevOps/actions/workflows/ci.yml/badge.svg)](https://github.com/nandish123-tech/Employee-Directory-DevOps/actions/workflows/ci.yml)
[![Java 17](https://img.shields.io/badge/Java-17-blue.svg)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

An end-to-end, production-oriented DevOps implementation for an **Employee Directory REST API** developed with Java 17 and Spring Boot. This repository demonstrates the complete lifecycle of modern software delivery across two primary evaluation components:

1. **Continuous Integration (CI):** Git branching & PR workflows, Maven build automation, automated JUnit 5 / MockMvc testing, Jenkins declarative pipeline with SCM polling, dynamic build versioning, and GitHub Actions.
2. **Configuration Management & Container Orchestration:** Ansible roles and playbooks for automated host provisioning and idempotent deployments, multi-stage Docker containerization with non-root security, and Kubernetes orchestration (Deployment, NodePort Service, ConfigMap, Health Probes, Horizontal Scaling, and Zero-Downtime Rollback).

---

## Academic Context

- **Course:** DevOps (Course Code: `24CYAEC59`)
- **Institution:** Department of CSE (Cyber Security / AI & ML), M. S. Ramaiah Institute of Technology
- **Repository:** [https://github.com/nandish123-tech/Employee-Directory-DevOps.git](https://github.com/nandish123-tech/Employee-Directory-DevOps.git)

---

## Table of Contents

- [Project Architecture](#project-architecture)
- [Directory Structure](#directory-structure)
- [Technologies & Tools](#technologies--tools)
- [Application Details & REST Endpoints](#application-details--rest-endpoints)
- [Local Setup & Testing Instructions](#local-setup--testing-instructions)
- [Git Branching Strategy](#git-branching-strategy)
- [Jenkins CI Pipeline](#jenkins-ci-pipeline)
- [Ansible Configuration Management](#ansible-configuration-management)
- [Docker Multi-Stage Containerization](#docker-multi-stage-containerization)
- [Kubernetes Deployment & Scaling](#kubernetes-deployment--scaling)

---

## Project Architecture

```
                       [ Developer ]
                             │
                      git push / PR
                             ▼
                    ┌─────────────────┐
                    │  GitHub (SCM)   │
                    └────────┬────────┘
                             │
             ┌───────────────┴───────────────┐
             ▼                               ▼
    ┌─────────────────┐             ┌─────────────────┐
    │  GitHub Actions │             │   Jenkins CI    │
    │   (Hosted CI)   │             │   (Self-Hosted) │
    └────────┬────────┘             └────────┬────────┘
             │                               │
    [ JUnit 5 Tests ]               [ JUnit 5 Tests ]
             │                               │
    [ Versioned JAR ]               [ Versioned JAR ]
             │                               │
             └───────────────┬───────────────┘
                             ▼
                   [ Target Artifact ]
                             │
             ┌───────────────┴───────────────┐
             ▼                               ▼
    ┌─────────────────┐             ┌─────────────────┐
    │  Ansible Role   │             │ Docker Container│
    │ (Ubuntu Server) │             │  (Multi-Stage)  │
    └─────────────────┘             └────────┬────────┘
                                             │
                                             ▼
                                    ┌─────────────────┐
                                    │ Kubernetes / k8s│
                                    │ (Minikube / HA) │
                                    └─────────────────┘
```

---

## Directory Structure

```
Employee-Directory-DevOps/
├── .github/
│   └── workflows/
│       └── ci.yml                            # GitHub Actions CI workflow
├── .mvn/
│   └── wrapper/
│       └── maven-wrapper.properties          # Maven wrapper specification
├── ansible/
│   ├── ansible.cfg                           # Ansible configuration
│   ├── inventory.ini                         # Target host inventory
│   ├── playbook.yml                          # Deployment & verification playbook
│   ├── roles/
│   │   └── employee_app/
│   │       ├── defaults/main.yml             # Role default variables
│   │       ├── handlers/main.yml             # Idempotent service restart handler
│   │       ├── tasks/main.yml                # Installation & provisioning tasks
│   │       └── templates/
│   │           └── employee-directory.init.j2# SysV init service script template
│   └── target-host/
│       └── Dockerfile                        # Managed Ubuntu test environment
├── k8s/
│   ├── configmap.yaml                        # Application environment configuration
│   ├── deployment.yaml                       # 3-replica Deployment with health probes
│   └── service.yaml                          # NodePort Service (Port 30080)
├── src/
│   ├── main/
│   │   ├── java/com/devops/employeedirectory/
│   │   │   ├── EmployeeDirectoryApplication.java # Application main class
│   │   │   ├── controller/EmployeeController.java# REST Controller
│   │   │   ├── exception/
│   │   │   │   ├── EmployeeNotFoundException.java# 404 Exception
│   │   │   │   └── GlobalExceptionHandler.java   # Centralized error handler
│   │   │   ├── model/Employee.java               # Entity with Jakarta Validation
│   │   │   └── service/EmployeeService.java      # In-memory business logic
│   │   └── resources/
│   │       └── application.properties        # Config, port & actuator settings
│   └── test/
│       └── java/com/devops/employeedirectory/
│           ├── EmployeeControllerTest.java   # 7 MockMvc integration tests
│           └── EmployeeDirectoryApplicationTests.java # Context loading test
├── .dockerignore                             # Docker build context exclusions
├── .gitignore                                # Git ignored files
├── Dockerfile                                # Production multi-stage Dockerfile
├── Jenkinsfile                               # Declarative Jenkins CI pipeline
├── mvnw                                      # Linux/macOS Maven wrapper
├── mvnw.cmd                                  # Windows Maven wrapper batch script
├── pom.xml                                   # Maven POM with dynamic CI versioning
├── README.md                                 # Complete project documentation
└── repo_link.txt                             # Canonical GitHub repository URL
```

---

## Technologies & Tools

- **Programming Language:** Java 17 (OpenJDK / Eclipse Temurin)
- **Framework:** Spring Boot 3.3.5 (Spring Web, Spring Validation, Spring Boot Actuator)
- **Build System:** Apache Maven 3.9.9 (via Maven Wrapper `mvnw`)
- **Version Control:** Git 2.50+ & GitHub CLI (`gh`)
- **Continuous Integration:** Jenkins LTS & GitHub Actions
- **Unit & Integration Testing:** JUnit 5, Spring Boot Test, MockMvc, AssertJ
- **Configuration Management:** Ansible Core & `community.docker` plugin
- **Containerization:** Docker (Multi-stage build, Temurin JRE runtime, non-root user)
- **Container Orchestration:** Kubernetes (Minikube cluster, Kubectl)

---

## Application Details & REST Endpoints

The application manages employee records using a thread-safe, in-memory repository (`CopyOnWriteArrayList` and `AtomicLong` ID generator).

### Endpoints Specification

| Method | Endpoint | Description | Status Code |
|---|---|---|---|
| `GET` | `/` | Application and pod metadata (app name, version, host) | `200 OK` |
| `GET` | `/employees` | Retrieve all employee records | `200 OK` |
| `GET` | `/employees/{id}` | Retrieve employee by ID | `200 OK` / `404 Not Found` |
| `POST` | `/employees` | Add a new employee | `201 Created` / `400 Bad Request` |
| `GET` | `/actuator/health` | Comprehensive application health status | `200 OK` |
| `GET` | `/actuator/health/liveness` | Kubernetes liveness probe endpoint | `200 OK` |
| `GET` | `/actuator/health/readiness`| Kubernetes readiness probe endpoint | `200 OK` |

### Sample JSON Payloads

#### 1. Retrieve All Employees (`GET /employees`)
```json
[
  {
    "id": 1,
    "name": "Alice Smith",
    "department": "Engineering",
    "email": "alice.smith@example.com"
  },
  {
    "id": 2,
    "name": "Bob Jones",
    "department": "Quality Assurance",
    "email": "bob.jones@example.com"
  }
]
```

#### 2. Create Employee (`POST /employees`)
**Request Body:**
```json
{
  "name": "Charlie Brown",
  "department": "Cyber Security",
  "email": "charlie.brown@example.com"
}
```

**Response (`201 Created`):**
```json
{
  "id": 3,
  "name": "Charlie Brown",
  "department": "Cyber Security",
  "email": "charlie.brown@example.com"
}
```

#### 3. Validation Error Response (`POST /employees` with invalid data)
**Response (`400 Bad Request`):**
```json
{
  "timestamp": "2026-11-14T16:09:42",
  "status": 400,
  "error": "Bad Request",
  "validationErrors": {
    "name": "Name is mandatory",
    "department": "Department is mandatory",
    "email": "Email should be valid"
  }
}
```

---

## Local Setup & Testing Instructions

### Prerequisites
- JDK 17 installed and configured in `JAVA_HOME`
- Maven 3.9+ (or use the included `./mvnw` / `mvnw.cmd` wrapper)

### Build and Run Tests
```bash
# Clone the repository
git clone https://github.com/nandish123-tech/Employee-Directory-DevOps.git
cd Employee-Directory-DevOps

# Run all 8 automated JUnit 5 tests
./mvnw clean test

# Package the application JAR
./mvnw clean package -DskipTests

# Run the Spring Boot application
java -jar target/employee-directory-1.0.0.jar
```

### Test Endpoints Locally with cURL
```bash
# Test application info
curl -s http://localhost:8080/

# Test list of employees
curl -s http://localhost:8080/employees

# Test get employee by ID
curl -s http://localhost:8080/employees/1

# Test adding new employee
curl -s -X POST http://localhost:8080/employees \
  -H "Content-Type: application/json" \
  -d '{"name":"Kavya Sharma","department":"DevOps","email":"kavya.sharma@example.com"}'

# Test health check
curl -s http://localhost:8080/actuator/health
```

---

## Git Branching Strategy

The repository follows a clean, professional Git branching strategy where no direct commits are made to `main` without isolation and review:

```
main ───────────●───────────────────●───────────────────●───────────────────●───────────────────●
                 \                 / \                 / \                 / \                 /
feature/employee-api ─────────────●   │                 │ │                 │ │                 │
(PR #1 - REST API & Tests)            │                 │ │                 │ │                 │
                 \                    │                 │ │                 │ │                 │
feature/ci-pipeline ──────────────────●                 │ │                 │ │                 │
(PR #2 - Jenkins & GitHub Actions)                      │ │                 │ │                 │
                 \                                      │ │                 │ │                 │
feature/ansible-deployment ─────────────────────────────● │                 │ │                 │
(PR #3 - Ansible Role & Playbook)                         │                 │ │                 │
                 \                                        │                 │ │                 │
feature/docker-container ─────────────────────────────────●                 │ │                 │
(PR #4 - Multi-Stage Dockerfile)                                            │ │                 │
                 \                                                          │ │                 │
feature/kubernetes-manifests ───────────────────────────────────────────────● │                 │
(PR #5 - K8s Manifests & Probes)                                              │                 │
                 \                                                            │                 │
feature/documentation ────────────────────────────────────────────────────────●
(PR #6 - Complete README & Report Guide)
```

Verify the live Git log graph:
```bash
git log --graph --oneline --decorate --all
```

---

## Jenkins CI Pipeline

The project includes a declarative `Jenkinsfile` designed for automated continuous integration.

### Pipeline Stages
1. **Checkout:** Clones the repository branch `main` from GitHub SCM and prints the latest commit hash.
2. **Test:** Runs `./mvnw -B test`. The `post { always { junit ... } }` step publishes JUnit XML reports to display test trends, test counts, and health graphs in the Jenkins UI.
3. **Build:** Executes `./mvnw -B -DskipTests -Drevision=${APP_VERSION} clean package` using dynamic build-number versioning (`1.0.<BUILD_NUMBER>`).
4. **Post-Build Action:** Archives the generated versioned artifact (`target/employee-directory-*.jar`) with fingerprinting.

### Automatic Triggering
- **SCM Polling:** `triggers { pollSCM('* * * * *') }` continuously checks GitHub every minute for new commits and automatically triggers the pipeline upon detection.
- **Cross-Platform Compatibility:** Uses Jenkins `isUnix()` logic so the pipeline runs transparently on Linux, macOS, and Windows Jenkins agents.

---

## Ansible Configuration Management

Located in `ansible/`, the Ansible setup demonstrates modular infrastructure-as-code and idempotent deployment.

### Features
- **Modular Role Structure:** Custom `employee_app` role containing `tasks`, `handlers`, `defaults`, and `templates`.
- **System Hardening:** Runs the application under a dedicated, unprivileged system user (`empapp`).
- **Idempotency:** Executing `ansible-playbook -i inventory.ini playbook.yml` repeatedly results in `changed=0` on subsequent runs because Ansible ensures the system already matches the declared state.
- **Service Handlers:** The `Restart employee-directory` handler executes **only** when the application JAR or service script changes.

### Ansible Execution Commands
```bash
# 1. Build Ubuntu target container
docker build -t ansible-target ansible/target-host

# 2. Run managed container (acting as server)
docker run -d --init --name appserver -p 8081:8080 ansible-target

# 3. First run (Initial deployment)
cd ansible
ansible-playbook -i inventory.ini playbook.yml

# 4. Second run (Verifying idempotency: changed=0)
ansible-playbook -i inventory.ini playbook.yml

# 5. Verify service output
curl -s http://localhost:8081/
curl -s http://localhost:8081/actuator/health
```

---

## Docker Multi-Stage Containerization

The [Dockerfile](file:///C:/Users/ysnan/Desktop/DevOps/Employee-Directory-DevOps/Dockerfile) implements an optimized multi-stage build:

1. **Stage 1 (Build):**
   - Image: `maven:3.9-eclipse-temurin-17`
   - Caches dependencies using `mvn dependency:go-offline`.
   - Compiles and packages `app.jar`.
2. **Stage 2 (Runtime):**
   - Image: `eclipse-temurin:17-jre` (lightweight, minimal attack surface).
   - Creates a dedicated non-root user `appuser`.
   - Copies only the finished JAR from the build stage with user ownership.
   - Runs with `USER appuser` and exposes port `8080`.

### Docker Commands
```bash
# Build the Docker image
docker build -t employee-directory:1.0 .

# Inspect image size
docker images employee-directory:1.0

# Run container locally
docker run -d --name employee-api-app -p 8082:8080 employee-directory:1.0

# Verify container status and logs
docker ps --filter name=employee-api-app
docker logs employee-api-app

# Test application inside container
curl -s http://localhost:8082/
curl -s http://localhost:8082/actuator/health
```

---

## Kubernetes Deployment & Scaling

All manifests are organized inside the `k8s/` directory.

### Key Manifest Highlights
1. **ConfigMap (`k8s/configmap.yaml`):** Injects `APP_PORT: "8080"` and `APP_MESSAGE: "..."` dynamically into pods without rebuilding container images.
2. **Deployment (`k8s/deployment.yaml`):**
   - Sets `replicas: 3` for high availability.
   - Defines resource requests (`cpu: 100m`, `memory: 256Mi`) and limits (`memory: 512Mi`).
   - Configures **Readiness Probe** (`/actuator/health/readiness`) to ensure traffic only reaches initialized pods.
   - Configures **Liveness Probe** (`/actuator/health/liveness`) to automatically restart unhealthy containers.
3. **Service (`k8s/service.yaml`):** NodePort service mapping port 8080 to cluster port `30080` with selector `app: employee-directory`.

### Minikube Commands
```bash
# 1. Start Minikube cluster
minikube start --driver=docker --cpus=2 --memory=3500

# 2. Load Docker image into Minikube
minikube image load employee-directory:1.0

# 3. Apply manifests
kubectl apply -f k8s/

# 4. Check deployment rollout status
kubectl rollout status deployment/employee-directory --timeout=120s

# 5. Inspect resources
kubectl get deployment employee-directory -o wide
kubectl get rs -l app=employee-directory
kubectl get pods -l app=employee-directory -o wide --show-labels
kubectl get svc employee-directory-service -o wide

# 6. Verify load balancing across pods (host field displays different pod hostnames)
for i in 1 2 3 4 5; do minikube ssh -- curl -s localhost:30080/; echo; done

# 7. Scale up to 5 replicas
kubectl scale deployment employee-directory --replicas=5
kubectl get pods -l app=employee-directory -w

# 8. Scale back down to 3 replicas
kubectl scale deployment employee-directory --replicas=3
kubectl get pods -l app=employee-directory

# 9. Zero-downtime Rollback Demonstration
# Set a faulty environment port that breaks probes
kubectl annotate deployment/employee-directory kubernetes.io/change-cause="v1.0 initial deploy"
kubectl set env deployment/employee-directory APP_PORT=9999
kubectl annotate deployment/employee-directory kubernetes.io/change-cause="v1.1 BROKEN: APP_PORT=9999" --overwrite

# Observe rollout pause because readiness probe prevents traffic routing to broken pods:
kubectl rollout status deployment/employee-directory --timeout=10s

# Safely undo the deployment
kubectl rollout undo deployment/employee-directory
kubectl rollout status deployment/employee-directory
```

---

## Conclusion & Learning Outcomes

1. **Continuous Integration:** Implemented automated test-driven CI pipelines where every commit and pull request triggers build verification, executes 8 automated JUnit 5 tests, publishes surefire test reports, and tags traceable versioned artifacts.
2. **Configuration Management:** Gained practical understanding of Ansible's state-driven idempotency and handlers, ensuring predictable deployments without manual configuration drift.
3. **Container Security & Orchestration:** Developed multi-stage Docker builds adhering to non-root execution principles and utilized Kubernetes declarative manifests to achieve horizontal auto-recovery, probe-based traffic gating, and seamless rollback capabilities.
