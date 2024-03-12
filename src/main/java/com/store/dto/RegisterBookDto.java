package com.store.dto;

import java.util.Set;

import com.store.model.BooksCategory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterBookDto {
    private String name;
    private String description;
    private Set<String> category;
    private String img;
    private Float price;
}
