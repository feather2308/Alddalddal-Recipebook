package com.team9.alddalddal.repository;

import com.team9.alddalddal.entity.Cocktail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CocktailRepository extends JpaRepository<Cocktail, Long> {
    Cocktail findByName(String name);
}
