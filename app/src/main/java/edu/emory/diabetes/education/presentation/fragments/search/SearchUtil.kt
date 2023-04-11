package edu.emory.diabetes.education.presentation.fragments.search

import android.content.Context

class SearchUtil {
    companion object{
        object ParseHtml{

            fun readHtmlFromAssets(context: Context, fileName: String): String {
                return context.assets.open(fileName).bufferedReader().use {
                    it.readText()
                }
            }
            /**
             *  Removes extra space found at the first element in a string
             * */
            fun formatString(string: String): String {
                return if (string.first() == ' ') {
                    string.replaceRange(0, 1, "")
                } else {
                    string
                }
            }

            fun borrowChar(char:String,string: String):String{
                return if (string.contains(char)) {
                    string.replace(char,"∧")
                } else{
                    string
                }
            }
            fun resetChar(char:String,string: String):String{
                return if (string.contains("∧")) string.replace("∧","'")
                else string
            }
        }
        object ResultSearch{
            fun countOccurrences(s: String, ch: Char): Int {
                return s.count { it == ch }
            }
        }

    }
}