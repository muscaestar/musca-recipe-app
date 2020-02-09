package xyz.muscaestar.muscarecipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import xyz.muscaestar.muscarecipeapp.commands.IngredientCommand;
import xyz.muscaestar.muscarecipeapp.domain.Ingredient;
import xyz.muscaestar.muscarecipeapp.services.IngredientService;
import xyz.muscaestar.muscarecipeapp.services.UomService;

@Controller
public class IngredientController {

    private IngredientService ingredientService;
    private UomService uomService;

    public IngredientController(IngredientService ingredientService, UomService uomService) {
        this.ingredientService = ingredientService;
        this.uomService = uomService;
    }

    @GetMapping({"/recipe/{recipeId}/ingredients"})
    public String showIngredientsOfRecipe(@PathVariable String recipeId, Model model) {
        model.addAttribute("ingredients", ingredientService.getIngredientsByRecipeId(new Long(recipeId)));
        return "/recipe/ingredient/list";
    }

    @GetMapping({"/recipe/{recipeId}/ingredient/{ingId}/show",
                "/recipe/{recipeId}/ingredient/{ingId}/",
                "/recipe/{recipeId}/ingredient/{ingId}"})
    public String showIngredient(@PathVariable String ingId, Model model) {
        Ingredient ingredient = ingredientService.getIngredientById(new Long(ingId));
        model.addAttribute("ingredient", ingredient);
        return "/recipe/ingredient/show";
    }

    @GetMapping({"/recipe/{recipeId}/ingredient/new"})
    public String newIngredient(@PathVariable String recipeId, Model model) {
        IngredientCommand command = new IngredientCommand();
        command.setRecipeId(new Long(recipeId));
        model.addAttribute("ingredient", command);
        model.addAttribute("uomList", uomService.listAllUoms());
        return "/recipe/ingredient/ingredientform";
    }

    @GetMapping({"/recipe/{recipeId}/ingredient/{ingId}/update"})
    public String updateIngredient(@PathVariable String ingId, Model model) {
        IngredientCommand command = ingredientService.getIngredientCommandById(new Long(ingId));
        model.addAttribute("ingredient", command);
        model.addAttribute("uomList", uomService.listAllUoms());
        return "/recipe/ingredient/ingredientform";
    }

    @PostMapping({"/recipe/{recipeId}/ingredient/"})
    @Transactional
    public String saveOrUpdateIngredient(@PathVariable String recipeId, @ModelAttribute IngredientCommand command) {
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
        return "redirect:/recipe/" + recipeId + "/ingredient/" + savedCommand.getId() + "/show";
    }

    @GetMapping({"/recipe/{recipeId}/ingredient/{ingId}/delete"})
    public String deleteIngredientById(@PathVariable String ingId, @PathVariable String recipeId) {
        ingredientService.deleteIngredientById(new Long(ingId));
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }
}
