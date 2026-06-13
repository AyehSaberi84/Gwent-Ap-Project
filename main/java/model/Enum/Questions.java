package model.Enum;

public enum Questions {
    QUESTION1("1. What is your favorite color?"),
    QUESTION2("2. What is your favorite food?"),
    QUESTION3("3. What is your favorite car?"),
    QUESTION4("4. What is your favorite lesson?"),
    QUESTION5("5. What is your favorite animal?"),
    QUESTION6("6. When is your birthday?"),
    QUESTION7("7. How many brothers you have?"),
    QUESTION8("8. How many sisters you have?"),
    QUESTION9("9. What is your favorite book?"),
    QUESTION10("10. What is your favorite movie?");

    private final String question;

    Questions(String question) {
        this.question = question;
    }
    public String getQuestion() {
        return question;
    }
}
