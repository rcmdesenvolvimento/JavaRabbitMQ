package com.rcm.desenvolvimento.estoquepreco.controller;

import dto.EstoqueDto;
import constants.RabbitMqconstants;
import com.rcm.desenvolvimento.estoquepreco.service.RabbitMqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    @Autowired
    private RabbitMqService rabbitMqService;
    @PutMapping
    public ResponseEntity alteraEstoque(@RequestBody EstoqueDto estoqueDto) {
        System.out.println(">>> "+estoqueDto.codigoProduto + "  "+estoqueDto.quantidade+" <<<");
        this.rabbitMqService.enviaMensagem(RabbitMqconstants.FILA_ESTOQUE, estoqueDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
