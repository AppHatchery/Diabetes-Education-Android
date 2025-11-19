package edu.emory.diabetes.education

object Ext {

    fun getPathUrl(filename: String) =
        String.format("file:///android_asset/pages/%s%s", filename, htmlExt)


    fun getAmericanDiabetesUrl() =
        String.format(American_Diabetes_Association_url)
    fun getAppHatcheryUrl() =
        String.format(AppHatchery_url)
}