package com.svs.svscollections;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.svs.svscollections.service.ProductService;
import org.springframework.web.bind.annotation.PathVariable;
import com.svs.svscollections.model.Product;
import com.svs.svscollections.model.Cart;
import com.svs.svscollections.service.CartService;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;
import com.svs.svscollections.model.Order;
import com.svs.svscollections.service.OrderService;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.svs.svscollections.model.User;
import com.svs.svscollections.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;


@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;


    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/shop")
    public String shop(Model model) {
        model.addAttribute("products",
                productService.getAllProducts());
        return "shop";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            HttpSession session) {

        User user = userService.login(email, password);

        if (user != null) {
            session.setAttribute("loggedInUser", user);
            return "redirect:/shop";
        }

        return "redirect:/login?error";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(@ModelAttribute User user) {

        userService.registerUser(user);

        return "redirect:/login";
    }

    @GetMapping("/wishlist")
    public String wishlist() {
        return "wishlist";
    }

    @GetMapping("/orders")
    public String orders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "orders";
    }

    @GetMapping("/product/{id}")
    public String product(@PathVariable int id, Model model) {

        Product product = productService.getProductById(id);

        if (product == null) {
            model.addAttribute("errorMessage", "Product not found.");
            return "admin-error";
        }

        model.addAttribute("product", product);

        return "product";
    }

    @GetMapping("/checkout")
    public String checkout() {
        return "checkout";
    }

    @PostMapping("/place-order")
    public String placeOrder(
            @ModelAttribute Order order,
            HttpSession session) {

        List<Cart> cartItems = cartService.getAllCartItems();

        // Do not continue if cart is empty
        if (cartItems == null || cartItems.isEmpty()) {
            return "redirect:/cart";
        }

        // COD → immediately create orders
        if ("Cash on Delivery".equalsIgnoreCase(order.getPaymentMethod())) {

            for (Cart item : cartItems) {

                Order newOrder = new Order();

                newOrder.setCustomerName(order.getCustomerName());
                newOrder.setEmail(order.getEmail());
                newOrder.setPhone(order.getPhone());
                newOrder.setAddress(order.getAddress());
                newOrder.setPaymentMethod(order.getPaymentMethod());

                newOrder.setProductName(item.getName());
                newOrder.setQuantity(item.getQuantity());

                double total = item.getPrice() * item.getQuantity();
                newOrder.setAmount(total);

                newOrder.setStatus("Placed");
                newOrder.setOrderDate(LocalDate.now());

                orderService.saveOrder(newOrder);
            }

            cartService.clearCart();

            return "redirect:/order-success";
        }

        // Online payment → store checkout + cart items in session
        session.setAttribute("pendingOrder", order);
        session.setAttribute("pendingCartItems", new ArrayList<>(cartItems));

        return "redirect:/payment";
    }

    @GetMapping("/payment")
    public String payment(HttpSession session, Model model) {

        Order pendingOrder = (Order) session.getAttribute("pendingOrder");

        // No pending checkout information
        if (pendingOrder == null) {
            return "redirect:/checkout";
        }

        model.addAttribute("pendingOrder", pendingOrder);

        return "payment";
    }

    @PostMapping("/payment/process")
    public String processPayment(HttpSession session) {

        Order pendingOrder =
                (Order) session.getAttribute("pendingOrder");

        @SuppressWarnings("unchecked")
        List<Cart> pendingCartItems =
                (List<Cart>) session.getAttribute("pendingCartItems");

        // No pending payment session
        if (pendingOrder == null || pendingCartItems == null) {
            return "redirect:/checkout";
        }

        // Only online payment methods
        String paymentMethod = pendingOrder.getPaymentMethod();

        boolean isOnlinePayment =
                "UPI".equalsIgnoreCase(paymentMethod)
                        || "Credit/Debit Card".equalsIgnoreCase(paymentMethod);

        if (!isOnlinePayment) {
            return "redirect:/checkout";
        }

        // Saved cart snapshot is empty
        if (pendingCartItems.isEmpty()) {
            session.removeAttribute("pendingOrder");
            session.removeAttribute("pendingCartItems");

            return "redirect:/cart";
        }


         /* Demo/local payment:
         * Clicking Pay Now is treated as successful.
         */
        for (Cart item : pendingCartItems) {

            Order newOrder = new Order();

            newOrder.setCustomerName(pendingOrder.getCustomerName());
            newOrder.setEmail(pendingOrder.getEmail());
            newOrder.setPhone(pendingOrder.getPhone());
            newOrder.setAddress(pendingOrder.getAddress());
            newOrder.setPaymentMethod(pendingOrder.getPaymentMethod());

            newOrder.setProductName(item.getName());
            newOrder.setQuantity(item.getQuantity());

            double total = item.getPrice() * item.getQuantity();
            newOrder.setAmount(total);

            newOrder.setStatus("Placed");
            newOrder.setOrderDate(LocalDate.now());

            orderService.saveOrder(newOrder);
        }

        // Clear the real cart only after successful payment
        cartService.clearCart();

        // Removes  temporary payment data
        session.removeAttribute("pendingOrder");
        session.removeAttribute("pendingCartItems");

        return "redirect:/order-success";
    }

    @GetMapping("/order-success")
    public String orderSuccess() {
        return "order-success";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }
}

