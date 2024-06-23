package com.shoppingApp.inventry_service.controller;

import com.shoppingApp.inventry_service.dto.InventoryMatch;
import com.shoppingApp.inventry_service.dto.InventoryRequest;
import com.shoppingApp.inventry_service.dto.InventoryResponse;
import com.shoppingApp.inventry_service.model.Inventory;
import com.shoppingApp.inventry_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){
      //  List<String> temp = skuCode;
        return inventoryService.isInStock(skuCode);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> getAllProductsList(){
        return inventoryService.listOfAllProducts();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String addInTheStock(@RequestBody InventoryRequest inventoryRequest){
        inventoryService.addInTheStock(inventoryRequest);
        return "Added in the Stock Successfully";
    }
}
