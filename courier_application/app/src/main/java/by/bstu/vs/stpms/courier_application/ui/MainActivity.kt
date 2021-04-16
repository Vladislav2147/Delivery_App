package by.bstu.vs.stpms.courier_application.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.bstu.vs.stpms.courier_application.R
import by.bstu.vs.stpms.courier_application.ui.main.order.OrderFragment
import by.bstu.vs.stpms.courier_application.ui.main.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

}