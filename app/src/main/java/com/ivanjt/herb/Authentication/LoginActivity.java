package com.ivanjt.herb.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.ivanjt.herb.R;


/**
 * This Activity provides Sign-In by Email or Google
 *
 * */
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
        mEmailEditText.setOnEditorActionListener(editorActionListener());

        //Implements OnEditorActionListener for pass
        mPasswordEditText.setOnEditorActionListener(editorActionListener());

        //Implements OnFocusChanged for email
        mEmailEditText.addTextChangedListener(textChangedListener());

        //Implements OnFocusChanged for pass
        mPasswordEditText.addTextChangedListener(textChangedListener());
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
                if (Validation.isEmailValid(mEmailEditText.getText().toString()) && Validation.isPassValid(mPasswordEditText.getText().toString())){
                    mSignInWithEmail.setEnabled(true);
                } else {
                    mSignInWithEmail.setEnabled(false);
                }
            }
        };
    }

    private TextView.OnEditorActionListener editorActionListener() {
        return new TextView.OnEditorActionListener() {
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
        };
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

                //Sign in with email
                mAuth.signInWithEmailAndPassword(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "SignIn as " + mEmailEditText.getText().toString(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Login failed!", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });

                break;
            case R.id.bt_sign_in_by_google: //If user sign in with Google Account
                break;
        }

        //Sign out, must be delete (only for testing)
        mAuth.signOut();
    }

    public void signUp(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }
}
