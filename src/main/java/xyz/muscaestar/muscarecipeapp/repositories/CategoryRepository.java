package xyz.muscaestar.muscarecipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import xyz.muscaestar.muscarecipeapp.domain.Category;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByDescription(String description);
}
