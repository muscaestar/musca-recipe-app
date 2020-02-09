package xyz.muscaestar.muscarecipeapp.services;

import xyz.muscaestar.muscarecipeapp.commands.IngredientCommand;
import xyz.muscaestar.muscarecipeapp.domain.Ingredient;

import java.util.Set;

public interface IngredientService {

    Ingredient getIngredientById(Long id);

    Set<Ingredient> getIngredients();

    Set<Ingredient> getIngredientsByRecipeId(Long recipeId);

    void deleteIngredientById(Long id);

    IngredientCommand getIngredientCommandById(Long id);

    IngredientCommand saveIngredientCommand(IngredientCommand command);

}
