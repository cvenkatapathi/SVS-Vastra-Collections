package com.svs.svscollections.service;

import com.svs.svscollections.model.Cart;
import com.svs.svscollections.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public void addToCart(Cart cart) {
        cartRepository.save(cart);
    }

    public List<Cart> getAllCartItems() {
        return cartRepository.findAll();
    }

    public void removeFromCart(int id) {
        cartRepository.deleteById(id);
    }

    public void clearCart() {
        cartRepository.deleteAll();
    }
}