package com.example.storeapi.service;


import com.example.storeapi.model.Item;
import com.example.storeapi.model.Sacola;
import com.example.storeapi.resource.Dto.ItemDto;

import java.util.Optional;

public interface SacolaService {
    Item incluirItemNaSacola(ItemDto itemDto);
    Sacola verSacola(Long id);
    Sacola fecharSacola(Long id, int formaPagamento);
}