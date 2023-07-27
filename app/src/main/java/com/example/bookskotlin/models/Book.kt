package com.example.bookskotlin.models

import java.io.Serializable


data class Book(
    val author: String,
    val cover: String,
    val cover_color: String,
    val cover_thumb: String,
    val epoch: String,
    val full_sort_key: String,
    val genre: String,
    val has_audio: Boolean,
    val href: String,
    val kind: String,
    val liked: Any?,
    val simple_thumb: String,
    val slug: String,
    val title: String,
    val url: String?
): Serializable