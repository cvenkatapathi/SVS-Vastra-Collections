package com.svs.svscollections;

import com.svs.svscollections.model.Cart;
import com.svs.svscollections.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/cart")
    public String cart(Model model) {

        List<Cart> cartItems = cartService.getAllCartItems();

        double grandTotal = 0;

        for (Cart item : cartItems) {
            grandTotal += item.getPrice() * item.getQuantity();
        }

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("grandTotal", grandTotal);

        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(
            @RequestParam String name,
            @RequestParam double price,
            @RequestParam String image,
            @RequestParam(defaultValue = "1") int quantity) {

        Cart cart = new Cart(name, price, image, quantity);
        cartService.addToCart(cart);

        return "redirect:/cart";
    }

    @GetMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable int id) {

        cartService.removeFromCart(id);

        return "redirect:/cart";
    }
}