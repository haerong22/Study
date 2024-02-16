package org.example.delivery.api.domain.userorder.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.delivery.api.domain.store.controller.model.StoreResponse;
import org.example.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOrderDetailResponse {

    private UserOrderResponse userOrderResponse;
    private StoreResponse storeResponse;
    private List<StoreMenuResponse> storeMenuResponseList;
}
