package edu.emory.diabetes.education.presentation.fragments.newResources.nav

sealed class NewResourcesScreen(val route: String){
    object NewResourcesMain: NewResourcesScreen("new_resources_main")
    object CourseList: NewResourcesScreen("course_list")
    object ChapterContent : NewResourcesScreen("chapter_content")
    object ChapterFinish : NewResourcesScreen("chapter_finish")
}