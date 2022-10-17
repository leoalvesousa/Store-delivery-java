package com.example.storeapi.resource.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ItemDto {
    private Long produtoId;
    private int quantidade;
    private Long sacolaId;
}