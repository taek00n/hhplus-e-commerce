package com.hhplus.ecommerce.presentation;

import com.hhplus.ecommerce.common.exception.domain.CommonErrorCode;
import com.hhplus.ecommerce.common.exception.RestApiException;
import com.hhplus.ecommerce.presentation.dto.request.user.UserBalanceRequestDto;
import com.hhplus.ecommerce.presentation.dto.request.user.UserChargeRequestDto;
import com.hhplus.ecommerce.presentation.dto.response.user.UserBalanceResponseDto;
import com.hhplus.ecommerce.presentation.dto.response.user.UserChargeResponseDto;
import com.hhplus.ecommerce.presentation.facade.UserFacade;
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
        name = "사용자",
        description = "사용자 기능 API"
)
@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;

    @Operation(
            summary = "보유 잔액 조회",
            description = "보유 잔액 조회 API",
            parameters = {
                    @Parameter(name = "userId", description = "사용자 아이디", required = true, example = "1", in = ParameterIn.PATH),
            },
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(schema = @Schema(implementation = UserBalanceResponseDto.class))
            )
    )
    @PostMapping
    public UserBalanceResponseDto getBalance(@RequestBody UserBalanceRequestDto userBalanceRequestDto) throws RestApiException {

        return userFacade.getUserBalance(userBalanceRequestDto);
    }

    @Operation(
            summary = "잔액 충전",
            description = "잔액 충전 API",
            parameters = {
                    @Parameter(name = "userId", description = "사용자 아이디", required = true, example = "1", in = ParameterIn.PATH),
                    @Parameter(name = "balance", description = "충전 금액", required = true, example = "1", in = ParameterIn.PATH),
            },
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(schema = @Schema(implementation = UserBalanceResponseDto.class))
            )
    )
    @PostMapping("/charge")
    public UserChargeResponseDto chargeBalance(@RequestBody UserChargeRequestDto userChargeRequestDto) {

        return userFacade.chargeUserBalance(userChargeRequestDto);
    }

}
