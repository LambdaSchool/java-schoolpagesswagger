package com.lambdaschool.school.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "Course", description = "Course Entity")
@Entity
@Table(name = "course")
public class Course
{
    @ApiModelProperty(name = "courseid", value = "Primary Key for Courses", required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long courseid;

    @ApiModelProperty(name = "coursename", value = "Name ofCourses", required = true, example = "Math")
    private String coursename;

    @ManyToOne
    @JoinColumn(name = "instructid")
    @JsonIgnoreProperties("courses")
    private Instructor instructor;

    @ManyToMany(mappedBy = "courses")
    @JsonIgnoreProperties("courses")
    private List<Student> students = new ArrayList<>();

    public Course()
    {
    }

    public Course(String coursename)
    {
        this.coursename = coursename;
    }

    public Course(String coursename, Instructor instructor)
    {
        this.coursename = coursename;
        this.instructor = instructor;
    }

    public long getCourseid()
    {
        return courseid;
    }

    public void setCourseid(long courseid)
    {
        this.courseid = courseid;
    }

    public String getCoursename()
    {
        return coursename;
    }

    public void setCoursename(String coursename)
    {
        this.coursename = coursename;
    }

    public Instructor getInstructor()
    {
        return instructor;
    }

    public void setInstructor(Instructor instructor)
    {
        this.instructor = instructor;
    }

    public List<Student> getStudents()
    {
        return students;
    }

    public void setStudents(List<Student> students)
    {
        this.students = students;
    }
}
