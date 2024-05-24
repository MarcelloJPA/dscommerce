package com.devsuperior.dscommerce.entities;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name="tb_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Esta configuração é útil quando você precisa de precisão de data e hora e quer evitar ambiguidades relacionadas a fusos horários.
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant moment;

    private OrderStatus status;

    @ManyToOne //Muitos pedidos podem estar associados a um único cliente.
    @JoinColumn(name="client_id") //FK que referencia a tabela User.
    private User client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }
}
