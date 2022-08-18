package edu.emory.diabetes.education.presentation.fragments.resources

import edu.emory.diabetes.education.domain.model.Communities
import edu.emory.diabetes.education.domain.model.FoodDiary
import edu.emory.diabetes.education.domain.model.MustHaveApp
import edu.emory.diabetes.education.domain.model.MustHaveAppPage

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

    val communities = listOf(
        Communities(
            "A nonprofit organization serving children and teens living with type 1 diabetes "
        ),
        Communities(
            "A nonprofit organization serving children and teens living with type 1 diabetes "
        ),
        Communities(
            "A nonprofit organization serving children and teens living with type 1 diabetes "
        )
    )

    val mustHaveAppPage = listOf(
        MustHaveAppPage(
            "My Sugar",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text."
        ),
        MustHaveAppPage(
            "Calorie King",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text."
        ),
        MustHaveAppPage(
            "My Fitness Pal",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text."
        )

    )


}