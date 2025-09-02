package com.V.FoodApp.auth_users.entity;


import com.V.FoodApp.cart.entity.Cart;
import com.V.FoodApp.orders.entity.Order;
import com.V.FoodApp.payment.entity.Payment;
import com.V.FoodApp.review.entity.Review;
import com.V.FoodApp.role.entity.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    @NotBlank(message = "password is required")
    private String password;


    private String phonenumber;

    private String profileUrl;

    private String address;

    private boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Order> orders;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Payment> payments;

    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL)
    private Cart cart;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
