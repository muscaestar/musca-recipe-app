package xyz.muscaestar.muscarecipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import xyz.muscaestar.muscarecipeapp.domain.UnitOfMeasure;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {
    Optional<UnitOfMeasure> findByDescription(String description);
}
