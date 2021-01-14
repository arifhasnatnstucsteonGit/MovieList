package com.arifhasnat.movielist.models.movieList

import java.util.*

class MovieList {
    var page: String? = null
    var total_pages: String? = null
    var results: ArrayList<Results>? = null
    var total_results: String? = null

    override fun toString(): String {
        return "MoiveList{" +
                "page='" + page + '\'' +
                ", total_pages='" + total_pages + '\'' +
                ", results=" + results +
                ", total_results='" + total_results + '\'' +
                '}'
    }
}