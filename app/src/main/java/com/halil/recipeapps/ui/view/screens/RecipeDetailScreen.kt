package com.halil.recipeapps.ui.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.halil.recipeapps.data.model.Recipe

@Composable
fun RecipeDetailScreen(recipe: Recipe?) {
    recipe?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = it.name, style = MaterialTheme.typography.titleLarge) // titleLarge kullanılıyor
            Image(
                painter = rememberAsyncImagePainter(it.image_url),
                contentDescription = it.name,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Text(text = "Ingredients: ${it.ingredients}", style = MaterialTheme.typography.bodyMedium) // bodyMedium kullanılıyor
            Text(text = "Instructions: ${it.instructions}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Protein: ${it.protein}g", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Calories: ${it.kcal}kcal", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Fat: ${it.fat}g", style = MaterialTheme.typography.bodyMedium)
        }
    } ?: run {
        Text(text = "Recipe not found", color = MaterialTheme.colorScheme.error) // MaterialTheme.colorScheme.error kullanılıyor
    }
}
