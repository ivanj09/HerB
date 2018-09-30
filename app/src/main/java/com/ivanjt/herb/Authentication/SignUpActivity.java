package com.ivanjt.herb.Authentication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.ivanjt.herb.R;

public class SignUpActivity extends AppCompatActivity {
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mConfirmPasswordEditText;
    private EditText mAgeEditText;
    private EditText mDisplayNameEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Set custom ActionBar, move ActionBarTitle to center of screen
        setCustomActionBar();

        //Reference objects to views in xml
        mEmailEditText = this.findViewById(R.id.et_email_address);
        mPasswordEditText = this.findViewById(R.id.et_password);
        mConfirmPasswordEditText = this.findViewById(R.id.et_confirm_password);
        mAgeEditText = this.findViewById(R.id.et_age);
        mDisplayNameEditText = this.findViewById(R.id.et_display_name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.complete_sign_up, menu);
        final MenuItem mDoneButton = menu.findItem(R.id.mi_done);

        //Implements OnFocusChanged for email
        mEmailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    Validation.validateEmail(mEmailEditText, mDoneButton);
                    
                    boolean isEmailValid = Validation.isEmailValid(mEmailEditText.getText().toString());
                    boolean isPassValid = Validation.isPassValid(mPasswordEditText.getText().toString());
                    boolean isConfirmPassValid = Validation.isPassValid(mPasswordEditText.getText().toString()) && mConfirmPasswordEditText.getText().length() >= 8;
                    boolean isAgeValid = Validation.isAgeValid(mAgeEditText.getText().toString());
                    boolean isDisplayNameValid = Validation.isDisplayNameValid(mDisplayNameEditText.getText().toString());

                    if (isAgeValid && isConfirmPassValid && isPassValid && isEmailValid && isDisplayNameValid){
                        mDoneButton.setEnabled(true);
                    }
                }
            }
        });

        //Implements OnFocusChanged for password
        mPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    Validation.validatePass(mPasswordEditText, mDoneButton);

                    boolean isEmailValid = Validation.isEmailValid(mEmailEditText.getText().toString());
                    boolean isPassValid = Validation.isPassValid(mPasswordEditText.getText().toString());
                    boolean isConfirmPassValid = Validation.isPassValid(mPasswordEditText.getText().toString()) && mConfirmPasswordEditText.getText().length() >= 8;
                    boolean isAgeValid = Validation.isAgeValid(mAgeEditText.getText().toString());
                    boolean isDisplayNameValid = Validation.isDisplayNameValid(mDisplayNameEditText.getText().toString());

                    if (isAgeValid && isConfirmPassValid && isPassValid && isEmailValid && isDisplayNameValid){
                        mDoneButton.setEnabled(true);
                    }
                }
            }
        });
        
        //Implements OnFocusChanged for confirm password
        mConfirmPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    Validation.validateConfirmPass(mPasswordEditText, mConfirmPasswordEditText, mDoneButton);

                    boolean isEmailValid = Validation.isEmailValid(mEmailEditText.getText().toString());
                    boolean isPassValid = Validation.isPassValid(mPasswordEditText.getText().toString());
                    boolean isConfirmPassValid = Validation.isPassValid(mPasswordEditText.getText().toString()) && mConfirmPasswordEditText.getText().length() >= 8;
                    boolean isAgeValid = Validation.isAgeValid(mAgeEditText.getText().toString());
                    boolean isDisplayNameValid = Validation.isDisplayNameValid(mDisplayNameEditText.getText().toString());

                    if (isAgeValid && isConfirmPassValid && isPassValid && isEmailValid && isDisplayNameValid){
                        mDoneButton.setEnabled(true);
                    }
                }
            }
        });
        
        //Implements OnEditorActionListener for email
        mEmailEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_NEXT){
                    Validation.validateEmail(mEmailEditText, mDoneButton);

                    boolean isEmailValid = Validation.isEmailValid(mEmailEditText.getText().toString());
                    boolean isPassValid = Validation.isPassValid(mPasswordEditText.getText().toString());
                    boolean isConfirmPassValid = Validation.isPassValid(mPasswordEditText.getText().toString()) && mConfirmPasswordEditText.getText().length() >= 8;
                    boolean isAgeValid = Validation.isAgeValid(mAgeEditText.getText().toString());
                    boolean isDisplayNameValid = Validation.isDisplayNameValid(mDisplayNameEditText.getText().toString());

                    if (isAgeValid && isConfirmPassValid && isPassValid && isEmailValid && isDisplayNameValid){
                        mDoneButton.setEnabled(true);
                    }
                }
                return false;
            }
        });

        //Implements OnEditorActionListener for pass
        mPasswordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_NEXT){
                    Validation.validatePass(mPasswordEditText, mDoneButton);

                    boolean isEmailValid = Validation.isEmailValid(mEmailEditText.getText().toString());
                    boolean isPassValid = Validation.isPassValid(mPasswordEditText.getText().toString());
                    boolean isConfirmPassValid = Validation.isPassValid(mPasswordEditText.getText().toString()) && mConfirmPasswordEditText.getText().length() >= 8;
                    boolean isAgeValid = Validation.isAgeValid(mAgeEditText.getText().toString());
                    boolean isDisplayNameValid = Validation.isDisplayNameValid(mDisplayNameEditText.getText().toString());

                    if (isAgeValid && isConfirmPassValid && isPassValid && isEmailValid && isDisplayNameValid){
                        mDoneButton.setEnabled(true);
                    }
                }
                return false;
            }
        });

        //Implements OnEditorActionListener for confirm pass
        mConfirmPasswordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_GO){
                    Validation.validatePass(mPasswordEditText, mDoneButton);

                    boolean isEmailValid = Validation.isEmailValid(mEmailEditText.getText().toString());
                    boolean isPassValid = Validation.isPassValid(mPasswordEditText.getText().toString());
                    boolean isConfirmPassValid = Validation.isPassValid(mPasswordEditText.getText().toString()) && mConfirmPasswordEditText.getText().length() >= 8;
                    boolean isAgeValid = Validation.isAgeValid(mAgeEditText.getText().toString());
                    boolean isDisplayNameValid = Validation.isDisplayNameValid(mDisplayNameEditText.getText().toString());

                    if (isAgeValid && isConfirmPassValid && isPassValid && isEmailValid && isDisplayNameValid){
                        mDoneButton.setEnabled(true);
                    }
                }
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //When all field are validate, user can sign up
        if (item.isEnabled()){
            //Sign Up
        }
        return super.onOptionsItemSelected(item);
    }

    private void setCustomActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.action_bar);

            //Set ActionBar title
            TextView mActionBarTitle = actionBar.getCustomView().findViewById(R.id.action_bar_title);
            mActionBarTitle.setText(R.string.setup_profile);
        }
    }
}
