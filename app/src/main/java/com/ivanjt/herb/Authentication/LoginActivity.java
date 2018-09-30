package com.ivanjt.herb.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.ivanjt.herb.R;


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

        //If user in active state, directly go to dashboard (will be implemented next time)
        //        if (mAuth.getCurrentUser() != null){
        //            startActivity(new Intent(this, DashboardActivity.class));
        //        }

        //Reference objects to views in xml
        mEmailEditText = this.findViewById(R.id.et_email_address);
        mPasswordEditText = this.findViewById(R.id.et_password);
        mSignInWithEmail = this.findViewById(R.id.bt_sign_in_by_email);

        //Set enabled state to false
        mSignInWithEmail.setEnabled(false);

        //Implements OnEditorActionListener for email
        mEmailEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_NEXT){
                    if (Validation.isEmailValid(mEmailEditText.getText().toString()) && Validation.isPassValid(mPasswordEditText.getText().toString())){
                        mSignInWithEmail.setEnabled(true);
                    } else {
                        mSignInWithEmail.setEnabled(false);
                    }
                }
                return false;
            }
        });

        //Implements OnEditorActionListener for pass
        mPasswordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_GO){
                    if (Validation.isEmailValid(mEmailEditText.getText().toString()) && Validation.isPassValid(mPasswordEditText.getText().toString())){
                        mSignInWithEmail.setEnabled(true);
                        mSignInWithEmail.performClick();
                    } else {
                        mSignInWithEmail.setEnabled(false);
                    }
                }
                return false;
            }
        });

        //Implements OnFocusChanged for email
        mEmailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    if (Validation.isEmailValid(mEmailEditText.getText().toString()) && Validation.isPassValid(mPasswordEditText.getText().toString())){
                        mSignInWithEmail.setEnabled(true);
                    } else {
                        mSignInWithEmail.setEnabled(false);
                    }
                }
            }
        });

        //Implements OnFocusChanged for pass
        mPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    if (Validation.isEmailValid(mEmailEditText.getText().toString()) && Validation.isPassValid(mPasswordEditText.getText().toString())){
                        mSignInWithEmail.setEnabled(true);
                    } else {
                        mSignInWithEmail.setEnabled(false);
                    }
                }
            }
        });
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
                            Toast.makeText(LoginActivity.this, "Login failed!", Toast.LENGTH_SHORT).show();
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

    public void signUp(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }
}
