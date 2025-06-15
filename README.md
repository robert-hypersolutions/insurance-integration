
# Insurance Integration Microservices

This project contains two Spring Boot 3.2 microservices written in Java 21:

1. **vehicle-service**: Accepts a vehicle registration number and returns vehicle details.
2. **insurance-service**: Accepts a personal identification number and returns all insurances for the person, including monthly costs. If the person has a car insurance, it integrates with the vehicle-service to fetch vehicle details.

---

## Architecture & Design

- **Language**: Java 21
- **Build Tool**: Maven
- **Framework**: Spring Boot 3.2
- **Communication**: OpenFeign for inter-service calls
- **Feature Toggle**: FF4J with runtime configuration and web console
- **Storage**: In-memory maps for vehicle and insurance data (demo scope)
- **Deployment**:
  - Dockerized services
  - Kubernetes manifests
  - GitOps-ready with ArgoCD and AWS EKS

---

## Feature Toggling (FF4J)

- **Toggle**: `DISCOUNT_CAMPAIGN`
- **Purpose**: Applies a $10 discount when the user has all 3 insurances
- **Runtime control**: Via FF4J web console (`/ff4j-console`)
- Toggle logic is covered by unit tests
- Supports future expansion (e.g., user-specific toggles, staged rollouts)

---

## Test Strategy (Test Pyramid)

| Layer      | Tool/Approach         | Scope                          |
|------------|------------------------|--------------------------------|
| Unit       | JUnit 5, Mockito        | Business logic, toggle logic   |
| Integration| Mocked Feign clients    | Inter-service integration      |
| Contract   | (Future) OpenAPI-based  | Schema validation              |
| E2E        | Postman tested manually | API-level validation           |

Tests are run automatically via CI. Core features and toggled logic are covered at the unit level.

---

## CI/CD Pipeline

- **CI**: GitHub Actions triggers on push to `main`
- **Build**: Maven unit and integration tests
- **CD**: AWS CodeBuild builds and pushes Docker images to ECR
- **GitOps**: ArgoCD (deployed in EKS) monitors a separate `eks-deployments` Git repo for changes

### GitOps Strategy

- Code and manifests are decoupled:
  - `insurance-integration`: holds application code
  - `eks-deployments`: holds K8s manifests
- ArgoCD requires read-only access only to the `eks-deployments` repo
- CI pipeline updates Docker tag in the deployment manifests
- ArgoCD syncs the new version automatically to EKS

This approach ensures:
- Safe, auditable deployments
- No direct access from CI/CD to cluster
- Supports progressive delivery (e.g., feature flag rollout)

---

## How to Run Locally

### Requirements
- Java 21
- Maven
- Docker (optional, for container builds)

### Run Services
```bash
cd vehicle-service
./mvnw spring-boot:run

cd insurance-service
./mvnw spring-boot:run
```

### API Endpoints
- Vehicle: GET http://localhost:8081/vehicles/{registrationNumber}
- Insurance: GET http://localhost:8080/insurances/{personalId}
- FF4J Console (REST API):
  - GET http://localhost:8080/admin/ff4j/features
  - POST http://localhost:8080/admin/ff4j/enable/DISCOUNT_CAMPAIGN
  - POST http://localhost:8080/admin/ff4j/disable/DISCOUNT_CAMPAIGN

---

## Error Handling & Extensibility

- Handles missing vehicles and unknown personal IDs gracefully
- Insurance product list is easily extendable via static map
- New toggles can be added with FF4J
- VehicleService returns Optional; null-safe
- Code uses Java 21 records for DTOs to reduce boilerplate

---

## What Could Be Improved

Given more time, I would:
- Add contract tests using OpenAPI validation
- Add persistence layer (e.g., PostgreSQL or DynamoDB)
- Enable OAuth2 security for external-facing APIs
- Add Argo Rollouts for canary/blue-green support
- Automate Postman

---

## Personal Reflection

This project reflects the architecture patterns I've applied in previous financial and insurance systems, particularly in AWS environments using Spring Boot and Kubernetes.

The challenge of combining FF4J feature toggles with a CI/CD-safe delivery model was particularly interesting. I emphasized testability, toggle logic isolation, and CI integration.

The GitOps model with ArgoCD and CodeBuild aligns with my preferred approach to managing production releases safely and transparently.

---
