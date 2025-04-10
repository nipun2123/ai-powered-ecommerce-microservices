## [User Story] As a Developer, I want a foundational Spring Boot module for product management so that I can build product-related features

**Story Points**: 5 (Fibonacci scale)  
**Priority**: High  
- **Acceptance Criteria**:  
  - [ ] Module created with `product-service` directory structure  
  - [ ] Parent POM dependencies configured  
  - [ ] Basic health endpoint available at `/actuator/health` 

**Tech Notes**:
```xml
<!-- pom.xml -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.2.0</version>
</parent>
```
**Estimated Time**: 1 hours
