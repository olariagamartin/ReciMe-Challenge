package com.themarto.recimechallenge.features.search.model

import com.themarto.recimechallenge.data.model.DietaryAttribute

sealed class SearchScreenParam {
    object SearchBar : SearchScreenParam()
    data class Filter(val filter: DietaryAttribute) : SearchScreenParam()
    object None : SearchScreenParam()
}