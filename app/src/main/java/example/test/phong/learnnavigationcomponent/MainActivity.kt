package example.test.phong.learnnavigationcomponent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        setupNavigation()
    }

    // sync menu item in
    override fun onSupportNavigateUp(): Boolean {
        // sync click on drawer with nav graph
        return NavigationUI.navigateUp(drawerLayout, findNavController(R.id.nav_host_fragment))
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun setupNavigation() {
        val navController = findNavController(R.id.nav_host_fragment)

        // update action bar to reflect navigation
        setupActionBarWithNavController(this, navController, drawerLayout)

        navigationView.setNavigationItemSelectedListener {menuItem ->
            menuItem.isChecked = true
            drawerLayout.closeDrawers()
            true
        }

        NavigationUI.setupWithNavController(navigationView, navController)
    }
}
