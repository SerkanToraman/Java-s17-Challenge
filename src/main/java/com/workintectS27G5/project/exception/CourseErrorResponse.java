package com.workintectS27G5.project.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseErrorResponse {
    private int status;
    private String message;
    private long timestamp;
}
