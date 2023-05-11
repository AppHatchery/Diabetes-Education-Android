package edu.emory.diabetes.education

import android.content.Context
import android.webkit.WebView
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import org.jsoup.Jsoup

object
SearchUtils {

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

                if (element.tagName().equals("img")) {
                    array.add(element.attr("alt"))
                } else if (element.tagName().equals("a")) {

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

            val hashSet = HashSet<String>()
            array.forEach {
                if (it.isNotEmpty()) {
                    var string: String = if (fixString(it).contains("'")) {
                        fixString(it).replace("'", "∧")
                    } else {
                        fixString(it)
                    }
                    // Trim whitespace and periods from end of string
                    while (string.isNotEmpty() && (string.last() == ' ' || string.last() == '.')) {
                        string = string.substring(0, string.length - 1)
                    }
                    hashSet.add(string)
                }
            }
            val newArray = hashSet.toList()

            return newArray.joinToString("_")
        }
    }


    class WebViewSearchHelper {
        fun searchAndScroll(webView: WebView, searchQuery: String) {
            if (searchQuery.isNotEmpty()) {
                webView.findAllAsync(removeHtmlTags(searchQuery))
                webView.setFindListener { activeMatchOrdinal, numberOfMatches, isDone ->
                }
            }
        }

        fun removeHtmlTags(text: String): String {
            return text
                .replace(Regex("<br>|<p>"), " ")
                .replace(Regex("<.*?>"), "")
                .replace(Regex("<[^>]+>"), "")
        }

        fun halfString(string: String): String {
            val halfLength = removeHtmlTags(string).length
            val firstHalf = removeHtmlTags(string).substring(0, halfLength)
            val secondHalf = removeHtmlTags(string).substring(halfLength)
            return firstHalf
        }

        fun halfStringForTable(string: String): String {
            val threeQuartersLength = (removeHtmlTags(string).length * 0.75).toInt()
            return removeHtmlTags(string).substring(0, threeQuartersLength)
        }


    }

    @Module
    @InstallIn(FragmentComponent::class)
    object MyModule {

        @Provides
        fun provideWebViewSearchHelper() = SearchUtils.WebViewSearchHelper()

    }
}