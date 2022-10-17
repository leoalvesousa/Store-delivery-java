package com.example.storeapi.resource;

import com.example.storeapi.model.Item;
import com.example.storeapi.model.Sacola;
import com.example.storeapi.resource.Dto.ItemDto;
import com.example.storeapi.service.SacolaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sacolas")
@RequiredArgsConstructor
public class SacolaResource {
    private final SacolaService sacolaService;

    @PostMapping
    public Item incluirItemNaSacola(@RequestBody ItemDto itemDto) {
        return sacolaService.incluirItemNaSacola(itemDto);
    }
    @GetMapping("/{id}")
    public Sacola verSacola(@PathVariable("id")Long id){
        return sacolaService.verSacola(id);
    }

    @PatchMapping("/fecharSacola/{id}")
    public Sacola fecharSacola(@PathVariable("id")Long id , @RequestParam() int formaPagamento){
        return sacolaService.fecharSacola(id, formaPagamento);
    }
}