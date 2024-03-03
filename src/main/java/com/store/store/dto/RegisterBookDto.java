package com.store.store.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterBookDto {
    private String name;
    private String description;
    private String img;
    private Float price;
}
