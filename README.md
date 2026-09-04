# Microservices Services (Parent Repository)

## Student Information
- **Student Name:** Y.A. Thamidu Chamod
- **Student Number:** [241711050]
- **Slack Handle:** [Thamidu Chamod]
- **GCP Project ID:** [thermal-petal-506905-g0]

---

## Project Description
This repository acts as the main parent (super) repository for the backend business microservices. It utilizes Git Submodules to manage individual service repositories (`book-service`, `borrow-service`, and `user-service`) and includes process management and build configurations.

---

## Technology Stack
- **Language:** Java 25
- **Framework:** Spring Boot (Latest), Spring Cloud
- **Data Access:** Spring Data
- **Databases:** MySQL and MongoDB (Relational and Non-Relational integration)
- **Process Manager:** PM2 (for automatic restarts and process management)

---

## Included Submodules
This repository contains the following service submodules:
1. **`book-service`** - Manages library book records and related operations.
2. **`borrow-service`** - Handles donations and financial records.
3. **`user-service`** - Manages user authentication, profiles, and data.

---

## Setup / Getting Started Instructions
1. Clone this parent repository along with all its submodules:
   ```bash
   git clone --recursive https://github.com/ThamiduChamod/book_management_system-microservices_services.git
