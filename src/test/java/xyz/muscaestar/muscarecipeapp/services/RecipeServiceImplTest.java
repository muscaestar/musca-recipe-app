package xyz.muscaestar.muscarecipeapp.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import xyz.muscaestar.muscarecipeapp.commands.RecipeCommand;
import xyz.muscaestar.muscarecipeapp.converters.RecipeToRecipeCommand;
import xyz.muscaestar.muscarecipeapp.domain.Recipe;
import xyz.muscaestar.muscarecipeapp.exceptions.NotFoundException;
import xyz.muscaestar.muscarecipeapp.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    @InjectMocks
    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    Recipe recipe1;
    RecipeCommand command1;
    final Long ID = 1L;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        recipe1 = Recipe.builder().id(ID).build();
        command1 = new RecipeCommand();
        command1.setId(ID);
    }

    @Test
    public void getRecipes() {
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(recipe1);
        when(recipeRepository.findAll()).thenReturn(recipes);

        assertEquals(1, recipeService.getRecipes().size());
        verify(recipeRepository).findAll();
    }

    @Test
    public void getRecipeById() {
        when(recipeRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(recipe1));

        assertEquals(ID, recipeService.getRecipeById(ID).getId());
        verify(recipeRepository).findById(anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void getRecipeByIdNotExist() {
        when(recipeRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());

        recipeService.getRecipeById(ID);
    }

    @Test
    public void getRecipeCommandById() {
        when(recipeRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(recipe1));
        when(recipeToRecipeCommand.convert(any(Recipe.class))).thenReturn(command1);

        RecipeCommand returnedCommand = recipeService.getRecipeCommandById(ID);
        assertEquals(ID, returnedCommand.getId());

        verify(recipeRepository).findById(anyLong());
        verify(recipeToRecipeCommand).convert(any(Recipe.class));
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void deleteRecipeById() {

        recipeService.deleteRecipeById(ID);

        verify(recipeRepository).deleteById(anyLong());
    }
}