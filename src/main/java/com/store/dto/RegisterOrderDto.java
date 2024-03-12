package com.store.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// @Getter
// @Setter
@Data
@AllArgsConstructor
public class RegisterOrderDto {
    public Set<Long> productIds;
    public Float amount;
    public Number quantity;
}
