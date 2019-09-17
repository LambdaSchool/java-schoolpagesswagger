package com.lambdaschool.school.controller;

import com.lambdaschool.school.model.ErrorDetail;
import com.lambdaschool.school.model.Student;
import com.lambdaschool.school.service.StudentService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController
{
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    ///-----------------------------------------------------------------------------------------------
    @ApiOperation(value = "returns All Students with Paging and Sorting", response = Student.class, responseContainer = "List")

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})

    // Please note there is no way to add students to course yet!

    //http://localhost:2019/students/students/?page=0&size=1

    @GetMapping(value = "/students", produces = {"application/json"})
    public ResponseEntity<?> listAllStudentsWithPaging(HttpServletRequest request,
                                             @PageableDefault(page = 0,
                                             size = 3)
                                             Pageable pageable)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        List<Student> myStudents = studentService.findAll(pageable);
        return new ResponseEntity<>(myStudents, HttpStatus.OK);
    }


    ///-----------------------------------------------------------------------------------------------
    @ApiOperation(value = "Get All Students Forreal")
    //http:localhost:2019/students/allstudents
    @GetMapping(value = "/allstudents",
            produces = {"application/json"})
    public ResponseEntity<?> reallyListAllStudents()
    {
        List<Student> myStudents = studentService.findAll(Pageable.unpaged());
        return new ResponseEntity<>(myStudents, HttpStatus.OK);
    }


    ///-----------------------------------------------------------------------------------------------
    @ApiOperation(value = "Get Student associated with the studid", response = Student.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message="Student Found", response = Student.class),
            @ApiResponse(code = 404, message = "Student Not Found", response = ErrorDetail.class)
    })
    @GetMapping(value = "/Student/{StudentId}",
                produces = {"application/json"})
    public ResponseEntity<?> getStudentById(
            @ApiParam(value = "Restaurant Id", required = true, example = "1")
            @PathVariable
                    Long StudentId,
            HttpServletRequest request
            )
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        Student r = studentService.findStudentById(StudentId);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }


    ///-----------------------------------------------------------------------------------------------
    @ApiOperation(value = "Get Student Associated with the Students Name",
                  response = Student.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,
            message = "Student Found",
            response = Student.class),
            @ApiResponse(code = 404,
            message = "Student Not Found",
            response = ErrorDetail.class)
    })
    @GetMapping(value = "/student/namelike/{name}",
                produces = {"application/json"})
    public ResponseEntity<?> getStudentByNameContaining(
            @ApiParam(value = "Student Name",
            required = true,
            example = "John Doe")
            @PathVariable String name,
            HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        List<Student> myStudents = studentService.findStudentByNameLike(name);
        return new ResponseEntity<>(myStudents, HttpStatus.OK);
    }


    ///-----------------------------------------------------------------------------------------------
    @ApiOperation(value = "Creates A New Student",
                notes = "Newly created student id will be sent in the location header",
                response = void.class)
    @ApiResponses(value ={
            @ApiResponse(code = 201,
                    message = "Student Successfully Created",
                    response = void.class),
            @ApiResponse(code = 500,
                    message = "Error Creating Student",
                    response = ErrorDetail.class)
    })
    @PostMapping(value = "/Student",
                 consumes = {"application/json"},
                 produces = {"application/json"})
    public ResponseEntity<?> addNewStudent(@Valid
                                           @RequestBody
                                                   Student newStudent, HttpServletRequest request) throws URISyntaxException
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        newStudent = studentService.save(newStudent);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newStudentURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{Studentid}").buildAndExpand(newStudent.getStudid()).toUri();
        responseHeaders.setLocation(newStudentURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }


    ///-----------------------------------------------------------------------------------------------
    @ApiOperation(value = "Updates a student based on id", notes = "Newly Created student id sent in response header", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Student Updated Successfully", response = void.class),
    })
    @PutMapping(value = "/Student/{Studentid}")
    public ResponseEntity<?> updateStudent(
            @RequestBody
                    Student updateStudent,
            @ApiParam(value = "Student Id",
                    required = true,
                    example = "1")
            @PathVariable
                    long Studentid, HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        studentService.update(updateStudent, Studentid);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    ///-----------------------------------------------------------------------------------------------
    @ApiOperation(value = "Deletes Student Based on Id",  response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Student Deleted Successfully", response = void.class),
    })
    @DeleteMapping("/Student/{Studentid}")
    public ResponseEntity<?> deleteStudentById(
            @ApiParam(value = "Student Id",
            required = true,
            example = "1")
            @PathVariable
                    long Studentid, HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        studentService.delete(Studentid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
