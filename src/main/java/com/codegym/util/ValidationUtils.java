package com.codegym.util;

import java.util.regex.Pattern;

public class ValidationUtils {
    public static final String NAME_REGEX = "^[A-Z][a-z]*([ ][A-Z][a-z]*)*$";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    public static final String ADDRESS_REGEX = "^[A-Z0-9][A-Za-z0-9]*([ ][A-Z0-9][A-Za-z0-9]*)*$";
    public static final String DATE_REGEX = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
    public static final String COURSENAME_REGEX = "^([a-zA-Z]+[a-zA-Z0-9]*)+$";
    public static final String NUMBERONLY_REGEX = "\\d+";

    public static boolean isNameValid (String name) {
        return Pattern.compile(NAME_REGEX).matcher(name).matches();
    }

    public static boolean isEmailValid (String email) {
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }

    public static boolean isAddressValid (String address) {
        return Pattern.compile(ADDRESS_REGEX).matcher(address).matches();
    }

    public static boolean isDateValid (String date) {
        return Pattern.compile(DATE_REGEX).matcher(date).matches();
    }

    public static boolean isCourseNameValid (String courseName) {
        return Pattern.compile(COURSENAME_REGEX).matcher(courseName).matches();
    }

    public static boolean isNumberValid (String number) {
        return Pattern.compile(NUMBERONLY_REGEX).matcher(number).matches();
    }
}
