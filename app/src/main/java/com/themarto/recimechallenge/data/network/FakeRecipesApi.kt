package com.themarto.recimechallenge.data.network

import android.content.Context
import com.themarto.recimechallenge.data.model.DietaryAttribute
import com.themarto.recimechallenge.data.model.ExploreFeed
import com.themarto.recimechallenge.data.model.Ingredient
import com.themarto.recimechallenge.data.model.Rating
import com.themarto.recimechallenge.data.model.Recipe
import com.themarto.recimechallenge.data.model.RecipeSearch
import com.themarto.recimechallenge.data.model.Result
import org.json.JSONArray
import org.json.JSONObject

// simulates network connection
class FakeRecipesApi(context: Context) : RecipesApi {

    private val recipes: List<Recipe> = loadRecipes(context)

    override suspend fun getExploreFeed(): Result<ExploreFeed> {
        val featured = recipes.firstOrNull()
            ?: return Result.Error("No recipes available")
        return Result.Success(
            ExploreFeed(
                recipeOfTheDay = featured,
                recommended = recipes.drop(1)
            )
        )
    }

    override suspend fun searchRecipes(search: RecipeSearch): Result<List<Recipe>> {
        return Result.Success(recipes.filter { it.matches(search) })
    }

    override suspend fun getRecipe(id: Int): Result<Recipe> {
        val recipe = recipes.find { it.id == id.toString() }
            ?: return Result.Error("Recipe $id not found")
        return Result.Success(recipe)
    }

    private fun loadRecipes(context: Context): List<Recipe> {
        val json = context.assets.open(RECIPES_FILE).bufferedReader().use { it.readText() }
        return parseRecipes(json)
    }

    private fun Recipe.matches(search: RecipeSearch): Boolean {
        return matchesQuery(search.query) &&
            matchesDietaryPreferences(search.dietaryPreferences) &&
            matchesServings(search.minServings, search.maxServings) &&
            matchesIncludedIngredients(search.includeIngredients) &&
            matchesExcludedIngredients(search.excludeIngredients)
    }

    private fun Recipe.matchesQuery(query: String): Boolean {
        val normalized = query.trim()
        if (normalized.isEmpty()) return true
        return title.contains(normalized, ignoreCase = true) ||
            ingredients.any { it.name.contains(normalized, ignoreCase = true) } ||
            instructions.any { it.contains(normalized, ignoreCase = true) }
    }

    private fun Recipe.matchesDietaryPreferences(preferences: List<DietaryAttribute>): Boolean {
        return preferences.all { preference ->
            dietaryAttributes.any { attribute ->
                attribute == preference
            }
        }
    }

    private fun Recipe.matchesServings(minServings: Int?, maxServings: Int?): Boolean {
        val aboveMin = minServings?.let { servings >= it } ?: true
        val belowMax = maxServings?.let { servings <= it } ?: true
        return aboveMin && belowMax
    }

    private fun Recipe.matchesIncludedIngredients(required: List<String>): Boolean {
        return required.all { ingredient ->
            ingredients.any { it.name.contains(ingredient.trim(), ignoreCase = true) }
        }
    }

    private fun Recipe.matchesExcludedIngredients(excluded: List<String>): Boolean {
        return excluded.none { ingredient ->
            ingredients.any { it.name.contains(ingredient.trim(), ignoreCase = true) }
        }
    }

    private companion object {
        const val RECIPES_FILE = "recipes.json"

        fun parseRecipes(json: String): List<Recipe> {
            val array = JSONArray(json)
            return List(array.length()) { index -> array.getJSONObject(index).toRecipe() }
        }

        fun JSONObject.toRecipe(): Recipe {
            return Recipe(
                id = get("id").toString(),
                title = getString("title"),
                imgUrl = getString("imgUrl"),
                servings = getInt("servings"),
                rating = getJSONObject("rating").toRating(),
                timeInMinutes = getInt("timeInMinutes"),
                dietaryAttributes = getJSONArray("dietaryAttributes").toDietaryAttrList(),
                saved = optBoolean("saved", false),
                ingredients = getJSONArray("ingredients").toIngredientList(),
                instructions = getJSONArray("instructions").toStringList()
            )
        }

        fun JSONObject.toRating(): Rating {
            return Rating(
                stars = getDouble("stars").toFloat(),
                reviews = getInt("reviews")
            )
        }

        fun JSONArray.toDietaryAttrList(): List<DietaryAttribute> {
            return List(length()) { index ->
                when (getString(index)) {
                    "VEGAN" -> DietaryAttribute.VEGAN
                    "VEGETARIAN" -> DietaryAttribute.VEGETARIAN
                    "GLUTEN_FREE" -> DietaryAttribute.GLUTEN_FREE
                    "DAIRY_FREE" -> DietaryAttribute.DAIRY_FREE
                    "KETO" -> DietaryAttribute.KETO
                    "HIGH_PROTEIN" -> DietaryAttribute.HIGH_PROTEIN
                    else -> throw IllegalArgumentException(
                        "Unknown dietary attribute: ${
                            getString(
                                index
                            )
                        }"
                    )
                }
            }
        }

        fun JSONArray.toStringList(): List<String> {
            return List(length()) { index -> getString(index) }
        }

        fun JSONArray.toIngredientList(): List<Ingredient> {
            return List(length()) { index ->
                getJSONObject(index).let { ingredient ->
                    Ingredient(
                        name = ingredient.getString("name"),
                        amount = ingredient.get("amount").toString(),
                        unit = ingredient.getString("unit")
                    )
                }
            }
        }
    }
}
