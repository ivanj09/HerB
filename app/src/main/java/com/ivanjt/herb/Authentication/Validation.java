package com.ivanjt.herb.Authentication;

import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

class Validation {
    static void validateEmail(EditText editText, Button button) {
        if (isEmailValid(editText.getText().toString())){
            editText.setError(null);
        } else {
            button.setEnabled(false);
            editText.setError("Invalid email address, try again!");
        }
    }
    static void validatePass(EditText editText, Button button){
        if (isPassValid(editText.getText().toString())){
            editText.setError(null);
        } else {
            button.setEnabled(false);
            editText.setError("Password is too short..");
        }
    }

    static boolean isEmailValid(String s) {
        return Patterns.EMAIL_ADDRESS.matcher(s).matches();
    }

    static boolean isPassValid(String s){
        return s.length() >= 8;
    }
}
