package com.hhplus.ecommerce.presentation;

import com.hhplus.ecommerce.presentation.dto.request.basket.BasketRequestDto;
import com.hhplus.ecommerce.presentation.dto.request.basket.DeleteBasketDetailRequestDto;
import com.hhplus.ecommerce.presentation.dto.response.basket.BasketResponseDto;
import com.hhplus.ecommerce.presentation.dto.response.basket.DeleteBasketDetailResponseDto;
import com.hhplus.ecommerce.presentation.facade.BasketFacade;
import com.hhplus.ecommerce.presentation.dto.request.basket.AddBasketRequestDto;
import com.hhplus.ecommerce.presentation.dto.response.basket.AddBasketResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "장바구니",
        description = "장바구니 기능 API"
)
@RequestMapping("/basket")
@RestController
@RequiredArgsConstructor
public class BasketController {

    private final BasketFacade basketFacade;

    @Operation(
            summary = "장바구니 상품 추가",
            description = "장바구니 상품 추가 API",
            parameters = {
                    @Parameter(name = "userId", description = "사용자 아이디", required = true, example = "1", in = ParameterIn.PATH),
                    @Parameter(name = "itemMap", description = "추가할 상품 정보", required = true, example = "1", in = ParameterIn.PATH)
            },
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(schema = @Schema(implementation = AddBasketResponseDto.class))
            )
    )
    @PostMapping("/add")
    public AddBasketResponseDto addBasket(@RequestBody AddBasketRequestDto addBasketRequestDto) {

        return basketFacade.addBasket(addBasketRequestDto);
    }

    @Operation(
            summary = "장바구니 상품 제거",
            description = "장바구니의 담긴 상품을 삭제합니다.",
            parameters = {
                    @Parameter(name = "userId", description = "사용자 아이디", required = true, example = "1", in = ParameterIn.PATH),
                    @Parameter(name = "itemId", description = "삭제할 상품 번호", required = true, example = "1", in = ParameterIn.PATH)
            },
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(schema = @Schema(implementation = DeleteBasketDetailResponseDto.class))
            )
    )
    @DeleteMapping("/delete")
    public DeleteBasketDetailResponseDto deleteBasketDetail(@RequestBody DeleteBasketDetailRequestDto deleteBasketDetailRequestDto) {

        return basketFacade.deleteBasketDetail(deleteBasketDetailRequestDto);
    }

    @Operation(
            summary = "장바구니 상세 조회",
            description = "장바구니의 담긴 모든 상품들을 보여줍니다.",
            parameters = {
                    @Parameter(name = "userId", description = "사용자 아이디", required = true, example = "1", in = ParameterIn.PATH)
            },
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(schema = @Schema(implementation = BasketResponseDto.class))
            )
    )
    @PostMapping
    public BasketResponseDto getBasket(@RequestBody BasketRequestDto basketRequestDto) {

        return basketFacade.getBasket(basketRequestDto);
    }
}
