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
import xyz.muscaestar.muscarecipeapp.commands.IngredientCommand;
import xyz.muscaestar.muscarecipeapp.commands.UnitOfMeasureCommand;
import xyz.muscaestar.muscarecipeapp.domain.Ingredient;
import xyz.muscaestar.muscarecipeapp.services.IngredientService;
import xyz.muscaestar.muscarecipeapp.services.UomService;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {

    @InjectMocks
    IngredientController ingredientController;

    @Mock
    IngredientService ingredientService;

    @Mock
    UomService uomService;

    MockMvc mockMvc;

    Ingredient ingredient;
    IngredientCommand command;
    Set<UnitOfMeasureCommand> list;
    final Long ING_ID = 1L;
    final Long RECIPE_ID = 1L;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();

        ingredient = new Ingredient();
        ingredient.setId(ING_ID);
        command = new IngredientCommand();
        command.setId(ING_ID);
        list = new HashSet<>();
    }


    @Test
    public void showIngredientsOfRecipe() throws Exception {
        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(ingredient);

        when(ingredientService.getIngredientsByRecipeId(RECIPE_ID)).thenReturn(ingredients);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredients"))
                .andExpect(view().name("/recipe/ingredient/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredients"));
    }

    @Test
    public void showIngredient() throws Exception {
        when(ingredientService.getIngredientById(anyLong())).thenReturn(ingredient);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredient/1/show"))
                .andExpect(view().name("/recipe/ingredient/show"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"));
    }

    @Test
    public void newIngredient() throws Exception {
        when(uomService.listAllUoms()).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredient/new"))
                .andExpect(view().name("/recipe/ingredient/ingredientform"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"));

    }

    @Test
    public void updateIngredient() throws Exception {
        when(ingredientService.getIngredientCommandById(anyLong())).thenReturn(command);
        when(uomService.listAllUoms()).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredient/1/update"))
                .andExpect(view().name("/recipe/ingredient/ingredientform"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"));


    }

    @Test
    public void saveOrUpdateIngredient() throws Exception {
        when(ingredientService.saveIngredientCommand(any())).thenReturn(command);

        mockMvc.perform(MockMvcRequestBuilders.post("/recipe/1/ingredient/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
        ).andExpect(view().name("redirect:/recipe/1/ingredient/1/show"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void deleteIngredientById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredient/1/delete"))
                .andExpect(view().name("redirect:/recipe/1/ingredients"))
                .andExpect(status().is3xxRedirection());
        verify(ingredientService).deleteIngredientById(anyLong());
    }
}