package com.lambdaschool.school.service;

import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.view.CountStudentsInCourses;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;

public interface CourseService
{
    ArrayList<Course> findAll(Pageable pageable);

    ArrayList<CountStudentsInCourses> getCountStudentsInCourse();

    void delete(long id);
}
