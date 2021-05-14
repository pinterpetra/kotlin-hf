package com.jetbrains.handson.website

data class BlogEntry(val headline: String, val all: String, val seen: String)

val blogEntries = mutableListOf(
    BlogEntry(
        "The Series",
        "this much",
        "this much"
    )
)