package com.store.store.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.store.model.BooksCategory;
import com.store.store.model.CategoryName;

@Repository
public interface BooksCategoryRepository extends JpaRepository<BooksCategory, Long> {
    Optional<BooksCategory> findByName(CategoryName name);
}
