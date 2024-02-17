package org.example.delivery.storeadmin.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping(path = {"/", "/main"})
    public String mainPage() {
        return "/main";
    }

    @GetMapping("/order")
    public String orderPage() {
        return "/order/order";
    }
}
