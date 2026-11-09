package com.themarto.recimechallenge.features.search

import com.themarto.recimechallenge.data.model.DietaryAttribute
import com.themarto.recimechallenge.navigation.Destination

object SearchDestination : Destination {
    const val PARAM_OPEN_WITH = "openWith"

    override val route: String = "search/{$PARAM_OPEN_WITH}"
    const val PARAM_SEARCH_BAR = "searchBar"
    fun withFilter(filter: DietaryAttribute) = "search/${filter.name}"
    fun withSearchBar() = "search/$PARAM_SEARCH_BAR"
}