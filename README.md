# Web Quiz
### The multi-user web application for creating and solving quizzes.

[![gjmreborn1](https://circleci.com/gh/gjmreborn1/WebQuiz.svg?style=shield)](https://circleci.com)

### Features
* User authorization system
* Variety of quiz operations (add, remove, solve, get)
* Data validation that ensures its consistency

### Developers info
* Unit and integration tests are provided.
* Authorization is done with **JWT strategy** (you pass JWT inside **Authorization header**).
If you will be unauthorized at the secure route, 401 status code is returned.
* Quizzes are paged (pages start from 0).
* There are two implementations of QuizService (choose implementation in QuizController's constructor):
    * QuizServiceDatabaseImpl (DatabaseQuizService qualifier) - stores quizzes data inside database
    * QuizServiceInMemoryImpl (InMemoryQuizService qualifier) - stores quizzes data inside in-memory list

### Technologies used
* Java
* Spring
* Hibernate (JPA)
* JUnit
* Mockito
* Gradle
* Git

### Each user has these fields:
* id
* username - is unique and required
* password - at least 5 characters, encrypted via BCrypt
* email - is required and must be valid email

### Operations on users:
* POST `/api/register` - register user. User's JSON must be sent in request body. When success returns 200, but when user already exist 400 is returned.
* POST `/api/login` - login user by name and password (sent in request body). JWT token is returned in case of success (200).
When user doesn't exist 404 is returned and when passwords are mismatched 400 will be returned.

### Each quiz has these fields:
* id
* title - is required
* text - content (question) of a quiz, required
* options - list of possible answers, required at least 2 options
* answer - indexes of correct answers, **only writable** (Jackson doesn't serialize it), optional (because all options can be wrong)

### Operations on quizzes:
* GET `/api/quizzes/{id}` secure - returns Quiz JSON or 404 if quiz with such ID doesn't exist
* GET `/api/quizzes?page=PAGE` secure - returns requested page of quizzes
* POST `/api/quizzes` secure - adds a new quiz (passed via request body). In response body there will be resent added quiz with fulfilled ID field.
* POST `/api/quizzes/{id}/solve` secure - solves an existing quiz by ID. In request body you must pass answer list fulfilled with indexes of user answers. This list can be empty, because some quizzes can have no correct answers.
404 will be returned if requested quiz doesn't exist and 200 in other cases. In response body there will be success and feedback keys, for example:
```json
    {
        "success": "true",
        "feedback": "Congratulations, you're right!"
    }
```
* DELETE `/api/quizzes/{id}` secure - deletes an existing quiz by ID. When quiz doesn't exist, it returns 404 and 204 in successful case.
