# Web Quiz Engine
The multi-user web application for creating and solving quizzes.

### Each quiz has these fields:
* id
* title, required
* text - question, required
* options - array of possible answers, required at least 2 options
* answer - indexes of correct answers, **only writable** (Jackson doesn't serialize it), optional (because all options can be wrong)

### Currently, we have these operations:
* creating a new quiz - POST `/api/quizzes` with body including Quiz JSON Server responds with JSON including id, title, text and options fields.
* get quiz by id - GET `/api/quizzes/{id}` returns Quiz JSON or 404 if quiz doesn't exist
* get all quizzes - GET `/api/quizzes` returns array of quizzes
* solve quiz by id - POST `/api/quizzes/{id}/solve` with answer array in request body. This parameter consists of indexes of chosen options from `options` array.
It is possible to send empty array since some quizzes may not have correct options.
Server returns JSON with two fields: success (true/false) and feedback (string) or 404 code if the quiz doesn't exist.
