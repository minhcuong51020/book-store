package com.example.shopstore.repository;

import com.example.shopstore.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Orders, Integer> {

    public List<Orders> findAllByUserIdAndStatusOrderByIdDesc(int id, int status);

    public Orders getOrdersByIdAndUserId(int id, int userId);

    public List<Orders> findAllByStatus(int status);

    @Modifying
    @Query(value = "update Orders set status=?1 where id=?2", nativeQuery = true)
    public int setStatusOrdersById(int status, int id);

}
