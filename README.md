# 🧾 PAN & Aadhaar Validator (Spring Boot + MongoDB)

A full-stack backend built with **Spring Boot 3**, **MongoDB**, and **Spring Validation** that validates **Indian PAN** and **Aadhaar** numbers.

This project demonstrates:
- The use of **annotations** in Spring (`@RestController`, `@Service`, `@Document`, etc.)
- DTO pattern for request/response handling
- Validation using **Jakarta Bean Validation** (`@Valid`, `@NotBlank`)
- Regex & Verhoeff algorithm-based ID verification
- Swagger (OpenAPI 3) for interactive API documentation

---

## 🧠 Features

✅ Validate **PAN** numbers using regex pattern  
✅ Validate **Aadhaar** numbers using the **Verhoeff checksum algorithm**  
✅ Store each validation result in **MongoDB**  
✅ Fetch all validation records or individual records  
✅ Interactive **Swagger UI** for testing APIs  
✅ Clean architecture with **Controller → Service → Repository** flow

---

## 📂 Project Structure
```bash
id-validate/
├── pom.xml
├── README.md
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── id_validate/
│   │   │               ├── IdValidateApplication.java
│   │   │               │
│   │   │               ├── controller/
│   │   │               │   └── ValidationController.java
│   │   │               │
│   │   │               ├── dto/
│   │   │               │   ├── ValidateRequestDTO.java
│   │   │               │   └── ValidateResponseDTO.java
│   │   │               │
│   │   │               ├── model/
│   │   │               │   └── ValidationRecord.java
│   │   │               │
│   │   │               ├── repo/
│   │   │               │   └── ValidationRecordRepository.java
│   │   │               │
│   │   │               ├── service/
│   │   │               │   └── ValidationService.java
│   │   │               │
│   │   │               └── util/
│   │   │                   └── AadhaarVerhoeff.java
│   │   │
│    └── resources/
│       ├── application.properties
│       └── static/    # (optional for frontend assets)
│   
│   
│      
│           
│            
│                
│                       
│
└── target/                # generated after build
```


---

## 🧩 Technology Stack

| Layer | Technology |
|--------|-------------|
| **Backend** | Spring Boot 3, Java 17 |
| **Database** | MongoDB |
| **Validation** | Jakarta Bean Validation |
| **Documentation** | Springdoc OpenAPI (Swagger UI) |
| **Build Tool** | Maven |

---

## ⚙️ Setup Instructions

### 1️⃣ Clone the project

```bash
          git clone https://github.com/<your-username>/id-validate.git
           cd id-validate 
```

----
2️⃣ Start MongoDB

Option A: via Docker

docker run -d -p 27017:27017 --name mongo mongo:7


Option B: local Mongo service running on port 27017

3️⃣ Configure application.properties

File: src/main/resources/application.properties
```bash
      spring.application.name=id-validate
      server.port=8080
      spring.data.mongodb.uri=mongodb://localhost:27017/idvalidate
      springdoc.api-docs.path=/v3/api-docs
```

4️⃣ Build & Run
mvn clean spring-boot:run
Server will start at 👉 http://localhost:8080

🧠 Annotations Used (and Why)
```bash
| Annotation                                      | Location                     | Purpose                                                  |
| ----------------------------------------------- | ---------------------------- | -------------------------------------------------------- |
| `@SpringBootApplication`                        | `IdValidateApplication.java` | Bootstraps Spring Boot and auto-scans components         |
| `@RestController`                               | `ValidationController.java`  | Marks class as REST API controller                       |
| `@RequestMapping`                               | Controller                   | Sets base path for all endpoints                         |
| `@PostMapping`, `@GetMapping`                   | Controller                   | Map specific HTTP verbs to methods                       |
| `@RequestBody`                                  | Controller                   | Maps JSON request → Java DTO                             |
| `@PathVariable`                                 | Controller                   | Extracts `{id}` from URL                                 |
| `@Valid`                                        | Controller                   | Enables validation of DTO using `@NotBlank`              |
| `@NotBlank`                                     | DTO                          | Ensures required fields are not null/empty               |
| `@Service`                                      | Service                      | Marks class for business logic layer                     |
| `@Repository`                                   | Repo                         | Marks data access layer                                  |
| `@Document`                                     | Model                        | Maps Java object → MongoDB collection                    |
| `@Id`, `@Indexed`                               | Model                        | Marks MongoDB primary key & indexed fields               |
| `@Data`, `@Builder`, `@RequiredArgsConstructor` | Lombok                       | Auto-generates boilerplate (getters, constructors, etc.) |
```

📤 API Endpoints
```bash
| Method | Endpoint                | Description                      |
| ------ | ----------------------- | -------------------------------- |
| `POST` | `/api/ids/validate`     | Validate a PAN or Aadhaar number |
| `GET`  | `/api/ids/records`      | Fetch all validation records     |
| `GET`  | `/api/ids/records/{id}` | Fetch one record by ID           |
```

💡 Request/Response Examples
✅ Validate PAN (Valid)
```bash

curl -X POST http://localhost:8080/api/ids/validate \
  -H "Content-Type: application/json" \
  -d '{"type":"PAN","value":"ABCDE1234F"}'
```
Response
```bash
{
  "valid": true,
  "normalized": "ABCDE1234F",
  "message": "Valid PAN format.",
  "id": "67125db64fc3ab7c8f0a3c0d"
}
```

❌ Validate PAN (Invalid)

Request
```bash

curl -X POST http://localhost:8080/api/ids/validate \
  -H "Content-Type: application/json" \
  -d '{"type":"PAN","value":"ABCDE12345"}'

```
Response
```bash
{
  "valid": false,
  "normalized": "ABCDE12345",
  "message": "Invalid PAN format.",
  "id": "67125dc74fc3ab7c8f0a3c0e"
}
```

✅ Validate Aadhaar

Request
```bash
curl -X POST http://localhost:8080/api/ids/validate \
  -H "Content-Type: application/json" \
  -d '{"type":"AADHAAR","value":"459050038690"}'
```

Response
```bash
{
  "valid": true,
  "normalized": "459050038690",
  "message": "Valid Aadhaar number.",
  "id": "67125df14fc3ab7c8f0a3c0f"
}
```

🔍 Fetch All Records

Request
```bash
curl http://localhost:8080/api/ids/records
```

Response
```bash
[
  {
    "id": "67125db64fc3ab7c8f0a3c0d",
    "type": "PAN",
    "input": "ABCDE1234F",
    "normalized": "ABCDE1234F",
    "valid": true,
    "message": "Valid PAN format.",
    "createdAt": "2025-10-18T10:15:30Z"
  }
]
```
🧭 System Workflow Diagram
<pre>
         ┌────────────────────────────┐
         │   User / API Client        │
         │  (Sends JSON Request)      │
         └──────────────┬─────────────┘
                        │
                        ▼
         ┌────────────────────────────┐
         │  ValidationController      │
         │  (@RestController)         │
         │  - Receives request        │
         │  - Runs @Valid DTO check   │
         └──────────────┬─────────────┘
                        │ calls
                        ▼
         ┌────────────────────────────┐
         │  ValidationService         │
         │  (@Service)                │
         │  - Business Logic          │
         │  - PAN regex check         │
         │  - Aadhaar checksum (Verhoeff) │
         └──────────────┬─────────────┘
                        │ saves
                        ▼
         ┌────────────────────────────┐
         │  ValidationRecordRepository│
         │  (MongoRepository)         │
         │  - Persists result         │
         │  - Fetches data            │
         └──────────────┬─────────────┘
                        │
                        ▼
         ┌────────────────────────────┐
         │  MongoDB Database           │
         │  Collection: validation_records │
         └──────────────┬─────────────┘
                        │
                        ▼
         ┌────────────────────────────┐
         │  ResponseEntity<DTO>       │
         │  Returns JSON response     │
         └────────────────────────────┘
         </prev>
🧾 License

MIT © 2025 – Developed by FSD team
