package com.ivanjt.herb.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.ivanjt.herb.Authentication.Model.User;
import com.ivanjt.herb.DashboardActivity;
import com.ivanjt.herb.R;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = SignUpActivity.class.getSimpleName();

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mConfirmPasswordEditText;
    private EditText mAgeEditText;
    private EditText mDisplayNameEditText;

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
        final MenuItem mDoneButton = menu.findItem(R.id.mi_done);

        //Implements OnFocusChanged for email
        mEmailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    Validation.validateEmail(mEmailEditText, mDoneButton);

                    boolean isEmailValid = Validation.isEmailValid(mEmailEditText.getText().toString());
                    boolean isPassValid = Validation.isPassValid(mPasswordEditText.getText().toString());
                    boolean isConfirmPassValid = Validation.isPassValid(mPasswordEditText.getText().toString()) && mConfirmPasswordEditText.getText().length() >= 8;
                    boolean isAgeValid = Validation.isAgeValid(mAgeEditText.getText().toString());
                    boolean isDisplayNameValid = Validation.isDisplayNameValid(mDisplayNameEditText.getText().toString());

                    if (isAgeValid && isConfirmPassValid && isPassValid && isEmailValid && isDisplayNameValid) {
                        mDoneButton.setEnabled(true);

                    }
                }
            }
        });

        //Implements OnFocusChanged for password
        mPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    Validation.validatePass(mPasswordEditText, mDoneButton);

                    boolean isEmailValid = Validation.isEmailValid(mEmailEditText.getText().toString());
                    boolean isPassValid = Validation.isPassValid(mPasswordEditText.getText().toString());
                    boolean isConfirmPassValid = Validation.isPassValid(mPasswordEditText.getText().toString()) && mConfirmPasswordEditText.getText().length() >= 8;
                    boolean isAgeValid = Validation.isAgeValid(mAgeEditText.getText().toString());
                    boolean isDisplayNameValid = Validation.isDisplayNameValid(mDisplayNameEditText.getText().toString());

                    if (isAgeValid && isConfirmPassValid && isPassValid && isEmailValid && isDisplayNameValid) {
                        mDoneButton.setEnabled(true);
                    }
                }
            }
        });

        //Implements OnFocusChanged for confirm password
        mConfirmPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    Validation.validateConfirmPass(mPasswordEditText, mConfirmPasswordEditText, mDoneButton);

                    boolean isEmailValid = Validation.isEmailValid(mEmailEditText.getText().toString());
                    boolean isPassValid = Validation.isPassValid(mPasswordEditText.getText().toString());
                    boolean isConfirmPassValid = Validation.isPassValid(mPasswordEditText.getText().toString()) && mConfirmPasswordEditText.getText().length() >= 8;
                    boolean isAgeValid = Validation.isAgeValid(mAgeEditText.getText().toString());
                    boolean isDisplayNameValid = Validation.isDisplayNameValid(mDisplayNameEditText.getText().toString());

                    if (isAgeValid && isConfirmPassValid && isPassValid && isEmailValid && isDisplayNameValid) {
                        mDoneButton.setEnabled(true);
                    }
                }
            }
        });

        //Implements OnEditorActionListener for email
        mEmailEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_NEXT) {
                    Validation.validateEmail(mEmailEditText, mDoneButton);

                    boolean isEmailValid = Validation.isEmailValid(mEmailEditText.getText().toString());
                    boolean isPassValid = Validation.isPassValid(mPasswordEditText.getText().toString());
                    boolean isConfirmPassValid = Validation.isPassValid(mPasswordEditText.getText().toString()) && mConfirmPasswordEditText.getText().length() >= 8;
                    boolean isAgeValid = Validation.isAgeValid(mAgeEditText.getText().toString());
                    boolean isDisplayNameValid = Validation.isDisplayNameValid(mDisplayNameEditText.getText().toString());

                    if (isAgeValid && isConfirmPassValid && isPassValid && isEmailValid && isDisplayNameValid) {
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
                if (i == EditorInfo.IME_ACTION_NEXT) {
                    Validation.validatePass(mPasswordEditText, mDoneButton);

                    boolean isEmailValid = Validation.isEmailValid(mEmailEditText.getText().toString());
                    boolean isPassValid = Validation.isPassValid(mPasswordEditText.getText().toString());
                    boolean isConfirmPassValid = Validation.isPassValid(mPasswordEditText.getText().toString()) && mConfirmPasswordEditText.getText().length() >= 8;
                    boolean isAgeValid = Validation.isAgeValid(mAgeEditText.getText().toString());
                    boolean isDisplayNameValid = Validation.isDisplayNameValid(mDisplayNameEditText.getText().toString());

                    if (isAgeValid && isConfirmPassValid && isPassValid && isEmailValid && isDisplayNameValid) {
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
                if (i == EditorInfo.IME_ACTION_GO) {
                    Validation.validatePass(mPasswordEditText, mDoneButton);

                    boolean isEmailValid = Validation.isEmailValid(mEmailEditText.getText().toString());
                    boolean isPassValid = Validation.isPassValid(mPasswordEditText.getText().toString());
                    boolean isConfirmPassValid = Validation.isPassValid(mPasswordEditText.getText().toString()) && mConfirmPasswordEditText.getText().length() >= 8;
                    boolean isAgeValid = Validation.isAgeValid(mAgeEditText.getText().toString());
                    boolean isDisplayNameValid = Validation.isDisplayNameValid(mDisplayNameEditText.getText().toString());

                    if (isAgeValid && isConfirmPassValid && isPassValid && isEmailValid && isDisplayNameValid) {
                        mDoneButton.setEnabled(true);
                        registerUser();
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


        new AsyncTask<Void, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
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
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            mAuth.signInWithEmailAndPassword(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString());
                                        }
                                    });

                                    try {
                                        Thread.sleep(400);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });

                return mAuth.getCurrentUser() != null;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);

                if (aBoolean) {
                    Toast.makeText(SignUpActivity.this, "Register successfully!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignUpActivity.this, DashboardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else {
                    Toast.makeText(SignUpActivity.this, "Email address has already been taken, please log in!", Toast.LENGTH_SHORT).show();
                }

                progressDialog.dismiss();
            }
        }.execute();
    }

    @Override
    protected void onStop() {
        super.onStop();

        //It must be delete in the next commit, only for testing
        if (mAuth.getCurrentUser() != null)
            mAuth.signOut();
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
