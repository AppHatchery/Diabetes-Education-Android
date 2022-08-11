package edu.emory.diabetes.education

object Ext {

    fun getPathUrl(filename: String) =
        String.format("file:///android_asset/pages/%s%s", filename, htmlExt)
}