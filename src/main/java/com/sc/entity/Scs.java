package com.sc.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Scs {
    private Integer id;
    private String no;
    private String course;
    private Double score;
}
