package com.team9.alddalddal.repository;

import com.team9.alddalddal.entity.Cocktail;
import com.team9.alddalddal.entity.Cocktail_Tag;
import com.team9.alddalddal.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Cocktail_TagRepository extends JpaRepository<Cocktail_Tag, Long> {
    List<Cocktail_Tag> findByCocktail(Cocktail cocktail);
    List<Cocktail_Tag> findByTag(Tag tag);
}
