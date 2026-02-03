
_**This repository contains several projects I work on in my free time.
Thank you for taking the time to check it out. Enjoy!**_

(_**В этом репозитории собраны несколько проектов, над которыми я работаю в свободное время.
Спасибо, что нашли время ознакомиться с ним. Приятного просмотра!**_)

_**Both projects contain examples of writing integration tests in Java and Spring Boot
using modern development practices and containerization.**_

(_**Оба проекта содержат примеры написания интеграционных тестов на Java и Spring Boot 
с использованием современных практик разработки и контейнеризации.**_)

🛠 **Technology Stacks (Стек Технологий)**

* **Language (Язык)**: Java 21
* **Framework (Фреймворк)**: Spring Boot 3.x, 
* **Containerization (Контейнеризация)**: Docker, Docker Compose
* **Google Container Tools (jib)** - For creating optimized Docker and OCI images for Java applications (для создания оптимизированных образов Docker и OCI для Java приложений)
* **Databases (Базы данных):** PostgreSQL
* **ORM:** Spring Data JPA (Hibernate)
* **Authentication:** Spring Security JWT


**Testing (Тестирование)**

* JUnit 5, Mockito 
* Spring MVC Test
* Testcontainers - For integration tests (для интеграционного тестирования)

**Services (Сервисы)**

* Book App
* Users Service


🚀 Запуск проекта

**Prerequisites (Предварительные требования)**

JDK 21 (17+ should be enough but I haven't tested it (17+ должно быть достаточно - но не проверил).
Docker 20.10+
Docker Compose 2.4+
Gradle 8.14+
GIT 2.51+
IntelliJ IDEA 2025.2 Community Edition

**Local launch (Локальный запуск)**

Clone the repository (Клонируйте репозиторий): 
 - `git clone https://github.com/capfer27/testing-java-apps.git`

 - Open any project (usersservice or bookapp) in Intellij Idea and launch the tests which are in the directory src/test and run the tests.
   (откройте любой проект (usersservice или bookapp) в Intellij Idea и запустите тесты, 
   которые лежат в директории src/test и запустите тесты). 

**The project can also be run via docker (Проект также можно запустить через докер):**
  - `cd usersservice `
  - `docker-compose up -d --build`
  - Open the file in postman and check (открывать файл в postman и проверить) _UsersServiceAPI.postman_collection.json_ .
  - To view the list of users, you must first log in by requesting the _api/users/login_ endpoint. Then, get the token (Bearer token) from the response headers
    and then make a GET request to _/api/users_.
    ( Для того чтобы посмотреть список пользователей сначала надо залогиниться, путем 
    запроса на endpoint _api/users/login_. Затем взять токен (Bearer token) из ответа (response headers)
    и дальше уже делать GET-запрос на _/api/users_).

**Stoping the services (Остановка сервисов):** 
 - `docker-compose down`

