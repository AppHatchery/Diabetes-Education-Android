package edu.emory.diabetes.education.presentation

import android.os.Bundle
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
import edu.emory.diabetes.education.domain.model.Lesson
import edu.emory.diabetes.education.domain.model.Quiz
import edu.emory.diabetes.education.presentation.fragments.basic.BasicFragmentDirections
import edu.emory.diabetes.education.presentation.fragments.basic.BasicNavigator
import edu.emory.diabetes.education.presentation.fragments.basic.Event

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BasicNavigator {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.fragmentContainerView)
        binding.bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, AppBarConfiguration(navController.graph))
//        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.whatIsDiabetes,
                R.id.quizQuestionFragment,
                R.id.resourceMustHaveFragment,
                R.id.orientationFragment,
                R.id.agendaFragment,
                R.id.medicalTeamFragment,
                R.id.lifeIsFragment,
                R.id.bloodSugarMonitoringFragment3,
                R.id.diabetesBasicsFragment,
                R.id.managementFragment,
                R.id.nutritionFragment,
                R.id.quizFragment,
                R.id.nutritionWebViewFragment,
                R.id.chapterFinishFragment,
                R.id.chapterFinishManagementFragment,
                R.id.chapterFinishNutritionFragment,
                R.id.managementQuizQuestionFragment,
                R.id.managementQuizFragment2,
                R.id.nutritionQuizQuestionsFragment,
                R.id.quizNutritionFragment,
                R.id.resourceWebViewFragment
                -> binding.bottomNavigationView.visibility = View.GONE
                else -> binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun invoke(lesson: Lesson?, quiz: Quiz?, event: Event) {

        when (event) {
            Event.Quiz ->
                quiz?.let {
                    BasicFragmentDirections
                        .actionDiabetesBasicsFragmentToQuizFragment(it).apply {
                            navController.navigate(this)
                        }
                }


            Event.Lesson ->
                lesson?.let {
                    BasicFragmentDirections
                        .actionDiabetesBasicsFragmentToWhatIsDiabetes(it).apply {
                            navController.navigate(this)
                        }
                }
        }
    }

}