package com.example.shopstore.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class SliderDTO {

    private int id;
    private String name;
    private String thumbnail;
    private int isActive;
    private MultipartFile file;

}
