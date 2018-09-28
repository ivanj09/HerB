package com.ivanjt.herb;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


/***
 * This Activity provides Sign-In by Email or Google
 * Catatan: Dummy user -> email = ivanjth26@gmail.com , pass = ulala999
 */
public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mSignInWithEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Get current instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        //If user in active state
        //        if (mAuth.getCurrentUser() != null){
        //            startActivity(new Intent(this, DashboardActivity.class));
        //        }

        //Reference object to views in xml
        mEmailEditText = this.findViewById(R.id.et_email_address);
        mPasswordEditText = this.findViewById(R.id.et_password);
        mSignInWithEmail = this.findViewById(R.id.bt_sign_in_by_email);

        //Set enabled to false
        mSignInWithEmail.setEnabled(false);

        //Implements setOnEditorActionListener for email
        mEmailEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_NEXT){
                    validateEmail(mEmailEditText.getText().toString());

                    if (isEmailValid(mEmailEditText.getText().toString()) && isPassValid(mPasswordEditText.getText().toString())){
                        mSignInWithEmail.setEnabled(true);
                    }
                }
                return false;
            }
        });

        //Implements setOnEditorActionListener for pass
        mPasswordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE){
                    validatePass(mPasswordEditText.getText().toString());

                    if (isEmailValid(mEmailEditText.getText().toString()) && isPassValid(mPasswordEditText.getText().toString())){
                        mSignInWithEmail.setEnabled(true);
                        mSignInWithEmail.performClick();
                    }
                }
                return false;
            }
        });

        //Implements setOnFocusChanged for email
        mEmailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    validateEmail(mEmailEditText.getText().toString());
                    if (isEmailValid(mEmailEditText.getText().toString()) && isPassValid(mPasswordEditText.getText().toString())){
                        mSignInWithEmail.setEnabled(true);
                    }
                }
            }
        });

        //Implements setOnFocusChanged for pass
        mPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    validatePass(mPasswordEditText.getText().toString());

                    if (isEmailValid(mEmailEditText.getText().toString()) && isPassValid(mPasswordEditText.getText().toString())){
                        mSignInWithEmail.setEnabled(true);
                    }
                }
            }
        });
    }

    private void validateEmail(String s) {
        if (isEmailValid(s)){
            mEmailEditText.setError(null);
        } else {
            mSignInWithEmail.setEnabled(false);
            mEmailEditText.setError("Invalid email address, try again!");
        }
    }
    private void validatePass(String s){
        if (isPassValid(s)){
            mPasswordEditText.setError(null);
        } else {
            mSignInWithEmail.setEnabled(false);
            mPasswordEditText.setError("Password is too short..");
        }
    }

    private boolean isEmailValid(String s) {
        return Patterns.EMAIL_ADDRESS.matcher(s).matches();
    }

    private boolean isPassValid(String s){
        return s.length() >= 8;
    }

    public void signInWith(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.bt_sign_in_by_email: //If user sign in with email
                //Declare ProgressDialog instance
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Signing In...");
                progressDialog.setProgressNumberFormat(null);
                progressDialog.setProgressPercentFormat(null);
                progressDialog.setIndeterminate(true);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.show();

                //Create AsyncTask for login using other thread, not in UI Thread
                new AsyncTask<Void, Integer, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        //Sign in with email and password
                        mAuth.signInWithEmailAndPassword(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString());

                        try {
                            Thread.sleep(400);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);

                        if (mAuth.getCurrentUser() != null){
                            Toast.makeText(LoginActivity.this, "Sign In as " + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                        }

                        //Dismiss the progress dialog
                        progressDialog.dismiss();

                        //Need to delete soon.., only for testing purpose
                        mAuth.signOut();
                    }
                }.execute();

                break;
            case R.id.bt_sign_in_by_google: //If user sign in with Google Account
                break;
        }
    }
}
