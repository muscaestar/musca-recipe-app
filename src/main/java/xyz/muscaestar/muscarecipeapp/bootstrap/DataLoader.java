package xyz.muscaestar.muscarecipeapp.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import xyz.muscaestar.muscarecipeapp.domain.*;
import xyz.muscaestar.muscarecipeapp.repositories.CategoryRepository;
import xyz.muscaestar.muscarecipeapp.repositories.RecipeRepository;
import xyz.muscaestar.muscarecipeapp.repositories.UnitOfMeasureRepository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

@Slf4j
@Component
@Profile({"default"})
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private RecipeRepository recipeRepository;

    public DataLoader(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadData();
    }


    private void loadData() {

        log.debug("DataLoader DEBUG");

        System.out.println("Loading recipes...");

        Recipe recipe1 = new Recipe();
        recipe1.setName("Perfect Guacamole");

        Set<Category> categories1 = new HashSet<>();
        Category catMexican = categoryRepository.findByDescription("Mexican").get();
        categories1.add(catMexican);
        recipe1.setCategories(categories1);

        //recipe1.setCookTime();
        recipe1.setDescription("The best guacamole keeps it simple: just ripe avocados, salt, a squeeze of lime, onions, chiles, cilantro, and some chopped tomato. Serve it as a dip at your next party or spoon it on top of tacos for an easy dinner upgrade.");
        recipe1.setDifficulty(Difficulty.EASY);
        recipe1.setDirections("1 Cut the avocado, remove flesh: Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "\n" +
                "4 Serve: Serve immediately, or if making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.");

        Supplier<RuntimeException> uomSupplier = () -> new RuntimeException("!!!Expected UOM not found.");

        UnitOfMeasure teaspoon = unitOfMeasureRepository.findByDescription("Teaspoon").orElseThrow(uomSupplier);
        UnitOfMeasure unit = unitOfMeasureRepository.findByDescription("Unit").orElseThrow(uomSupplier);
        UnitOfMeasure tablespoon = unitOfMeasureRepository.findByDescription("Tablespoon").orElseThrow(uomSupplier);

        recipe1.addIngredient(new Ingredient("salt, more to taste", new BigDecimal(1/4), teaspoon));
        recipe1.addIngredient(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), unit));
        recipe1.addIngredient(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(1), tablespoon));

        recipe1.setPrepTime(10);

        Notes notes1 = new Notes();
        notes1.setRecipeNotes("HOW TO STORE GUACAMOLE\n" +
                "Guacamole is best eaten right after it’s made. Like apples, avocados start to oxidize and turn brown once they’ve been cut. That said, the acid in the lime juice you add to guacamole can help slow down that process, and if you store the guacamole properly, you can easily make it a few hours ahead if you are preparing for a party.\n" +
                "\n" +
                "The trick to keeping guacamole green is to make sure air doesn’t touch it! Transfer it to a container, cover with plastic wrap, and press down on the plastic wrap to squeeze out any air pockets. Make sure any exposed surface of the guacamole is touching the plastic wrap, not air. This will keep the amount of browning to a minimum.\n" +
                "\n" +
                "You can store the guacamole in the fridge this way for up to three days.\n" +
                "\n" +
                "If you leave the guacamole exposed to air, it will start to brown and discolor. That browning isn’t very appetizing, but the guacamole is still good. You can either scrape off the brown parts and discard, or stir them into the rest of the guacamole.");
        recipe1.setNotes(notes1);

        recipe1.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        recipe1.setSource("ELISE BAUER");
        recipe1.setServings(4);
        //recipe1.setImage();



        Recipe recipe2 = new Recipe();
        recipe2.setName("Spicy Grilled Chicken Tacos");

        recipe2.setCategories(categories1);

        recipe2.setCookTime(15);
        recipe2.setDescription("Spicy grilled chicken tacos! Quick marinade, then grill. Ready in about 30 minutes. Great for a quick weeknight dinner, backyard cookouts, and tailgate parties.");
        recipe2.setDifficulty(Difficulty.HARD);
        recipe2.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.");

        recipe2.addIngredient(new Ingredient("salt, more to taste", new BigDecimal(1/4), teaspoon));
        recipe2.addIngredient(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), unit));
        recipe2.addIngredient(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(1), tablespoon));

        recipe2.setPrepTime(20);

        Notes notes2 = new Notes();
        notes2.setRecipeNotes("Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
                "\n" +
                "\n" +
                "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
                "\n" +
                "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!");
        recipe2.setNotes(notes2);

        recipe2.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        recipe2.setSource("SALLY VARGAS");
        recipe2.setServings(6);
        //recipe2.setImage();


        recipeRepository.save(recipe1);
        recipeRepository.save(recipe2);
        System.out.println("Bootstrap Data Loaded.");

    }
}
