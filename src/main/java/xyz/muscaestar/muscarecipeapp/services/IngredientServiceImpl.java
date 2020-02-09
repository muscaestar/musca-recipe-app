package xyz.muscaestar.muscarecipeapp.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.muscaestar.muscarecipeapp.commands.IngredientCommand;
import xyz.muscaestar.muscarecipeapp.converters.IngredientCommandToIngredient;
import xyz.muscaestar.muscarecipeapp.converters.IngredientToIngredientCommand;
import xyz.muscaestar.muscarecipeapp.domain.Ingredient;
import xyz.muscaestar.muscarecipeapp.repositories.IngredientRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class IngredientServiceImpl implements IngredientService {

    private IngredientRepository ingredientRepository;
    private IngredientToIngredientCommand ingredientToIngredientCommand;
    private IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    @Override
    public Ingredient getIngredientById(Long id) {
        return ingredientRepository.findById(id).orElse(null);
    }

    @Override
    public Set<Ingredient> getIngredients() {
        Set<Ingredient> ingredients = new HashSet<>();
        ingredientRepository.findAll().forEach(ingredients::add);
        return ingredients;
    }

    @Override
    public Set<Ingredient> getIngredientsByRecipeId(Long recipeId) {
        Set<Ingredient> ingredients = new HashSet<>();
        ingredientRepository.findAll().forEach(ingredient -> {
            if (recipeId.equals(ingredient.getRecipe().getId())) {
                ingredients.add(ingredient);
            }
        });
        return ingredients;
    }

    @Override
    public void deleteIngredientById(Long id) {
        ingredientRepository.deleteById(id);
    }

    @Override
    @Transactional
    public IngredientCommand getIngredientCommandById(Long id) {
        Ingredient ingredient = getIngredientById(id);
        return ingredientToIngredientCommand.convert(ingredient);
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Ingredient ingredient = ingredientCommandToIngredient.convert(command);
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return ingredientToIngredientCommand.convert(savedIngredient);
    }
}
