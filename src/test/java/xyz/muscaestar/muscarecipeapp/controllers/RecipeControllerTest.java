package xyz.muscaestar.muscarecipeapp.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import xyz.muscaestar.muscarecipeapp.commands.RecipeCommand;
import xyz.muscaestar.muscarecipeapp.domain.Recipe;
import xyz.muscaestar.muscarecipeapp.exceptions.NotFoundException;
import xyz.muscaestar.muscarecipeapp.services.RecipeService;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {

    @InjectMocks
    RecipeController recipeController;

    @Mock
    RecipeService recipeService;

    MockMvc mockMvc;

    Recipe recipe1;
    RecipeCommand command1;
    final Long ID = 1L;
    final String NAME = "KE";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();

        recipe1 = Recipe.builder().id(ID).name(NAME).build();
        command1 = new RecipeCommand();
        command1.setId(ID);
    }

    @Test
    public void showById() throws Exception {
        when(recipeService.getRecipeById(anyLong())).thenReturn(recipe1);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/show"))
                .andExpect(view().name("recipe/show"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));
        verify(recipeService).getRecipeById(anyLong());
        verify(recipeService, never()).getRecipes();
    }

    @Test
    public void showByIdNotExist() throws Exception {
        when(recipeService.getRecipeById(anyLong())).thenThrow(new NotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }

    @Test
    public void showByIdWrongType() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/badtype/show"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }

    @Test
    public void newRecipe() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/new"))
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));

    }

    @Test
    public void updateRecipe() throws Exception {
        when(recipeService.getRecipeCommandById(anyLong())).thenReturn(command1);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/update"))
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));
        verify(recipeService).getRecipeCommandById(anyLong());
        verify(recipeService, never()).getRecipes();

    }

    @Test
    public void saveOrUpdate() throws Exception {
        when(recipeService.saveRecipeCommand(any(RecipeCommand.class))).thenReturn(command1);

        mockMvc.perform(MockMvcRequestBuilders.post("/recipe/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("name", "name")
                .param("description", "description")
                .param("directions", "directions")
        ).andExpect(view().name("redirect:/recipe/1/show"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void saveOrUpdateOnFail() throws Exception {
        when(recipeService.saveRecipeCommand(any(RecipeCommand.class))).thenReturn(command1);

        mockMvc.perform(MockMvcRequestBuilders.post("/recipe/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("name", "name")
                .param("description", "description")
                .param("directions", "directions")
                .param("servings", String.valueOf(101))
        ).andExpect(view().name(RecipeController.RECIPE_RECIPEFORM_URL))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteRecipe() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/delete"))
                .andExpect(view().name("redirect:/index"))
                .andExpect(status().is3xxRedirection());
        verify(recipeService).deleteRecipeById(anyLong());
    }

}