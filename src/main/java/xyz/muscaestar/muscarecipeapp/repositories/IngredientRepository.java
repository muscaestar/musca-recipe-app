package xyz.muscaestar.muscarecipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import xyz.muscaestar.muscarecipeapp.domain.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
}
