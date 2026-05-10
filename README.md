# 💇 Hairstyle API

Hairstyle API is a RESTful service built with **Clojure** designed to manage hairstyle resources. It provides endpoints for creating, retrieving, updating, and deleting hairstyle records, following diplomat architecture principles and modular design.

---

# 📌 Overview

The project exposes HTTP endpoints for creating, retrieving, updating, and deleting hairstyles.

The application was built using:

- **Clojure 1.12**
- **Pedestal** as the HTTP server
- **Datomic** as the database
- **Prismatic Schema** for contract validation
- **Leiningen** for dependency management

The structure follows a clear separation between:

- Controllers
- Adapters
- Models
- Database Layer
- HTTP Diplomats
- Interceptors
- Wire formats

---

# 🧱 Project Architecture

```text
src/
└── hairstyle_api/
    ├── adapters/
    ├── controllers/
    ├── db/
    │   ├── config.clj
    │   ├── hairstyle.clj
    │   └── schema/
    ├── diplomat/
    │   └── http_server.clj
    ├── interceptors/
    ├── logic/
    ├── models/
    ├── protocols/
    ├── wire/
    │   ├── in/
    │   └── out/
    ├── components.clj
    └── server.clj
```

---

# 🚀 Getting Started

## Prerequisites

Before starting, install:

- Java 17+ (JVM)
- Leiningen

Verify installations:

```bash
java -version
lein version
```

---

## Installation

```bash
git clone <repository-url>
cd hairstyle-api
lein deps
```

---

## Running the Project

```bash
lein run
```

Server available at:

```text
http://localhost:8080
```

---

# 🔌 Available Endpoints

## Check API Version

### GET `/api/version`

### Response

```json
{
  "version": "1.0.0"
}
```

---

## List Hairstyles

### GET `/api/hairstyles`

### Response

```json
[
  {
    "id": 1,
    "name": "Buzz Cut",
    "description": "Minimal short hairstyle",
    "texture": ["straight"],
    "length": "short",
    "main-image": "https://example.com/image.jpg"
  }
]
```

---

## Get Hairstyle by ID

### GET `/api/hairstyles/:id`

### Example

```bash
curl http://localhost:8080/api/hairstyles/1
```

---

## Create Hairstyle

### POST `/api/hairstyles`

### Body

```json
{
  "name": "Undercut",
  "slug": "undercut",
  "description": "Modern hairstyle with shaved sides",
  "texture": ["straight", "wavy"],
  "length": "medium",
  "main-image": "https://example.com/undercut.jpg",
  "gallery": ["https://example.com/1.jpg", "https://example.com/2.jpg"],
  "tags": ["modern", "popular"]
}
```

### Response

```http
201 Created
```

---

## Update Hairstyle

### PUT `/api/hairstyles/:id`

Completely replaces the resource.

---

## Partial Update

### PATCH `/api/hairstyles/:id`

Updates only the provided fields.

### Example

```json
{
  "description": "Updated description"
}
```

---

## Delete Hairstyle

### DELETE `/api/hairstyles/:id`

### Response

```http
204 No Content
```

---

# 📦 Data Model

The main Hairstyle schema contains:

```clojure
{
 :id number
 :name string
 :slug string
 :description string
 :texture [string]
 :length string
 :main-image string
 :gallery [string]
 :tags [string]
}
```

---

# 🧠 Internal Structure

## Controllers

Responsible for orchestration rules.

File:

```text
src/hairstyle_api/controllers/hairstyle.clj
```

---

## Adapters

Transform internal structures ↔ wire format ↔ Datomic.

File:

```text
src/hairstyle_api/adapters/hairstyle.clj
```

---

## Diplomats

HTTP layer responsible for routes and responses.

File:

```text
src/hairstyle_api/diplomat/http_server.clj
```

---

## Database Layer

Responsible for:

- Queries
- Persistence
- Updates
- Retract
- Transactions

File:

```text
src/hairstyle_api/db/hairstyle.clj
```

---

# 🛡️ Validation

The API uses **Prismatic Schema** for validating:

- Inputs
- Internal contracts
- Data structures
- Controller operations

Example:

```clojure
(s/defschema Create
  {:name s/Str
   :description s/Str})
```

---

# 🧪 Tests

To run the tests:

```bash
lein test
```

---

# 📄 License

Project licensed under:

- EPL-2.0
- GPL-2.0-or-later

See the `LICENSE` file.

---
