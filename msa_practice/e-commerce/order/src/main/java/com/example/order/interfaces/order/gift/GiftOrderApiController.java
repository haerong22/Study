package com.example.order.interfaces.order.gift;

import com.example.order.application.order.OrderFacade;
import com.example.order.application.order.gift.GiftFacade;
import com.example.order.common.response.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gift-orders")
public class GiftOrderApiController {
    private final OrderFacade orderFacade;
    private final GiftFacade giftFacade;
    private final GiftOrderDtoMapper giftOrderDtoMapper;

    @PostMapping("/init")
    public CommonResponse registerOrder(@RequestBody @Valid GiftOrderDto.RegisterOrderRequest request) {
        var orderCommand = giftOrderDtoMapper.of(request);
        var result = orderFacade.registerOrder(orderCommand);
        var response = giftOrderDtoMapper.of(result);
        return CommonResponse.success(response);
    }

    @PostMapping("/payment-order")
    public CommonResponse paymentOrder(@RequestBody @Valid GiftOrderDto.PaymentRequest request) {
        var orderPaymentCommand = giftOrderDtoMapper.of(request);
        giftFacade.paymentOrder(orderPaymentCommand);
        return CommonResponse.success("OK");
    }

    @PostMapping("/{orderToken}/update-receiver-info")
    public CommonResponse updateReceiverInfo(
            @PathVariable String orderToken,
            @RequestBody @Valid GiftOrderDto.UpdateReceiverInfoReq request
    ) {
        var orderCommand = request.toCommand();
        orderFacade.updateReceiverInfo(orderToken, orderCommand);
        return CommonResponse.success("OK");
    }
}