package com.rcm.desenvolvimento.estoquepreco.connections;

import constants.RabbitMqconstants;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class RabbitMqConnection {

    private static final String NOME_EXCHANGE = "amq.direct";
    private AmqpAdmin amqpAdmin;

    public RabbitMqConnection(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    private Queue fila(String nomeFila) {
        return new Queue(nomeFila, true, false, false);
    }

    private DirectExchange directExchange() {
        return new DirectExchange(NOME_EXCHANGE);
    }

    private Binding relationship(Queue fila, DirectExchange troca) {
        return new Binding(fila.getName(), Binding.DestinationType.QUEUE, troca.getName(), fila.getName(), null);
    }

    @PostConstruct
    private void adiciona() {
        Queue filaEstoque = this.fila(RabbitMqconstants.FILA_ESTOQUE);
        Queue filaPreco = this.fila(RabbitMqconstants.FILA_PRECO);

        DirectExchange troca = this.directExchange();

        Binding bindEstoque = this.relationship(filaEstoque, troca);
        Binding bindPreco = this.relationship(filaPreco, troca);

        //Criando Filas no RabbitMQ.
        this.amqpAdmin.declareQueue(filaEstoque);
        this.amqpAdmin.declareQueue(filaPreco);

        this.amqpAdmin.declareExchange(troca);

        this.amqpAdmin.declareBinding(bindEstoque);
        this.amqpAdmin.declareBinding(bindPreco);

    }

}
