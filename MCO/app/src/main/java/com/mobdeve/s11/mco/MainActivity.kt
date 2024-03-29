package com.mobdeve.s11.mco

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.mobdeve.s11.mco.data.CartData.Companion.cartItems
import com.mobdeve.s11.mco.databinding.ActivityMainBinding
import com.mobdeve.s11.mco.model.Cart

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var sessionManager: SessionManagement


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        sessionManager = SessionManagement(applicationContext)
        sessionManager.logout()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.shop -> {
                // Perform your action when the "shop" item is clicked
                // For example, you can navigate to a new activity
                if(sessionManager.isLoggedIn()){
                    findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.Menu)
                }
                else{
                    Toast.makeText(applicationContext, "You are not signed in!",
                        Toast.LENGTH_SHORT).show();
                }
                true
            }
            R.id.profile -> {
                // Handle other menu items as needed
                if(sessionManager.isLoggedIn()){
                    findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.profileFragment)
                }
                else{
                    Toast.makeText(applicationContext, "You are not signed in!",
                        Toast.LENGTH_SHORT).show();
                }
                true
            }
            R.id.logout -> {
                // Handle other menu items as needed
                //Implement logout here
                if(sessionManager.isLoggedIn()){
                    sessionManager.clearAddress()
                    sessionManager.logout()
                    cartItems.clear()
                    findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.mainActivity)
                }
                else{
                    Toast.makeText(applicationContext, "You are not signed in!",
                        Toast.LENGTH_SHORT).show();
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}