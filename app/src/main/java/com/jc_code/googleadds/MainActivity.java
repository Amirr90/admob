package com.jc_code.googleadds;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.jc_code.googleadds.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

    }

    @Override
    protected void onStop() {
        super.onStop();
        navController = Navigation.findNavController(this, R.id.fragment_navigation);
        NavigationUI.setupActionBarWithNavController(this, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }
}