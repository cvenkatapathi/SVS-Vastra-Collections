package com.svs.svscollections;


import java.util.List;
import com.svs.svscollections.service.OrderService;
import com.svs.svscollections.model.Product;
import com.svs.svscollections.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import com.svs.svscollections.service.UserService;
import com.svs.svscollections.model.Order;
import org.springframework.web.bind.annotation.PathVariable;
import com.svs.svscollections.model.User;
import java.time.Month;
import com.svs.svscollections.service.SettingsService;
import com.svs.svscollections.model.Settings;




import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SettingsService settingsService;


    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin-products";
    }

    @PostMapping("/save")
    public String saveProduct(
            @ModelAttribute Product product,
            @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        if (!imageFile.isEmpty()) {

            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();

            Path uploadPath = Paths.get("uploads");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Files.copy(
                    imageFile.getInputStream(),
                    uploadPath.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING
            );

            product.setImage("/uploads/" + fileName);
        }

        productService.saveProduct(product);

        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable int id, Model model) {

        Product product = productService.getProductById(id);

        if (product == null) {
            model.addAttribute("errorMessage", "Product not found.");
            return "admin-error";
        }

        model.addAttribute("product", product);

        return "add-product";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        List<Product> products = productService.getAllProducts();
        List<Order> orders = orderService.getAllOrders();
        List<User> users = userService.getAllUsers();


        double totalRevenue = orders.stream()
                .mapToDouble(Order::getAmount)
                .sum();


        List<Order> recentOrders = orders.stream()
                .sorted((first, second) -> Integer.compare(
                        second.getId(),
                        first.getId()
                ))
                .limit(5)
                .toList();


        long pendingOrders = orders.stream()
                .filter(order -> "Pending".equalsIgnoreCase(order.getStatus()))
                .count();

        long placedOrders = orders.stream()
                .filter(order -> "Placed".equalsIgnoreCase(order.getStatus()))
                .count();

        long deliveredOrders = orders.stream()
                .filter(order -> "Delivered".equalsIgnoreCase(order.getStatus()))
                .count();

        long cancelledOrders = orders.stream()
                .filter(order -> "Cancelled".equalsIgnoreCase(order.getStatus()))
                .count();


        model.addAttribute("productCount", products.size());
        model.addAttribute("orderCount", orders.size());
        model.addAttribute("customerCount", users.size());
        model.addAttribute("totalRevenue", totalRevenue);


        model.addAttribute("recentOrders", recentOrders);

        model.addAttribute("pendingOrders", pendingOrders);
        model.addAttribute("placedOrders", placedOrders);
        model.addAttribute("deliveredOrders", deliveredOrders);
        model.addAttribute("cancelledOrders", cancelledOrders);

        return "admin-dashboard";
    }
    @GetMapping("/add-product")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        return "add-product";
    }

    @GetMapping("/orders")
    public String orders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "manage-orders";
    }

    @PostMapping("/orders/update")
    public String updateOrderStatus(
            @RequestParam int id,
            @RequestParam String status) {

        orderService.updateStatus(id, status);

        return "redirect:/admin/orders";
    }

    @GetMapping("/login")
    public String adminLogin() {
        return "admin-login";
    }

    @PostMapping("/login")
    public String adminLoginCheck(@RequestParam String username,
                                  @RequestParam String password,
                                  HttpSession session) {

        if (username.equals("admin") && password.equals("admin123")) {
            session.setAttribute("admin", "admin");
            return "redirect:/admin/dashboard";
        }

        return "redirect:/admin/login?error";
    }

    @GetMapping("/customers")
    public String customers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "customers";
    }

    @GetMapping("/customers/view/{id}")
    public String viewCustomer(@PathVariable int id, Model model) {

        User user = userService.getUserById(id);

        if (user == null) {
            model.addAttribute("errorMessage", "Customer not found.");
            return "admin-error";
        }

        List<Order> allOrders = orderService.getAllOrders();

        List<Order> customerOrders = allOrders.stream()
                .filter(order -> {

                    boolean emailMatch =
                            user.getEmail() != null
                                    && order.getEmail() != null
                                    && order.getEmail().trim()
                                    .equalsIgnoreCase(user.getEmail().trim());

                    boolean nameMatch =
                            user.getFullName() != null
                                    && order.getCustomerName() != null
                                    && order.getCustomerName().trim()
                                    .equalsIgnoreCase(user.getFullName().trim());

                    return emailMatch || nameMatch;
                })
                .toList();

        long totalOrders = customerOrders.size();

        long deliveredOrders = customerOrders.stream()
                .filter(order ->
                        "Delivered".equalsIgnoreCase(order.getStatus())
                )
                .count();

        long cancelledOrders = customerOrders.stream()
                .filter(order ->
                        "Cancelled".equalsIgnoreCase(order.getStatus())
                )
                .count();

        double totalSpent = customerOrders.stream()
                .mapToDouble(Order::getAmount)
                .sum();

        model.addAttribute("user", user);
        model.addAttribute("customerOrders", customerOrders);
        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("deliveredOrders", deliveredOrders);
        model.addAttribute("cancelledOrders", cancelledOrders);
        model.addAttribute("totalSpent", totalSpent);

        return "customer-details";
    }

    @GetMapping("/settings")
    public String settings(Model model) {

        Settings settings = settingsService.getSettings();

        if (settings == null) {
            settings = new Settings();
        }

        model.addAttribute("settings", settings);

        return "settings";
    }

    @PostMapping("/settings")
    public String saveSettings(@ModelAttribute Settings settings) {

        settingsService.saveSettings(settings);

        return "redirect:/admin/settings";
    }

    @GetMapping("/sales-report")
    public String salesReport(Model model) {

        List<Order> orders = orderService.getAllOrders();

        int totalOrders = orders.size();
        int deliveredOrders = 0;
        double totalRevenue = 0;

        for (Order order : orders) {

            totalRevenue += order.getAmount();

            if ("Delivered".equalsIgnoreCase(order.getStatus())) {
                deliveredOrders++;
            }
        }

        double averageOrderValue = 0;

        if (totalOrders > 0) {
            averageOrderValue = totalRevenue / totalOrders;
        }

        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("deliveredOrders", deliveredOrders);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("averageOrderValue", averageOrderValue);

        model.addAttribute(
                "monthlyOrders",
                orderService.getMonthlyOrderCount()
        );

        model.addAttribute(
                "monthlyRevenue",
                orderService.getMonthlyRevenue()
        );

        return "sales-report";
    }

    @GetMapping("/orders/view/{id}")
    public String viewOrder(@PathVariable int id, Model model) {

        Order order = orderService.getOrderById(id);

        if (order == null) {
            model.addAttribute("errorMessage", "Order not found.");
            return "admin-error";
        }

        model.addAttribute("order", order);

        return "order-details";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/admin/login";
    }

}
