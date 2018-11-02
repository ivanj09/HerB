package com.ivanjt.herb;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.ivanjt.herb.Authentication.LoginActivity;

public class DashboardActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // Set default fragment
        displayFragment(new HomeFragment());

        mDrawerLayout = this.findViewById(R.id.drawer_layout);

        //Change actionbar home icon
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        //Set NavigationItemSelectedListener to navigationView
        NavigationView navigationView = this.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        displayFragment(new HomeFragment());
                        break;
                    case R.id.history:
                        displayFragment(new HistoryFragment());
                        break;
                    case R.id.log_out:
                        //Sign out current user
                        mAuth.signOut();

                        //Go back to login page
                        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        finish();
                }

                // Close drawer
                mDrawerLayout.closeDrawers();

                // Set title
                setTitle(menuItem.getTitle());

                return true;
            }
        });
    }

    private void displayFragment(Fragment fragment){
        // Insert the fragment by replacing any existing fragment
        if (fragment != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_content, fragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
