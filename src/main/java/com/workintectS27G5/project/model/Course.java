package com.workintectS27G5.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Course {
    private int id;
    private String name;
    private int credit;
    private Grade grade;
}
