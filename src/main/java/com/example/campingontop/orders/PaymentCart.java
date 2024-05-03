package com.example.campingontop.orders;

import com.example.campingontop.cart.model.Cart;
import com.example.campingontop.house.model.House;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaymentCart {
    List<Cart> cartList;
}
