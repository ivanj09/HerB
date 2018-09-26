package com.ivanjt.herb;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/***
 * This is only for testing Firebase Login System
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    //Declare FirebaseAuth object
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find TextView in xml using findViewById
        final TextView mUserNameTextView = this.findViewById(R.id.tv_user_name);

        //Get instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        //Sign in with existing user
        mAuth.signInWithEmailAndPassword("ivanjth26@gmail.com", "ulala999")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){ //If the user login successfully
                            Log.d(TAG, "signInWithEmailAddress:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            mUserNameTextView.setText(user.getEmail());
                        } else { //If user fails
                            Log.d(TAG, "signInWithEmailAddress:fail");
                            Toast.makeText(MainActivity.this, "Login failed!.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
