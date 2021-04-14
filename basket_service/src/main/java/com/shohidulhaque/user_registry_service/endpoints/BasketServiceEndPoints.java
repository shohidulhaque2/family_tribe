package com.shohidulhaque.user_registry_service.endpoints;

import com.shohidulhaque.user_registry_service.BasketService;
import javax.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/basket-service")
public class BasketServiceEndPoints {

    private final static Logger Logger = LoggerFactory.getLogger(BasketServiceEndPoints.class);
    private final Validator validator;
    private final BasketService basketService;
    //==============================================================================================
    @Autowired
    public BasketServiceEndPoints(
        BasketService basketService,
        Validator validator) {
        this.validator = validator;
        this.basketService = basketService;
    }
}
