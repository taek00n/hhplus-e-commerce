package com.hhplus.ecommerce.presentation.facade;

import com.hhplus.ecommerce.application.BasketDetailService;
import com.hhplus.ecommerce.application.BasketService;
import com.hhplus.ecommerce.application.ItemService;
import com.hhplus.ecommerce.application.UserService;
import com.hhplus.ecommerce.common.exception.RestApiException;
import com.hhplus.ecommerce.common.exception.domain.BasketErrorCode;
import com.hhplus.ecommerce.common.exception.domain.ItemErrorCode;
import com.hhplus.ecommerce.domain.Basket;
import com.hhplus.ecommerce.domain.BasketDetail;
import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.domain.User;
import com.hhplus.ecommerce.presentation.dto.request.basket.AddBasketRequestDto;
import com.hhplus.ecommerce.presentation.dto.request.basket.BasketRequestDto;
import com.hhplus.ecommerce.presentation.dto.request.basket.DeleteBasketDetailRequestDto;
import com.hhplus.ecommerce.presentation.dto.response.basket.AddBasketResponseDto;
import com.hhplus.ecommerce.presentation.dto.response.basket.BasketResponseDto;
import com.hhplus.ecommerce.presentation.dto.response.basket.DeleteBasketDetailResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class BasketFacade {

    private final BasketService basketService;
    private final BasketDetailService basketDetailService;

    private final UserService userService;
    private final ItemService itemService;

    public BasketResponseDto getBasket(BasketRequestDto basketRequestDto) {

        Basket userBasket = basketService.getUserBasket(basketRequestDto.userId());
        if (userBasket == null) {
            throw new RestApiException(BasketErrorCode.NO_BASKET_BY_ID);
        }
        List<BasketDetail> allDetailByBasket = basketDetailService.getAllDetailByBasket(userBasket);

        return new BasketResponseDto(userBasket.getBasketId(), allDetailByBasket);
    }

    public AddBasketResponseDto addBasket(AddBasketRequestDto addBasketRequestDto) {

        Basket userBasket = getUserBasket(addBasketRequestDto.userId());
        List<BasketDetail> basketDetailList = addBasketDetail(userBasket, addBasketRequestDto.itemMap());

        basketDetailList.forEach(basketDetail -> {
            userBasket.addBasketDetail(basketDetail);
        });

        Basket createBasket = basketService.createBasket(userBasket);

        return new AddBasketResponseDto(createBasket.getBasketId());
    }

    public DeleteBasketDetailResponseDto deleteBasketDetailByItem(DeleteBasketDetailRequestDto requestDto) {

        Basket userBasket = getUserBasket(requestDto.userId());
        Item deleteItem = itemService.getItemByItemId(requestDto.itemId());

        List<BasketDetail> allDetailByBasket = basketDetailService.getAllDetailByBasket(userBasket);
        for (BasketDetail basketDetail : allDetailByBasket) {
            if(basketDetail.getItem().getItemId() == requestDto.itemId()){
                basketDetailService.delete(basketDetail.getBasketDetailId());
            }
        }

        return new DeleteBasketDetailResponseDto(deleteItem.getItemId(), deleteItem.getItemName());
    }
    
    private Basket getUserBasket(Long userId) {

        Basket userBasket = basketService.getUserBasket(userId);

        if (userBasket == null) {
            User user = userService.getUserByUserId(userId);
            return new Basket(user, LocalDateTime.now());
        }

        return userBasket;
    }

    private List<BasketDetail> addBasketDetail(Basket basket, Map<Long, Integer> itemMap) {
        List<BasketDetail> basketDetailList = new ArrayList<>();

        itemMap.forEach((itemId, amount) -> {
            Item item = itemService.getItemByItemId(itemId);
            BasketDetail itemInBasketDetail = basketDetailService.getBasketDetailByItem(item);

            if (item.getItemStock() < amount) {
                throw new RestApiException(ItemErrorCode.NO_ENOUGH_ITEM);
            }

            if (itemInBasketDetail == null) {
                BasketDetail basketDetail = new BasketDetail(basket, item, amount);
                basketDetailList.add(basketDetail);
            } else {
                itemInBasketDetail.addAmount(amount);
            }
        });

        return basketDetailList;
    }
}
