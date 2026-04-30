# 💇 Hairstyle API

## Overview
Hairstyle API is a RESTful service built with **Clojure** designed to manage hairstyle resources. It provides endpoints for creating, retrieving, updating, and deleting hairstyle records, following clean architecture principles and modular design.

---

## 🧱 Architecture

The project is structured to promote separation of concerns and maintainability:

- **Controllers**: Handle HTTP requests and responses
- **Domain / Protocols**: Define business rules and abstractions
- **Database Layer**: Responsible for persistence and data access
- **Components**: Lifecycle management of the application
- **Wire Layer**: Input/output transformations

---

## ⚙️ Tech Stack

- **Language**: Clojure
- **Build Tool**: Leiningen
- **Web Server**: Ring / Jetty
- **Database**: Configurable (e.g., Datomic or similar)

---

## 📁 Project Structure

```
src/
 └── hairstyle_api/
     ├── server.clj
     ├── config.clj
     ├── components.clj
     ├── controllers/
     ├── db/
     ├── diplomat/
     ├── protocols/
     └── wire/
```

---

## 🚀 Getting Started

### Prerequisites

- Java 8+
- Leiningen

### Installation

```bash
git clone <repository-url>
cd hairstyle-api
lein deps
```

### Running the Application

```bash
lein run
```

The API will be available at:

```
http://localhost:3000
```

---

## 🔌 API Endpoints

### Create Hairstyle
**POST /hairstyles**

```json
{
  "name": "Undercut",
  "description": "Modern style with shaved sides"
}
```

---

### Get All Hairstyles
**GET /hairstyles**

---

### Get Hairstyle by ID
**GET /hairstyles/{id}**

---

### Update Hairstyle
**PUT /hairstyles/{id}**

---

### Delete Hairstyle
**DELETE /hairstyles/{id}**

---

## 🧪 Testing

Run automated tests with:

```bash
lein test
```

---

## 🔧 Configuration

Application configuration is managed in:

```
src/hairstyle_api/config.clj
```

You can customize:

- Server port
- Database connection
- Environment settings (dev, test, prod)

---

## 📈 Best Practices

- Functional programming principles
- Immutable data structures
- Protocol-based abstractions
- Modular architecture
- Automated testing

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`feature/your-feature`)
3. Commit your changes
4. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License.
