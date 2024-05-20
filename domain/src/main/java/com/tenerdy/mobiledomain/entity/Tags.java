package com.tenerdy.mobiledomain.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tags {
    private Integer id;
    private String categoryId;
    private String name;
    private Integer weight;
}
