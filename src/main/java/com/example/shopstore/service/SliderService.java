package com.example.shopstore.service;

import com.example.shopstore.dto.SliderDTO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface SliderService {

    public SliderDTO addSlider(SliderDTO sliderDTO);

    public List<SliderDTO> getAllSlider();

    public void setActiveSlider(int isActive, int id);

    public List<SliderDTO> getAllSliderByIsActive(int isActive);

}
