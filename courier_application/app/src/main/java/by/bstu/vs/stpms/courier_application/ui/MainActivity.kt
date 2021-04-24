package by.bstu.vs.stpms.courier_application.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.bstu.vs.stpms.courier_application.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_order, R.id.navigation_profile, R.id.navigation_details
            )
        )

        navController.addOnDestinationChangedListener { controller: NavController?, destination: NavDestination?, arguments: Bundle? ->
            val fragmentsWithNavView: MutableList<String> = ArrayList()
            fragmentsWithNavView.add(getString(R.string.title_order))
            fragmentsWithNavView.add(getString(R.string.title_profile))
            val currentFragment =
                navController.currentDestination!!.label.toString()
            var visibility = View.GONE
            if (fragmentsWithNavView
                    .stream()
                    .anyMatch { foundTitle: String? -> currentFragment == foundTitle }
            ) {
                visibility = View.VISIBLE
            }

            navView.visibility = visibility

            if (navController.currentDestination?.label.toString() == getString(R.string.fragment_splash)) {
                supportActionBar?.hide()
            } else {
                supportActionBar?.show()
            }

        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

}