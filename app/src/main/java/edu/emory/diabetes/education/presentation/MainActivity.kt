package edu.emory.diabetes.education.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updatePadding
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.ActivityMainBinding
import edu.emory.diabetes.education.notifications.CheckReminderReceiver
import edu.emory.diabetes.education.presentation.fragments.sickDay.SickDayFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), EventNavigator {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var mMenu: Menu? = null

    private val TAG = "Main Activity"

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d(TAG, "POST_NOTIFICATIONS permission granted")
        } else {
            Log.w(TAG, "POST_NOTIFICATIONS permission DENIED")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle =
                SystemBarStyle.light(
                    android.graphics.Color.TRANSPARENT,
                    android.graphics.Color.TRANSPARENT
                )
        )

        requestNotificationPermission()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.bottomNavigationView) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(bottom = bars.bottom)
            insets
        }
        navController = findNavController(R.id.fragmentContainerView)
        binding.bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, AppBarConfiguration(navController.graph))
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.SharedWebpageHostFragmentForSearch,
                R.id.quizQuestionFragment,
              //  R.id.resourceMustHaveFragment,
                R.id.orientationFragment,
                R.id.agendaFragment,
                R.id.medicalTeamFragment,
                R.id.lifeIsFragment,
               // R.id.bloodSugarMonitoringFragment3,
                R.id.quizFragment,
              //  R.id.nutritionWebViewFragment,
                R.id.chapterFinishFragment,
                R.id.chapterFinishManagementFragment,
                R.id.chapterFinishNutritionFragment,
                R.id.managementQuizQuestionFragment,
                R.id.managementQuizFragment2,
                R.id.nutritionQuizQuestionsFragment,
                R.id.quizNutritionFragment,
                //R.id.resourceWebViewFragment,
                R.id.quizManagementFinishFragment,
                R.id.quizNutritionFinishFragment,
                R.id.quizFinishFragment,
                R.id.sickDayFragment,
                R.id.newCalculatorFragment3,
                R.id.mainFragment
                -> binding.bottomNavigationView.visibility = View.GONE
                else -> binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.title = destination.label
            negotiator(destination.label.toString())
        }

        handleReminderIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleReminderIntent(intent)
    }

    private fun handleReminderIntent(intent: Intent) {
        val route = intent.getStringExtra(CheckReminderReceiver.EXTRA_ROUTE)
        if (!route.isNullOrEmpty()) {
            // Navigate to the SickDay destination
            navigateToSickDay()
        }
    }

    private fun navigateToSickDay() {
        navController.navigate(R.id.sickDayFragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


    override fun invoke(eventNav: EventNav) {
        MainActivityEventNav(eventNav, navController).invoke()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.about_us, menu)
        mMenu = menu
        negotiator(supportActionBar?.title.toString())
        return true
    }

    private fun negotiator(fragmentName:String){
        if(fragmentName.equals("Welcome!")) {
            supportActionBar?.hide()
        }else{
            hideMenuItem()
        }
    }

    private fun hideMenuItem() {
        val item: MenuItem? = mMenu?.findItem(R.id.menu_overflow)
        item?.isVisible = false
    }

    private fun showMenuItem() {
        val item: MenuItem? = mMenu?.findItem(R.id.menu_overflow)
        item?.isVisible = true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_overflow -> {
                navController.navigate(R.id.aboutUsHomeFragment)
                binding.bottomNavigationView.visibility = View.GONE
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun requestNotificationPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(
                this,
                    Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
                ){
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

}