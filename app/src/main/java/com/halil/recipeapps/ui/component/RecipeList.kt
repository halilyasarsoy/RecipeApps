package com.halil.recipeapps.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.halil.recipeapps.data.model.Recipe
import com.halil.recipeapps.ui.viewmodel.UserViewModel

@Composable
fun RecipeList(recipes: List<Recipe>, userViewModel: UserViewModel, onClick: (Recipe) -> Unit) {
    if (recipes.isEmpty()) {
        Text("No recipes found")
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(recipes.chunked(2)) { pair ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    RecipeItem(
                        recipe = pair[0],
                        userViewModel = userViewModel,
                        onClick = { onClick(pair[0]) },
                        onFavoriteClick = { isFavorite ->
                            userViewModel.updateFavoriteStatus(pair[0], isFavorite)
                        },
                        modifier = Modifier.weight(1f)
                    )

                    if (pair.size > 1) {
                        RecipeItem(
                            recipe = pair[1],
                            userViewModel = userViewModel,
                            onClick = { onClick(pair[1]) },
                            onFavoriteClick = { isFavorite ->
                                userViewModel.updateFavoriteStatus(pair[1], isFavorite)
                            },
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
