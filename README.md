# Bank Cards API

RESTful API для управления банковскими картами, пользователями и транзакциями.  
Реализован на Spring Boot с использованием JWT для аутентификации.

---

## Функциональность

- Регистрация и авторизация пользователей
- Управление банковскими картами (создание, обновление, удаление)
- Запросы на создание карт (задачи)
- Перевод средств между картами
- Блокировка пользователей (админ)
- Просмотр баланса и истории карт

---

## Технологии

- Java 17
- Spring Boot
- Spring Security + JWT
- Spring Data JPA
- Validation (Jakarta Validation API)
- OpenAPI 3.0 спецификация

---

## Запуск

### Локально

1. Клонируй репозиторий
2. Настрой `application.properties` (БД, секреты JWT)
3. Собери проект с помощью Maven или Gradle:
   ```bash
   ./mvnw clean install
4. Запусти приложение: 
   ```bash
   ./mvnw spring-boot:run

### Запуск через Docker Compose

1. Убедись, что у тебя установлен Docker и Docker Compose
2. В корне проекта уже есть файл `docker-compose.yml` (при необходимости настрой его)
3. Собери Docker-образ приложения:

   ```bash
   docker build -t bankcards-api .
4. Запусти сервисы командой:   
   ```bash
    docker-compose up -d
---
## API Документация
Документация API доступна по следующим адресам:

- Swagger UI:  
  `http://<host>:<port>/swagger-ui.html`

- OpenAPI спецификация (JSON):  
  `http://<host>:<port>/v3/api-docs`
---
## Авторизация

Для доступа к защищённым эндпоинтам API необходимо передавать JWT токен в HTTP заголовке `Authorization`.
