package com.example.shopstore.repository;

import com.example.shopstore.entity.Slider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SliderRepository extends JpaRepository<Slider, Integer> {
    @Modifying
    @Query(value = "update Slider set is_active=?1 where id=?2", nativeQuery = true)
    public void setActiveSlider(int isActive, int id);
    public List<Slider> findByIsActive(int isActive);

}
