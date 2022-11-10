package edu.emory.diabetes.education.presentation.fragments.resources

import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.domain.model.Communities
import edu.emory.diabetes.education.domain.model.FoodDiary
import edu.emory.diabetes.education.domain.model.MustHaveApp
import edu.emory.diabetes.education.domain.model.MustHaveAppPage

object ResourceUtil {

    val mustHaveApp = listOf(
        MustHaveApp(
            "My Sugar",
            "the diabetes management app made for people with diabetes by people with diabetes.",
            image = R.drawable.im_sugar
        ),
        MustHaveApp(
            "Calorie King",
            "Find nutrition facts for your favorite brands and fast-food restaurants.",
            image = R.drawable.im_calorie
        ),
        MustHaveApp(
            "MyFitness Pal",
            "Track progress toward your nutrition, water, fitness, and weight loss goals ",
            image = R.drawable.im_fitnes
        )
    )


    val foodDiary = listOf(
        FoodDiary(
            "Low Carb Snack Combinations",
            "the diabetes management app made for people with diabetes by people with diabetes.",
            "low_carb_snack_combinations",
            image = R.drawable.im_low_carbs

        ),
        FoodDiary(
            "Know your carbs",
            "Find nutrition facts for your favorite brands and fast-food restaurants.",
            "know_your_carbs",
            image = R.drawable.im_know_your_carbs
        ),
        FoodDiary(
            "Foods that raise Blood Sugar",
            "Track progress toward your nutrition, water, fitness, and weight loss goals.",
            "foods_that_raise_blood_sugar",
            image = R.drawable.im_food_that_raise_bloods_sugar
        ),
        FoodDiary(
            "Foods that don't raise Blood Sugar",
            "Track progress toward your nutrition, water, fitness, and weight loss goals",
            "foods_that_don't_raise_blood_sugar",
            image = R.drawable.im_food_that_dont_raise
        )
    )

    val communities = listOf(
        Communities(
            0,
            "A nonprofit organization serving children and teens living with type 1 diabetes ",
            R.drawable.im_strong4life,
            "https://www.strong4life.com/en"
        ),
        Communities(
            1,
            "A nonprofit organization serving children and teens living with type 1 diabetes ",
            R.drawable.im_jrdf,
            "https://www.jdrf.org/georgiasouthcarolina/"
        ),
        Communities(
            2,
            "A nonprofit organization serving children and teens living with type 1 diabetes ",
            R.drawable.im_camp_kudze,
            "https://www.campkudzu.org/newly-diagnosed-resource-page/"
        )
    )

    val mustHaveAppPage = listOf(
        MustHaveAppPage(
            "My Sugar",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text.",
            image = R.drawable.im_sugar
        ),
        MustHaveAppPage(
            "Calorie King",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text.",
            image = R.drawable.im_calorie
        ),
        MustHaveAppPage(
            "My Fitness Pal",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text.",
            image = R.drawable.im_fitnes
        )

    )


}