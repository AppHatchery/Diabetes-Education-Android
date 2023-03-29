package edu.emory.diabetes.education.presentation

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), EventNavigator {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.fragmentContainerView)
        binding.bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, AppBarConfiguration(navController.graph))
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        GlobalScope.launch {
            val html = readHtmlFromAssets(this@MainActivity, "pages/index.html")
            val doc = Jsoup.parse(html);
            val paragraphs = doc.select("p,li");
            val array = mutableListOf<String>()
            val img = doc.getElementsByTag("img");
            img.forEach { element ->
                //Log.e("IMG TAGS", element.attr("alt"))
            }
            paragraphs.forEach { it ->
                //Log.e("Occurences","${countOccurrences(it.text(),'.')}  ELEMENT ${it.tagName()}   ${it.text()}")
                if (countOccurrences(it.text(), '.') > 1) {
                    val block = it.text().split(".")
                    block.forEach { item ->
                        if (item.isNotEmpty()) array.add(item)
                    }
                } else {
                    array.add(it.text())
                }

            }
            val newArray = mutableListOf<String>()
            array.forEach {
                newArray.add(fixString(it))
                //Log.e("Elements", it)9
            }
            newArray.forEach {
                //Log.e("Elements2", it)
            }
            //Log.e("READ FROM ASSETS",readHtmlFromAssets(this@MainActivity,"pages/index.html"))

        }
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
                R.id.quizFragment,
                R.id.nutritionWebViewFragment,
                R.id.chapterFinishFragment,
                R.id.chapterFinishManagementFragment,
                R.id.chapterFinishNutritionFragment,
                R.id.managementQuizQuestionFragment,
                R.id.managementQuizFragment2,
                R.id.nutritionQuizQuestionsFragment,
                R.id.quizNutritionFragment,
                R.id.resourceWebViewFragment,
                R.id.quizManagementFinishFragment,
                R.id.quizNutritionFinishFragment,
                R.id.quizFinishFragment
                -> binding.bottomNavigationView.visibility = View.GONE
                else -> binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


    override fun invoke(eventNav: EventNav) {
        MainActivityEventNav(eventNav, navController).invoke()
    }

    fun readHtmlFromAssets(context: Context, fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use {
            it.readText()
        }
    }


    //Utitlity functions
    fun countOccurrences(s: String, ch: Char): Int {
        return s.filter { it == ch }.count()
    }

    private fun fixString(string: String): String {
        return if (string.first() == ' ') {
            Log.e("FOUND", "FOUND SPACE")
            string.replaceRange(0, 1, "")
        } else {
            string
        }
    }

}