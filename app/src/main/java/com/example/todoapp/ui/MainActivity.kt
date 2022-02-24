package com.example.todoapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.todoapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupActionBarWithNavController(findNavController(R.id.navhost))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navhost)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    // Animation after splash
    override fun onResume() {
        super.onResume()
        overridePendingTransition(R.anim.from_right, R.anim.to_left)
    }
}