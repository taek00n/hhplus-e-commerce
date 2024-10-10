package com.hhplus.ecommerce.presentation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/orders")
@RestController
public class OrderController {

    @PostMapping("/charge")
    public Map<Object, Object> charge(@RequestParam Long userId,
                                      @RequestParam int chargeBalance) {
        Map<Object, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        int exBalance = 2000;
        int newBalance = exBalance + chargeBalance;

        response.put("status", "SUCCESS");
        response.put("message", "충전 성공");
        response.put("data", data);
        data.put("id", userId);
        data.put("ex_balance", exBalance);
        data.put("charge_balance", chargeBalance);
        data.put("new_balance", newBalance);

        return response;
    }
}
