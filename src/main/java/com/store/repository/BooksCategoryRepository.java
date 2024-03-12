package com.store.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.model.BooksCategory;
import com.store.model.CategoryName;

@Repository
public interface BooksCategoryRepository extends JpaRepository<BooksCategory, Long> {
    Optional<BooksCategory> findByName(CategoryName name);
}
