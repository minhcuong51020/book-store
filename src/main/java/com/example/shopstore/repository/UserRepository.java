package com.example.shopstore.repository;

import com.example.shopstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {

    public User getUserByUsername(String username);

    @Modifying
    @Query(value = "update User set fullName=?1, phone=?2, address=?3, email=?4  where id=?5", nativeQuery = true)
    public int updateInfoUser(String fullName, String phone, String address, String email, int id);

}
