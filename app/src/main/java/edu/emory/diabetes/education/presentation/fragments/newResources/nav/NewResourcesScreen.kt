package edu.emory.diabetes.education.presentation.fragments.newResources.nav

sealed class NewResourcesScreen(val route: String){
    object NewResourcesMain: NewResourcesScreen("new_resources_main")
    object CourseFlow : NewResourcesScreen("course_flow/{courseId}") {
        fun createRoute(courseId: Int) = "course_flow/$courseId"
    }
    object CourseList : NewResourcesScreen("course_list/{courseId}") {
        fun createRoute(courseId: Int) = "course_list/$courseId"
    }

    object ChapterContent : NewResourcesScreen("chapter_content/{courseId}") {
        fun createRoute(courseId: Int) = "chapter_content/$courseId"
    }

    object ChapterFinish : NewResourcesScreen("chapter_finish/{courseId}") {
        fun createRoute(courseId: Int) = "chapter_finish/$courseId"
    }

    object MedicalReferences: NewResourcesScreen("medical_references")
//    object CourseList: NewResourcesScreen("course_list")
//    object ChapterContent : NewResourcesScreen("chapter_content")
//    object ChapterFinish : NewResourcesScreen("chapter_finish")
}