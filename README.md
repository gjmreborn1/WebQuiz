# Web Quiz Engine
The multi-user web application for creating and solving quizzes.

### Each quiz has these fields:
* id
* title
* text - question
* options - array of possible answers
* answer - index of correct answer, **only writable** (Jackson doesn't serialize it)

### Currently, we have these operations:
* creating a new quiz - POST `/api/quizzes` with body including Quiz JSON (title, text, options, answer)
Server responds with JSON including id, title, text and options fields.
* get quiz by id - GET `/api/quizzes/{id}` returns Quiz JSON (id, title, text, options) or 404 if quiz doesn't exist
* get all quizzes - GET `/api/quizzes` returns array of quizzes
* solve quiz by id - POST `/api/quizzes/{id}/solve` with answer=value parameter in request body. This parameter is the index of a chosen option from `options` array.
Server returns JSON with two fields: success (true/false) and feedback (string) or 404 code if the quiz doesn't exist.
