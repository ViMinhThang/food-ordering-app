package com.V.FoodApp.orders.repository;

import com.V.FoodApp.orders.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

    @Query("SELECT CASE WHEN COUNT(o1) > 0 THEN true ELSE false END "+
    "FROM OrderItem oi "+
    "WHERE oi.order.id = :orderId AND oi.menu.id = :menuId")
    boolean existByOrderIdAndMenuId(@Param("orderId")  Long orderId,@Param("menuId") Long menuId);
}
