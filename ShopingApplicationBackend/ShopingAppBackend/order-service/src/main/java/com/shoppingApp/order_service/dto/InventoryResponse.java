package com.shoppingApp.order_service.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class InventoryResponse {
    String skuCode;
    Boolean isInStock;
}
