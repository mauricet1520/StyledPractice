package com.coolreecedev.styledpractice.util

import android.content.Context

class FileHelper {
    companion object{
        fun getTextFromResources(context: Context, resourceId: Int): String {
            return context.resources.openRawResource(resourceId).use {
                it.bufferedReader().use {
                    it.readText()
                }
            }
        }


        fun getTextFromAssest(context: Context, fileName: String): String {
            return context.assets.open(fileName).use {
                it.bufferedReader().use {
                    it.readText()
                }
            }
        }

        fun getPdfUrl(): String {
            return "https://drive.google.com/file/d/1RDAk5NicYpjomlTGEh8ydSjIokiFchvW/view?usp=sharing"
        }

        fun getBlogUrl(): String {
            return "https://loveefashion.com/myblog/"
        }
    }
}