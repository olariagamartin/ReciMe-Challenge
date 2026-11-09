package com.themarto.recimechallenge.features.search.model

import com.themarto.recimechallenge.data.model.DietaryAttribute
import kotlin.collections.contains

sealed class SearchScreenParam {
    object SearchBar : SearchScreenParam()
    data class Filter(val filter: DietaryAttribute) : SearchScreenParam()
    object None : SearchScreenParam()
}

fun getSearchScreenParam(param: String?): SearchScreenParam {
    return if (param in DietaryAttribute.entries.map { it.name })
        SearchScreenParam.Filter(DietaryAttribute.valueOf(param!!))
    else SearchScreenParam.SearchBar
}