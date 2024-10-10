package com.hhplus.ecommerce.presentation;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserController {

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

    @PostMapping("/balance")
    public Map<Object, Object> balance(@RequestParam Long userId) {
        Map<Object, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        response.put("status", "SUCCESS");
        response.put("message", "조회 성공");
        response.put("data", data);
        data.put("id", userId);
        data.put("balance", 2000);

        return response;
    }
}
