package com.shoppingApp.order_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "t_orders")             //this Order entity will be mapped to the t_order table
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String orderNumber;
    @OneToMany(cascade = CascadeType.ALL)     //relationship defined between one to another entity
    private List<OrderLineItems> orderLineItemsList;
}
