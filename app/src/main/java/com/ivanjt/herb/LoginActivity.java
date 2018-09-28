package com.ivanjt.herb;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Reference object to items in xml
        mEmailEditText = this.findViewById(R.id.et_email_address);
        mPasswordEditText = this.findViewById(R.id.et_password);
    }

    public void signInWith(View view) {
        int id = view.getId();

        //Get current instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

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
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        //Sign in with email and password
                        mAuth.signInWithEmailAndPassword(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString());

                        try {
                            Thread.sleep(200);
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
