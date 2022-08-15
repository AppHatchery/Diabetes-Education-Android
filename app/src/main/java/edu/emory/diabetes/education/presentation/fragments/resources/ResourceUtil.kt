package edu.emory.diabetes.education.presentation.fragments.resources

import edu.emory.diabetes.education.domain.model.FoodDiary
import edu.emory.diabetes.education.domain.model.MustHaveApp

object ResourceUtil {

    val mustHaveApp = listOf(
        MustHaveApp(
            "My Sugar",
            "the diabetes management app made for people with diabetes by people with diabetes."
        ),
        MustHaveApp(
            "Calorie King",
            "Find nutrition facts for your favorite brands and fast-food restaurants."
        ),
        MustHaveApp(
            "MyFitness Pal",
            "Track progress toward your nutrition, water, fitness, and weight loss goals "
        )
    )


    val foodDiary = listOf(
        FoodDiary(
            "My Sugar",
            "the diabetes management app made for people with diabetes by people with diabetes."
        ),
        FoodDiary(
            "Calorie King",
            "Find nutrition facts for your favorite brands and fast-food restaurants."
        ),
        FoodDiary(
            "MyFitness Pal",
            "Track progress toward your nutrition, water, fitness, and weight loss goals "
        )
    )
}