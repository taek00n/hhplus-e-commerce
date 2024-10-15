package com.hhplus.ecommerce.business.facade;

import com.hhplus.ecommerce.business.BasketDetailService;
import com.hhplus.ecommerce.business.BasketService;
import com.hhplus.ecommerce.business.ItemService;
import com.hhplus.ecommerce.business.UserService;
import com.hhplus.ecommerce.domain.Basket;
import com.hhplus.ecommerce.domain.BasketDetail;
import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.presentation.dto.request.basket.AddBasketRequestDto;
import com.hhplus.ecommerce.presentation.dto.response.basket.AddBasketResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BasketFacade {

    private final BasketService basketService;
    private final BasketDetailService basketDetailService;

    private final UserService userService;
    private final ItemService itemService;

    public AddBasketResponseDto addBasket(AddBasketRequestDto addBasketRequestDto) {

        List<BasketDetail> basketDetailList = new ArrayList<>();

        Basket userBasket = basketService.getUserBasket(addBasketRequestDto.userId());
        if (userBasket == null) {
            User basketUser = userService.getUser(addBasketRequestDto.userId());
            userBasket = basketService.createBasket(new Basket(basketUser, LocalDateTime.now()));
        }

        Basket finalUserBasket = userBasket;
        addBasketRequestDto.itemMap().forEach((itemId, amount) -> {
            Item item = itemService.getItem(itemId);
            BasketDetail basketDetail = new BasketDetail(finalUserBasket, item, amount);
            basketDetailList.add(basketDetail);
        });

        basketDetailList.forEach(basketDetail -> {
            finalUserBasket.addBasketDetail(basketDetail);
        });

        Basket createBasket = basketService.createBasket(finalUserBasket);

        return new AddBasketResponseDto(createBasket.getBasketId());
    }
}
