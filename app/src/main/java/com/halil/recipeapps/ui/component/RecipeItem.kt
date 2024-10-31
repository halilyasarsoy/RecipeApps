package com.halil.recipeapps.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.halil.recipeapps.R
import com.halil.recipeapps.data.model.Recipe
import com.halil.recipeapps.ui.viewmodel.UserViewModel

@Composable
fun RecipeItem(
    recipe: Recipe,
    onClick: () -> Unit,
    userViewModel: UserViewModel,
    onFavoriteClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = recipe.name,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            val imagePainter = if (recipe.image_url == "drawable/logo_recipe") {
                painterResource(id = R.drawable.logo_recipe)
            } else {
                rememberAsyncImagePainter(recipe.image_url)
            }

            Image(
                painter = imagePainter,
                contentDescription = recipe.name,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )

            IconButton(
                onClick = { onFavoriteClick(!recipe.isFavorite) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(
                    imageVector = if (recipe.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Toggle Favorite"
                )
            }
        }
    }
}

