package com.ivanjt.herb.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.ivanjt.herb.Model.User;
import com.ivanjt.herb.DashboardActivity;
import com.ivanjt.herb.R;

/**
 * This activity provides Sign-Up with Email
 *
 * */
public class SignUpActivity extends AppCompatActivity {
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mConfirmPasswordEditText;
    private EditText mAgeEditText;
    private EditText mDisplayNameEditText;
    private MenuItem mDoneButton;

    private FirebaseAuth mAuth;

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

        //Get instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.complete_sign_up, menu);
        mDoneButton = menu.findItem(R.id.mi_done);

        //Implements OnFocusChanged and textChanghedListener for name
        mDisplayNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    Validation.validateDisplayName(mDisplayNameEditText, mDoneButton);
                }
            }
        });

        mDisplayNameEditText.addTextChangedListener(textChangedListener());

        //Implements OnFocusChanged and textChangedListener for age
        mAgeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    Validation.validateAge(mAgeEditText, mDoneButton);
                }
            }
        });

        mAgeEditText.addTextChangedListener(textChangedListener());

        //Implements OnFocusChanged and textChangedListener for email
        mEmailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    Validation.validateEmail(mEmailEditText, mDoneButton);
                }
            }
        });

        mEmailEditText.addTextChangedListener(textChangedListener());

        //Implements OnFocusChanged and textChangedListener for password
        mPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    Validation.validatePass(mPasswordEditText, mDoneButton);
                }
            }
        });

        mPasswordEditText.addTextChangedListener(textChangedListener());

        //Implements OnFocusChanged and textChangedListener for confirm password
        mConfirmPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    Validation.validateConfirmPass(mPasswordEditText, mConfirmPasswordEditText, mDoneButton);
                }
            }
        });

        mConfirmPasswordEditText.addTextChangedListener(textChangedListener());

        return super.onCreateOptionsMenu(menu);
    }

    private TextWatcher textChangedListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                boolean isEmailValid = Validation.isEmailValid(mEmailEditText.getText().toString());
                boolean isPassValid = Validation.isPassValid(mPasswordEditText.getText().toString());
                boolean isConfirmPassValid = Validation.isPassValid(mPasswordEditText.getText().toString()) && mConfirmPasswordEditText.getText().length() >= 8;
                boolean isAgeValid = Validation.isAgeValid(mAgeEditText.getText().toString());
                boolean isDisplayNameValid = Validation.isDisplayNameValid(mDisplayNameEditText.getText().toString());

                if (isAgeValid && isConfirmPassValid && isPassValid && isEmailValid && isDisplayNameValid) {
                    mDoneButton.setEnabled(true);
                } else {
                    mDoneButton.setEnabled(false);
                }
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //When all field are validate, user can sign up
        if (item.isEnabled()) {
            registerUser();
        }
        return super.onOptionsItemSelected(item);
    }

    private void registerUser() {
        //Declare ProgressDialog instance
        final android.app.ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering your account, please wait...");
        progressDialog.setProgressNumberFormat(null);
        progressDialog.setProgressPercentFormat(null);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

        //Sign Up with validated email and pass
        mAuth.createUserWithEmailAndPassword(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString())
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(
                                    mDisplayNameEditText.getText().toString(),
                                    mAgeEditText.getText().toString()
                            );

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child("Information")
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    mAuth.signInWithEmailAndPassword(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString());
                                }
                            });

                            Toast.makeText(SignUpActivity.this, "Register successfully!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(SignUpActivity.this, DashboardActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignUpActivity.this, "Email address has already been taken, try again!", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    private void setCustomActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.action_bar);

            //Set ActionBar title
            TextView mActionBarTitle = actionBar.getCustomView().findViewById(R.id.action_bar_title);
            mActionBarTitle.setText(R.string.setup_profile);
        }
    }
}
