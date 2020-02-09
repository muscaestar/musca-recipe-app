package xyz.muscaestar.muscarecipeapp.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import xyz.muscaestar.muscarecipeapp.commands.IngredientCommand;
import xyz.muscaestar.muscarecipeapp.converters.IngredientCommandToIngredient;
import xyz.muscaestar.muscarecipeapp.converters.IngredientToIngredientCommand;
import xyz.muscaestar.muscarecipeapp.domain.Ingredient;
import xyz.muscaestar.muscarecipeapp.domain.Recipe;
import xyz.muscaestar.muscarecipeapp.repositories.IngredientRepository;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IngredientServiceImplTest {

    @InjectMocks
    private IngredientServiceImpl ingredientService;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private IngredientToIngredientCommand ingredientToIngredientCommand;

    @Mock
    private IngredientCommandToIngredient ingredientCommandToIngredient;

    Ingredient ingredient;
    IngredientCommand ingredientCommand;
    final Long ID = 1L;
    final Long RECIPE_ID = 1L;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ingredient = new Ingredient();
        ingredient.setId(ID);
        ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ID);

        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        ingredient.setRecipe(recipe);
    }

    @Test
    public void getIngredientById() {
        when(ingredientRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(ingredient));

        assertEquals(ID, ingredientService.getIngredientById(ID).getId());
    }

    @Test
    public void getIngredients() {
        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(ingredient);

        when(ingredientRepository.findAll()).thenReturn(ingredients);

        assertEquals(1, ingredientService.getIngredients().size());
    }

    @Test
    public void getIngredientsByRecipeId() {
        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(ingredient);

        when(ingredientRepository.findAll()).thenReturn(ingredients);

        assertEquals(1, ingredientService.getIngredients().size());
        assertEquals(1, ingredientService.getIngredientsByRecipeId(RECIPE_ID).size());
    }

    @Test
    public void deleteIngredientById() {
        ingredientService.deleteIngredientById(ID);

        verify(ingredientRepository).deleteById(anyLong());
    }

    @Test
    public void getIngredientCommandById() {
        when(ingredientRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(ingredient));
        when(ingredientToIngredientCommand.convert(any())).thenReturn(ingredientCommand);

        IngredientCommand command = ingredientService.getIngredientCommandById(ID);
        assertEquals(ID, command.getId());
    }

    @Test
    public void saveIngredientCommand() {
        when(ingredientCommandToIngredient.convert(any())).thenReturn(ingredient);
        when(ingredientRepository.save(any())).thenReturn(ingredient);
        when(ingredientToIngredientCommand.convert(any())).thenReturn(ingredientCommand);

        assertEquals(ID, ingredientService.saveIngredientCommand(ingredientCommand).getId());

    }

}