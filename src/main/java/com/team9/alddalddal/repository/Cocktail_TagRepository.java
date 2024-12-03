package com.team9.alddalddal.repository;

import com.team9.alddalddal.entity.Cocktail_Tag;
import com.team9.alddalddal.entity.Cocktail_TagId;
import com.team9.alddalddal.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Cocktail_TagRepository extends JpaRepository<Cocktail_Tag, Long> {
    List<Cocktail_Tag> findById_Name(String Id_Name);
    List<Cocktail_Tag> findById_Tag(int Id_Tag);
}
