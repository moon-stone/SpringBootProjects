package com.shoppingApp.inventry_service.controller;

import com.shoppingApp.inventry_service.dto.InventoryRequest;
import com.shoppingApp.inventry_service.model.Inventory;
import com.shoppingApp.inventry_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable("sku-code") String skuCode){
        return inventoryService.isInStock(skuCode);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String addInTheStock(@RequestBody InventoryRequest inventoryRequest){
        inventoryService.addInTheStock(inventoryRequest);
        return "Added in the Stock Successfully";
    }
}
