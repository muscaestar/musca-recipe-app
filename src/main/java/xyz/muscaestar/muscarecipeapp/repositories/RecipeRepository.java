package xyz.muscaestar.muscarecipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import xyz.muscaestar.muscarecipeapp.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
