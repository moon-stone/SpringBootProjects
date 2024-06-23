package com.shoppingApp.inventry_service.service;

import com.shoppingApp.inventry_service.dto.InventoryMatch;
import com.shoppingApp.inventry_service.dto.InventoryRequest;
import com.shoppingApp.inventry_service.dto.InventoryResponse;
import com.shoppingApp.inventry_service.model.Inventory;
import com.shoppingApp.inventry_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCodes)
    {
        return inventoryRepository.findBySkuCodeIn(skuCodes).stream()
                .map(inventory -> InventoryResponse.builder()
                        .skuCode(inventory.getSkuCode())
                        .isInStock(inventory.getQuantity() > 0)
                        .quantity(inventory.getQuantity())
                        .build()
                ).toList();

   // return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }


    public void addInTheStock(InventoryRequest inventoryRequest) {
        Inventory inventory = new Inventory();
        inventory.setSkuCode(inventoryRequest.getSkuCode());
        inventory.setQuantity(inventoryRequest.getQuantity());

        inventoryRepository.save(inventory);
    }

    public List<InventoryResponse> listOfAllProducts() {
        return inventoryRepository.findAll()
                .stream()
                .map(inventory -> InventoryResponse.builder()
                        .skuCode(inventory.getSkuCode())
                        .isInStock(inventory.getQuantity() > 0)
                        .quantity((inventory.getQuantity())).build()).toList();
    }
}
