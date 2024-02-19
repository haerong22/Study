package org.example.delivery.storeadmin.doamin.userorder.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.delivery.storeadmin.doamin.storemenu.controller.model.StoreMenuResponse;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderDetailResponse {

    private UserOrderResponse userOrderResponse;
    private List<StoreMenuResponse> storeMenuResponseList;
}