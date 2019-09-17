package com.lambdaschool.school.controller;

import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.service.CourseService;
import com.lambdaschool.school.view.CountStudentsInCourses;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/courses")
public class CourseController
{
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "returns All Courses", response = Course.class, responseContainer = "ArrayList")

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})

    @GetMapping(value = "/courses", produces = {"application/json"})
    public ResponseEntity<?> listAllCourses(HttpServletRequest request,
                                            @PageableDefault(page = 0,
                                            size = 3)
                                            Pageable pageable)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        ArrayList<Course> myCourses = courseService.findAll(pageable);
        return new ResponseEntity<>(myCourses, HttpStatus.OK);
    }

    //http:localhost:2019/courses/allcourses
    @ApiOperation(value = "Return All Students Forreal",
                    response = Course.class,
                    responseContainer = "List")
    @GetMapping(value = "/allcourses",
            produces = {"application/json"})
    public ResponseEntity<?> reallyListAllCourses()
    {
        List<Course> myCourses = courseService.findAll(Pageable.unpaged());
        return new ResponseEntity<>(myCourses, HttpStatus.OK);
    }

    @ApiOperation(value = "Return a Count of Students in Courses",
            response = Course.class,
            responseContainer = "List")
    @GetMapping(value = "/studcount", produces = {"application/json"})
    public ResponseEntity<?> getCountStudentsInCourses(HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        ArrayList<CountStudentsInCourses> list = courseService.getCountStudentsInCourse();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @ApiOperation(value="Deletes a Course Based on Id",
                response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,
            message = "Course Successfully Deleted",
            response = void.class)
    })
    @DeleteMapping("/courses/{courseid}")
    public ResponseEntity<?> deleteCourseById(
            @ApiParam(value = "Course Id",
            required = true,
            example = "1")
            @PathVariable long courseid,
            HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        courseService.delete(courseid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
