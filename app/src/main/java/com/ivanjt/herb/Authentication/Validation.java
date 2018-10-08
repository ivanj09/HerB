package com.ivanjt.herb.Authentication;

import android.util.Patterns;
import android.view.MenuItem;
import android.widget.EditText;

class Validation {
    static void validateDisplayName(EditText editText, MenuItem menuItem) {
        if (isDisplayNameValid(editText.getText().toString())){
            editText.setError(null);
        } else {
            menuItem.setEnabled(false);
            editText.setError("This field cannot be empty");
        }
    }

    static void validateAge(EditText editText, MenuItem menuItem) {
        if (isAgeValid(editText.getText().toString())){
            editText.setError(null);
        } else {
            menuItem.setEnabled(false);
            editText.setError("This field cannot be empty");
        }
    }

    static void validateEmail(EditText editText, MenuItem menuItem) {
        if (isEmailValid(editText.getText().toString())) {
            editText.setError(null);
        } else {
            menuItem.setEnabled(false);
            editText.setError("Invalid email address, try again!");
        }
    }

    static void validatePass(EditText editText, MenuItem menuItem) {
        if (isPassValid(editText.getText().toString())) {
            editText.setError(null);
        } else {
            menuItem.setEnabled(false);
            editText.setError("Password is too short..");
        }
    }

    static void validateConfirmPass(EditText mPasswordEditText, EditText mConfirmPasswordEditText, MenuItem menuItem) {
        if (mConfirmPasswordEditText.getText().toString().equals(mPasswordEditText.getText().toString())) {
            mConfirmPasswordEditText.setError(null);
        } else {
            mConfirmPasswordEditText.setError("Password is not match..");
            menuItem.setEnabled(false);
        }
    }

    static boolean isEmailValid(String s) {
        return Patterns.EMAIL_ADDRESS.matcher(s).matches();
    }

    static boolean isPassValid(String s) {
        return s.length() >= 8;
    }

    static boolean isAgeValid(String s) {
        return !s.isEmpty();
    }

    static boolean isDisplayNameValid(String s){
        return !s.isEmpty();
    }
}
