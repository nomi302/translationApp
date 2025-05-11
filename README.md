# Translation Service Project

Welcome to the Translation Service! This guide walks you through setup, configuration, and usage, including ready-to-import Postman samples.

---

## 📋 Table of Contents

1. [Prerequisites](#prerequisites)
2. [Project Setup](#project-setup)
3. [Configuration](#configuration)
4. [Running the Application](#running-the-application)
5. [API Endpoints](#api-endpoints)
6. [Postman Samples](#postman-samples)


---

## 🔧 Prerequisites

* **Java 17+**
* **Maven 3.6+**
* **MySQL** (or adjust `application.properties` for your DB)
* IDE (IntelliJ IDEA, Eclipse, etc.)
* Postman (for API testing)

---

## 🚀 Project Setup

1. **Clone the repo**

   ```bash
   git clone https://github.com/nomi302/translationApp.git
   cd TranslationService-app
   ```
2. **Configure the database**

   * Create a **MySQL** schema: `translation_db`
   * Edit `src/main/resources/application.properties`:

     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/translation_db?useSSL=false&serverTimezone=UTC
     spring.datasource.username=root
     spring.datasource.password=root
     ```
3. **Build**

   ```bash
   mvn clean install
   ```

---

## ⚙️ Configuration

Customize `src/main/resources/application.properties`:

```properties
# Server
server.port=8080
server.servlet.context-path=/

# Token
api.key=NjY4M2U1MDYtOGM0OC00ODIzLWI0OTgtNWQ2ZjI4MzQ0YjFj


---

## ▶️ Running the Application

```bash
mvn spring-boot:run
```

* **Base URL**: `http://localhost:8080/api`

---

## 📡 API Endpoints

### 1. Create Translation

**POST** `/translations/`
**Status:** `201 Created`
**Request Body:**

```json
{
  "locale": "al",
  "key": "welcome.headerrrrreeee",
  "content": "Welcome Back!",
  "tags": ["web","desktop"]
}
```

**Response:**

```json
{
  "id": 13,
  "locale": "al",
  "key": "welcome.headerrrrreeee",
  "content": "Welcome Back!",
  "tags": ["desktop","web"],
  "createdAt": "2025-05-11T20:51:19Z",
  "updatedAt": "2025-05-11T20:51:19Z"
}
```

### 2. Get Translation by ID

**GET** `/translations/{id}`
**Response:**

```json
{
  "id": 10,
  "locale": "ar",
  "key": "welcome.headerrrrr",
  "content": "Welcome Back!",
  "tags": ["desktop","web"],
  "createdAt": "2025-05-11T19:34:48Z",
  "updatedAt": "2025-05-11T19:34:48Z"
}
```

### 3. Update Translation

**PUT** `/translations/{id}`
**Body:** same as create
**Response:** updated object

### 4. Delete Translation

**DELETE** `/translations/{id}`
**Status:** `204 No Content`
**If Not Found:**

```json
{
  "status": 404,
  "message": "Translation not found with id: 1"
}
```

### 5. Search Translations

**GET** `/translations/search`
**Query Params (optional):**

* `locales=en,ar`
* `tags=web,desktop`
* `key=welcome.header`
* `content=Welcome`
* `page=0&size=10`

**Response:** (paginated)

```json
{
  "content": [ /* objects */ ],
  "pageable": { /* meta */ },
  "totalPages": 1,
  "totalElements": 7
}
```

### 6. Export Translations

**GET** `/translations/export`
**Response:**

```json
{
  "ar": {"welcome.headerrrrreeee":"Welcome Back!","welcome.headerrrrr":"Welcome Back!"},
  "en": { /* ... */ },
  "al": {"welcome.headerrrrreeee":"Welcome Back!"}
}
```

---

## 📬 Postman Samples

Set `{{baseUrl}} = http://localhost:8080/api` in your environment.

| Scenario            | Method | URL                    | Body / Params                                        | Expected                           |
| ------------------- | ------ | ---------------------- | ---------------------------------------------------- | ---------------------------------- |
| Create Translation  | POST   | `/translations/`       | Raw JSON as above                                    | `201 Created` + JSON               |
| Get Translation     | GET    | `/translations/10`     | —                                                    | `200 OK` + JSON                    |
| Delete Translation  | DELETE | `/translations/1`      | —                                                    | `204 No Content` / `404 Not Found` |
| Search Translations | GET    | `/translations/search` | `locales=en&tags=web&content=welcome&page=0&size=10` | Paginated JSON                     |
| Export Translations | GET    | `/translations/export` | —                                                    | Nested JSON map                    |

---

## 🧪 Automated Tests

A set of **JUnit 5** + **Spring Boot Test** cases validate the REST endpoints using `MockMvc`. To run the tests:

```bash
mvn test
```

### Key Test Scenarios

1. **Create Validation**: Blank fields should return `400 Bad Request`.
2. **Retrieve Translation**: Existing ID returns `200 OK` with correct payload; missing ID returns `404 Not Found`.
3. **Delete Translation**: Valid deletion returns `204 No Content`.
4. **Export Translations**: Mocks service output and asserts JSON structure.

```java
@SpringBootTest
@AutoConfigureMockMvc
class TranslationControllerTest {
    @Autowired MockMvc mockMvc;

    @MockBean TranslationService translationService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createTranslation_WithInvalidInput_ShouldReturnBadRequest() throws Exception {
        TranslationRequest invalid = new TranslationRequest("","","", Collections.emptySet());
        mockMvc.perform(post("/api/translations/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalid)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void getTranslation_WithInvalidId_ShouldReturnNotFound() throws Exception {
        when(translationService.getTranslation(999L))
            .thenThrow(new ResourceNotFoundException("Translation not found"));

        mockMvc.perform(get("/api/translations/999"))
            .andExpect(status().isNotFound());
    }

    @Test
    void deleteTranslation_ShouldReturnNoContent() throws Exception {
        doNothing().when(translationService).deleteTranslation(1L);
        mockMvc.perform(delete("/api/translations/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void exportTranslations_ShouldReturnCorrectStructure() throws Exception {
        Map<String, Map<String, String>> data = Map.of(
            "en", Map.of("welcome_header","Welcome")
        );
        when(translationService.exportTranslations()).thenReturn(data);

        mockMvc.perform(get("/api/translations/export"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.en.welcome_header").value("Welcome"));
    }
}
```
