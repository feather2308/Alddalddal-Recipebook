package com.team9.alddalddal.repository;

import com.team9.alddalddal.entity.Favorite;
import com.team9.alddalddal.entity.FavoriteId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends CrudRepository<Favorite, Long> {
    List<Favorite> findById_Id(String id);
    List<Favorite> findById_Name(String name);
    Optional<Favorite> findById_IdAndId_Name(String id, String name);

    void deleteById(FavoriteId favoriteId);
}
