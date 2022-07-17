package com.example.shopstore.repository;
import com.example.shopstore.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PublisherRepository extends JpaRepository<Publisher, Integer> {

    public List<Publisher> getAllByIsActive(int isActive);

}
