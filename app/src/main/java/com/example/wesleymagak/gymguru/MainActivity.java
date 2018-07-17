package com.example.wesleymagak.gymguru;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    FragmentManager loginFragmentManager = getSupportFragmentManager();
    Toolbar toolbar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_login:
                    loginFragmentManager.beginTransaction().replace(R.id.login_content, new LoginFragment()).commit();
                    return true;
                case R.id.navigation_register:
                    loginFragmentManager.beginTransaction().replace(R.id.login_content, new RegisterFragment()).commit();
                    return true;
                case R.id.navigation_settings:
                    loginFragmentManager.beginTransaction().replace(R.id.login_content, new SettingsFragment()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=(Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("GymGuru");
        setSupportActionBar(toolbar);
        //toolbar.setLogo(R.drawable.gym_guru_black_text_copy);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            toolbar.setElevation(10.f);
        }

        loginFragmentManager.beginTransaction().replace(R.id.login_content, new LoginFragment()).commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.login_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

}
