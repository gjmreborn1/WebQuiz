# Web Quiz Engine
The multi-user web application for creating and solving quizzes.

Each quiz has exactly three fields:
* title
* text - question
* options - array of possible answers

For now, it is a simple JSON API that always returns the same quiz to be solved.
Currently, we have two operations:
* getting the quiz - GET `/api/quiz`
* passing an answer - POST `/api/quiz` with answer=value parameter. This parameter is the index of a chosen option from `options` array.
    Server returns JSON with two fields: success (true/false) and feedback (string).