---
name: User story
about: Template for a stories for Scrum sprint
title: "[User Story] As a ___, I want ___ so that I can ___"
labels: user story
assignees: ''

---
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
