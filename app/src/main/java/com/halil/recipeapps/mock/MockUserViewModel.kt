package com.halil.recipeapps.mock

import com.halil.recipeapps.data.model.Recipe
import com.halil.recipeapps.ui.viewmodel.UserViewModel
import com.halil.recipeapps.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MockUserViewModel : UserViewModel(MockUserRepository(), MockRecipeDao()) {
    override val recipes: StateFlow<Resource<List<Recipe>>> =
        MutableStateFlow(
            Resource.Success(
                listOf(
                    Recipe(
                        1,
                        "Mock Recipeee",
                        "Mock Ingredients",
                        "Mock Instructions",
                        20.0,
                        200.0,
                        10.0,
                        "drawable/logo_recipe"
                    ),
                    Recipe(
                        2,
                        "Mock Recipeee",
                        "Mock Ingredients",
                        "Mock Instructions",
                        20.0,
                        200.0,
                        10.0,
                        "drawable/logo_recipe"
                    ),
                    Recipe(
                        3,
                        "Mock Recipeee",
                        "Mock Ingredients",
                        "Mock Instructions",
                        20.0,
                        200.0,
                        10.0,
                        "drawable/logo_recipe"
                    ),
                    Recipe(
                        4,
                        "Mock Recipeee",
                        "Mock Ingredients",
                        "Mock Instructions",
                        20.0,
                        200.0,
                        10.0,
                        "drawable/logo_recipe"
                    ),
                )
            )
        )
}
