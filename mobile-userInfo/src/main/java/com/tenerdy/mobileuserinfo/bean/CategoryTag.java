package com.tenerdy.mobileuserinfo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryTag {
    private String category;
    private Integer id;
    private String title;
    private Boolean selected;
}

