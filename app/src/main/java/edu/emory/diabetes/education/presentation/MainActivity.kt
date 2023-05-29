package edu.emory.diabetes.education.presentation

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), EventNavigator {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var mMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
                R.id.quizFinishFragment
                -> binding.bottomNavigationView.visibility = View.GONE
                else -> binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.title = destination.label
            negotiator(destination.label.toString())
        }
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
            showMenuItem()
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

}