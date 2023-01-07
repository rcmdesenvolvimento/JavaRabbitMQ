package com.rcm.desenvolvimento.estoquepreco.controller;

import dto.PrecoDto;
import constants.RabbitMqconstants;
import com.rcm.desenvolvimento.estoquepreco.service.RabbitMqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/preco")
public class PrecoController {

    @Autowired
    private RabbitMqService rabbitMqService;

    @PutMapping
    public ResponseEntity alteraEstoque(@RequestBody PrecoDto precoDto) {
        System.out.println(">>> " + precoDto.codigoProduto + "  " + precoDto.preco + " <<<");
        this.rabbitMqService.enviaMensagem(RabbitMqconstants.FILA_PRECO, precoDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
