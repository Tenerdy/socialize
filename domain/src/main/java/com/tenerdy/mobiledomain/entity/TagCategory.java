package com.tenerdy.mobiledomain.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagCategory {
    private Integer id;
    private String name;
    private String description;
    private Integer weight;
}
