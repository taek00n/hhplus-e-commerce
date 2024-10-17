package com.hhplus.ecommerce.presentation;

import com.hhplus.ecommerce.business.ItemService;
import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.presentation.dto.response.basket.AddBasketResponseDto;
import com.hhplus.ecommerce.presentation.dto.response.item.ItemResponseDto;
import com.hhplus.ecommerce.presentation.dto.response.item.ItemsResponseDto;
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
        name = "상품",
        description = "상품 기능 API"
)
@RequestMapping("/items")
@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @Operation(
            summary = "모든 상품 조회",
            description = "모든 상품 조회 API",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(schema = @Schema(implementation = ItemsResponseDto.class))
            )
    )
    @GetMapping
    public ItemsResponseDto getItems() {

        return new ItemsResponseDto(itemService.getItems());
    }

    @Operation(
            summary = "특정 상품 조회",
            description = "특정 상품 조회 API",
            parameters = {
                    @Parameter(name = "itemId", description = "상품 번호", required = true, example = "1", in = ParameterIn.PATH),
            },
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(schema = @Schema(implementation = ItemResponseDto.class))
            )
    )
    @GetMapping("/{itemId}")
    public ItemResponseDto getItem(@PathVariable Long itemId) {

        Item item = itemService.getItem(itemId);
        return new ItemResponseDto(item.getItemId(), item.getItemName(), item.getItemPrice(), item.getItemStock());
    }
}
