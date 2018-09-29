package com.ivanjt.herb.Authentication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ivanjt.herb.R;

public class SignUpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setCustomActionBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.complete_sign_up, menu);
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
