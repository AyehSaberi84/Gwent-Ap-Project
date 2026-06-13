package model.Enum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Validation {
    MENU_EXIT(""),
    SHOW_MENU(""),
    ALPHABETICAL_PASSWORD("(?=.*[a-zA-Z])[A-Za-z0-9@#$%^&+=]+"),
    CHECK_NUMBER_PASSWORD ("(?=.*[0-9])[A-Za-z@#$%^&+=0-9]+"),
    SPECIAL_CHARACTERS_PASSWORD ("(?=.*[@#$%^&+=])[A-Za-z0-9@#$%^&+=]+"),
    PICK_QUESTION(""),
    LOGIN(""),
    FORGET_PASSWORD(""),
    ANSWER(""),
    SET_PASSWORD(""),
    USER_INFO(""),
    CREATE_GAME(""),
    SHOW_FACTION(""),
    SELECT_FACTION(""),
    SHOW_CARDS(""),
    SHOW_DECK(""),
    SHOW_INFORMATION_CURRENT(""),
    SAVE_DECK(""),
    LOAD_DECK(""),
    SHOW_LEADERS(""),
    SELECT_LEADER(""),
    ADD_TO_DECK(""),
    DELETE_FROM_DECK(""),
    CHANGE_TURN(""),
    START_GAME(""),
    VETO_CARD(""),
    IN_HAND_DECK(""),
    REMAINING_CARDS(""),
    BURNED_CARDS(""),
    CARDS_IN_ROW(""),
    SPELLS_IN_PLAY(""),
    PLAY_CARD(""),
    SHOW_COMMANDER(""),
    COMMANDER_POWER_PLAY(""),
    SHOW_PLAYERS_INFO(""),
    SHOW_NUMBER_OF_CARDS_IN_HAND(""),
    SHOW_TURN_INFO(""),
    SHOW_TOTAL_SCORE(""),
    SHOW_TOTAL_SCORE_OF_ROW(""),
    PASS_ROUND(""),

    USERNAME_VALIDATION("[A-Za-z0-9-]+"),
    EMAIL_VALIDATION("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}"),
    PASSWORD_VALIDATION("[A-Za-z0-9@#$%^&+=]+")
    ;



    private final String regex;

    Validation(String regex) {
        this.regex = regex;
    }

    public static boolean isValid(String string, Validation validation) {
        return Pattern.matches(validation.regex , string);
    }

    public static Matcher getMatcher(String input, Validation validation) {
        return null;
    }
}
