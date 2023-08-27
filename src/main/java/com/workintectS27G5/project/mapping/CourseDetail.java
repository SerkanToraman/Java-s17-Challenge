package com.workintectS27G5.project.mapping;


import com.workintectS27G5.project.model.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDetail {
    private Course course;
    private double totalGpa;
}
