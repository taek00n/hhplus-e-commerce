package com.hhplus.ecommerce.presentation;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/item")
@RestController
public class ItemController {

    @GetMapping("/all")
    public Map<Object, Object> all() {
        Map<Object, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> items = new HashMap<>();

        response.put("status", "SUCCESS");
        response.put("message", "조회 성공");
        response.put("data", data);

        return response;
    }
}
