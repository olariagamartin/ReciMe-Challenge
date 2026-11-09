package com.themarto.recimechallenge.features.search.model

import com.themarto.recimechallenge.data.model.DietaryAttribute
import com.themarto.recimechallenge.features.search.SearchDestination
import kotlin.collections.contains

sealed class SearchScreenParam {
    object SearchBar : SearchScreenParam()
    data class Filter(val filter: DietaryAttribute) : SearchScreenParam()
    object None : SearchScreenParam()
}

fun getSearchScreenParam(param: String?): SearchScreenParam {
    return when (param) {
        in DietaryAttribute.entries.map { it.name } -> SearchScreenParam.Filter(
            DietaryAttribute.valueOf(
                param!!
            )
        )
        SearchDestination.PARAM_SEARCH_BAR -> {
            SearchScreenParam.SearchBar
        }
        else -> {
            SearchScreenParam.None
        }
    }
}