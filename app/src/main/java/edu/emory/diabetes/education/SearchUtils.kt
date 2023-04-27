package edu.emory.diabetes.education

import android.content.Context
import org.jsoup.Jsoup

object SearchUtils {

    //Utility functions
    fun readHtmlFromAssets(context: Context, fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use {
            it.readText()
        }
    }
    fun countOccurrences(s: String, ch: Char): Int {
        return s.filter { it == ch }.count()
    }
    fun fixString(string: String): String {
        return if (string.first() == ' ') {
            string.replaceRange(0, 1, "")
        } else {
            string
        }
    }

    class HtmlParser(private val context: Context, private val pageUrl: String) {
        fun parseHtml(): String {
            val filepath = "pages/$pageUrl.html"
            val html = readHtmlFromAssets(context, filepath)
            val doc = Jsoup.parse(html)
            val paragraphs = doc.select("p,li,img,tbody")
            val array = mutableListOf<String>()
            paragraphs.forEach { element ->
                if (element.tagName().equals("tbody")) {

                }
                if (element.tagName().equals("img")) {
                    array.add(element.attr("alt"))
                } else {
                    if (countOccurrences(element.text(), '.') > 1) {
                        val block = element.text().split(".")
                        block.forEach { item ->
                            if (item.isNotEmpty()) array.add(item)
                        }
                    } else {
                        array.add(element.text())
                    }
                }
            }
            val newArray = mutableListOf<String>()
            array.forEach {
                if (it.isNotEmpty()) {
                    var string = ""
                    if (fixString(it).contains("'")) {
                        string = fixString(it).replace("'", "∧")
                        newArray.add(string)
                    } else {
                        string = fixString(it)
                        newArray.add(string)
                    }
                }
            }
            return newArray.joinToString("_")
        }
    }
}