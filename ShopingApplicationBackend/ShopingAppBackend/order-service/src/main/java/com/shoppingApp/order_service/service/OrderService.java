package com.shoppingApp.order_service.service;

import com.shoppingApp.order_service.dto.InventoryMatch;
import com.shoppingApp.order_service.dto.InventoryResponse;
import com.shoppingApp.order_service.dto.OrderLineItemsDto;
import com.shoppingApp.order_service.dto.OrderRequest;
import com.shoppingApp.order_service.model.Order;
import com.shoppingApp.order_service.model.OrderLineItems;
import com.shoppingApp.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    @Autowired
    private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToOrderLineItems).toList();

        order.setOrderLineItemsList(orderLineItems);
        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                        .toList();

        List<InventoryMatch> inventoryMatchList = order.getOrderLineItemsList().stream()
                .map(orderLineItem ->
                     InventoryMatch.builder()
                            .skuCode(orderLineItem.getSkuCode())
                            .orderQuantity(orderLineItem.getSkuCode())
                            .build()
                )
                .toList();


        //check with InventoryService, if product is available then place order
        //since we are inquiring, we will call GET method only
        //we have the sku codes with us, we need to pass it in as requestParameter in webClient call to inventryService.
        InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .doOnError(throwable -> {
                    System.err.println("error 1 : " +throwable.getMessage());
                })
                .block();
        //To read the data from the response, we need to use the bodyToMono() method and inside this method, we need to pass the return type of the response.
        //By default, webclient makes Async request but to make it call Sync. Request we need to call block().
        long productsNotAvailable = 1;

        if(inventoryResponses.length > 0){
            productsNotAvailable = Arrays.stream(inventoryResponses)
                    .filter(inventoryResponse -> !inventoryResponse.getIsInStock())
                    .count();
        }

        //so we will create an order when all products are in the stock
        if(productsNotAvailable > 0){
            throw new IllegalArgumentException("Product is not in Stock, try after sometime");
        } else {
            orderRepository.save(order);
        }
        return;
    }

    private OrderLineItems mapToOrderLineItems(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        return orderLineItems;
    }

}
