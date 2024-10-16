package com.hhplus.ecommerce.presentation;

import com.hhplus.ecommerce.business.facade.BasketFacade;
import com.hhplus.ecommerce.presentation.dto.request.basket.AddBasketRequestDto;
import com.hhplus.ecommerce.presentation.dto.response.basket.AddBasketResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/basket")
@RestController
@RequiredArgsConstructor
public class BasketController {

    private final BasketFacade basketFacade;

    @PostMapping
    public AddBasketResponseDto addBasket(@RequestBody AddBasketRequestDto addBasketRequestDto) {

        return basketFacade.addBasket(addBasketRequestDto);
    }

}
