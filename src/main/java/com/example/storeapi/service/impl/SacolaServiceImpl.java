package com.example.storeapi.service.impl;

import com.example.storeapi.enumeration.FormaPagamento;
import com.example.storeapi.model.Item;
import com.example.storeapi.model.Sacola;
import com.example.storeapi.repository.ItemRepository;
import com.example.storeapi.repository.ProdutoRepository;
import com.example.storeapi.repository.SacolaRepository;
import com.example.storeapi.resource.Dto.ItemDto;
import com.example.storeapi.service.SacolaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SacolaServiceImpl  implements SacolaService {

    private final SacolaRepository sacolaRepository;
    private final ProdutoRepository produtoRepository;

    private final ItemRepository itemRepository;
    @Override
    public Item incluirItemNaSacola(ItemDto itemDto) {

        Sacola sacola = verSacola(itemDto.getSacolaId());
        if (sacola.isFechada()) {
            throw new RuntimeException("Esta sacola está fechada.");
        }

        Item itemParaSerInserido = Item.builder()
                .quantidade(itemDto.getQuantidade())
                .sacola(sacola)
                .produto(produtoRepository.findById(itemDto.getProdutoId()).orElseThrow(
                        () -> {
                            throw new RuntimeException("Esse produto não existe!");
                        }
                ))
                .build();
        List<Item> itensDaScola = sacola.getItens();
        if (itensDaScola.isEmpty()) {
            itensDaScola.add(itemParaSerInserido);
        }

        double valorTotalSacola = itensDaScola.stream()
                .mapToDouble(itemAtual -> itemAtual.getProduto().getValorUnitario() * itemAtual.getQuantidade())
                .sum();

        sacola.setValorTotal(valorTotalSacola);
        sacolaRepository.save(sacola);
        return itemParaSerInserido;
    }

    @Override
    public Sacola verSacola(Long id) {
        return sacolaRepository.findById(id).orElseThrow(
                () -> {
                    throw new RuntimeException("Sacola não existe");
                }
        );
    }

    @Override
    public Sacola fecharSacola(Long id, int escolhaformaPagamento) {
        Sacola sacola = verSacola(id);
        if (sacola.getItens().isEmpty()){
            throw new RuntimeException("Inclua items na sacola");
        }
        FormaPagamento formaPagamento =
                escolhaformaPagamento == 0 ? FormaPagamento.DINHEIRO : FormaPagamento.CARTAO;
        sacola.setFormaPagamento(formaPagamento);
        sacola.setFechada(true);
        return sacolaRepository.save(sacola);
    }
}
