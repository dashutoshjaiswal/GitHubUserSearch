package com.example.githubusersearch.ui.interfaces

/** Allows to do search from activity's SearchView */
interface ISearchListenerFragment {
    fun doSearch(searchQuery: String)
}