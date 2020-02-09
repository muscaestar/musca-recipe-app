package xyz.muscaestar.muscarecipeapp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.muscaestar.muscarecipeapp.commands.RecipeCommand;
import xyz.muscaestar.muscarecipeapp.converters.RecipeCommandToRecipe;
import xyz.muscaestar.muscarecipeapp.converters.RecipeToRecipeCommand;
import xyz.muscaestar.muscarecipeapp.domain.Recipe;
import xyz.muscaestar.muscarecipeapp.exceptions.NotFoundException;
import xyz.muscaestar.muscarecipeapp.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {

        log.debug("Recipe Service DEBUG");

        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
        return recipes;
    }

    @Override
    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id).orElseThrow(new NotFoundException());
    }

    @Override
    public void deleteRecipeById(Long id) {
        try {

            recipeRepository.deleteById(id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            throw new NotFoundException();
        }
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved RecipeId: " + savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeCommand getRecipeCommandById(Long id) {
        return recipeToRecipeCommand.convert(getRecipeById(id));
    }
}
