package xyz.muscaestar.muscarecipeapp.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import xyz.muscaestar.muscarecipeapp.commands.RecipeCommand;
import xyz.muscaestar.muscarecipeapp.converters.RecipeToRecipeCommand;
import xyz.muscaestar.muscarecipeapp.domain.Recipe;
import xyz.muscaestar.muscarecipeapp.repositories.RecipeRepository;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceImplIT {

    public static final String NEW_DESCP = "New Description";

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Test
    @Transactional
    public void testSaveOfDescription() {
        //given
        Recipe recipe = recipeRepository.findAll().iterator().next();
        RecipeCommand command = recipeToRecipeCommand.convert(recipe);

        //when
        command.setDescription(NEW_DESCP);
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        //then
        assertEquals(NEW_DESCP, savedCommand.getDescription());
        assertEquals(recipe.getId(), savedCommand.getId());
        assertEquals(recipe.getCategories().size(), savedCommand.getCategories().size());
        assertEquals(recipe.getIngredients().size(), savedCommand.getIngredients().size());

    }
}