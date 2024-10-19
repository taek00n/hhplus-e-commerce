package com.hhplus.ecommerce.presentation;

import com.hhplus.ecommerce.presentation.dto.request.order.OrderRequestDto;
import com.hhplus.ecommerce.presentation.dto.response.order.OrderResponseDto;
import com.hhplus.ecommerce.presentation.facade.OrderFacade;
import com.hhplus.ecommerce.presentation.facade.PayFacade;
import com.hhplus.ecommerce.presentation.dto.request.order.CreateOrderRequestDto;
import com.hhplus.ecommerce.presentation.dto.request.pay.PayRequestDto;
import com.hhplus.ecommerce.presentation.dto.response.order.CreateOrderResponseDto;
import com.hhplus.ecommerce.presentation.dto.response.pay.PayResponseDto;
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
        name = "주문",
        description = "주문 기능 API"
)
@RequestMapping("/orders")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderFacade orderFacade;
    private final PayFacade payFacade;

    @Operation(
            summary = "주문 조회",
            description = "주문 조회 API",
            parameters = {
                    @Parameter(name = "userId", description = "사용자 아이디", required = true, example = "1", in = ParameterIn.PATH),
            },
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(schema = @Schema(implementation = OrderResponseDto.class))
            )
    )
    @PostMapping
    public OrderResponseDto getOrder(@RequestBody OrderRequestDto orderRequestDto) {

        return orderFacade.getUserOrder(orderRequestDto);
    }

    @Operation(
            summary = "주문 생성",
            description = "주문 생성 API",
            parameters = {
                    @Parameter(name = "itemId", description = "상품 번호", required = true, example = "1", in = ParameterIn.PATH),
            },
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(schema = @Schema(implementation = CreateOrderResponseDto.class))
            )
    )
    @PostMapping("/order")
    public CreateOrderResponseDto createOrder(@RequestBody CreateOrderRequestDto createOrderRequestDto) {

        return orderFacade.createOrder(createOrderRequestDto);
    }

    @Operation(
            summary = "결제",
            description = "결제 API",
            parameters = {
                    @Parameter(name = "orderId", description = "주문 번호", required = true, example = "1", in = ParameterIn.PATH),
            },
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(schema = @Schema(implementation = PayResponseDto.class))
            )
    )
    @PostMapping("/pay")
    public PayResponseDto pay(@RequestBody PayRequestDto payRequestDto) {

        return payFacade.receiveOrderToPay(payRequestDto);
    }
}
