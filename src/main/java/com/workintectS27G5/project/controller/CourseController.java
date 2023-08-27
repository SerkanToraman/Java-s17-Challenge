package com.workintectS27G5.project.controller;

import com.workintectS27G5.project.exception.CourseException;
import com.workintectS27G5.project.exception.CourseValidation;
import com.workintectS27G5.project.mapping.CourseDetail;
import com.workintectS27G5.project.mapping.CourseDetailFactory;
import com.workintectS27G5.project.model.Course;
import com.workintectS27G5.project.model.CourseGpa;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private List<Course> courses;

    private CourseGpa lowGpa;
    private CourseGpa mediumGpa;
    private CourseGpa highGpa;

    @Autowired
    public CourseController(@Qualifier("lowCourseGpa") CourseGpa lowGpa, @Qualifier("mediumCourseGpa") CourseGpa mediumGpa, @Qualifier("highCourseGpa") CourseGpa highGpa) {
        this.lowGpa = lowGpa;
        this.mediumGpa = mediumGpa;
        this.highGpa = highGpa;
    }

    @PostConstruct
    public void init() {
        courses = new ArrayList<>();
    }

    @GetMapping("/")
    public List<Course> get() {
        return courses;
    }

    @GetMapping("/{name}")
    public Course getByName(@PathVariable String name) {
        List<Course> requestedCourse = courses.stream().filter(course -> course.getName().equals(name)).collect(Collectors.toList());
        if (requestedCourse.size() == 0) {
            throw new CourseException("Course with given name is not exist: " + name, HttpStatus.BAD_REQUEST);
        }
        return requestedCourse.get(0);
    }
    @PostMapping("/")
    public CourseDetail create(@RequestBody Course course){
        CourseValidation.isIdValid(course.getId());
        CourseValidation.checkCourseValid(course);
        CourseValidation.isDublicateNameFound(courses, course.getName());

        courses.add(course);
        return CourseDetailFactory.createCourseDetail(course,lowGpa,mediumGpa,highGpa);
    }
    @PutMapping("/{id}")
    public Course update(@RequestBody Course course,@PathVariable int id){
        CourseValidation.checkCourseValid(course);
        Optional<Course> foundCourse = courses.stream().filter(c->c.getId()==id).findFirst();
        if(foundCourse.isPresent()) {
            int index = courses.indexOf(foundCourse.get());
            course.setId(id);
            courses.set(index, course);
            return course;
        } else{
             throw new CourseException("Course with given id is not exist: " + id, HttpStatus.BAD_REQUEST);
            }
        }

    @DeleteMapping("/{id}")
    public Course delete (@PathVariable int id){
        Optional<Course>foundCourse=courses.stream().filter(course->course.getId()==id).findFirst();
        if(foundCourse.isPresent()){
            int index = courses.indexOf(foundCourse.get());
            courses.remove(index);
            return foundCourse.get();
        }else{
            throw new CourseException("Course with given id is not exist: " + id, HttpStatus.BAD_REQUEST);
        }
        }


}
