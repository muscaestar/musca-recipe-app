package xyz.muscaestar.muscarecipeapp.services;

import xyz.muscaestar.muscarecipeapp.commands.RecipeCommand;
import xyz.muscaestar.muscarecipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe getRecipeById(Long id);

    void deleteRecipeById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    RecipeCommand getRecipeCommandById(Long id);
}
