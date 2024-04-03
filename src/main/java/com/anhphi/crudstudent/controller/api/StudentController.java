package com.anhphi.crudstudent.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anhphi.crudstudent.dto.PaginateRequest;
import com.anhphi.crudstudent.dto.StudentDto;
import com.anhphi.crudstudent.model.Student;
import com.anhphi.crudstudent.service.StudentService;

@RestController("apiStudentController")
@RequestMapping("/api/v1")
public class StudentController {
  @Autowired
  private StudentService studentService;

  @GetMapping("/students")
  public ResponseEntity<List<Student>> findAll(
      @RequestParam(name = "page", required = false, defaultValue = "0") int page,
      @RequestParam(name = "size", required = false, defaultValue = "5") int size,
      @RequestParam(name = "name", required = false) String name,
      @RequestParam(name = "age", required = false) String age,
      @RequestParam(name = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
      @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
      Model model) {
    Page<Student> students = studentService.findAll(
        new StudentDto().setAge(age).setName(name),
        new PaginateRequest(page, size).setDirection(direction).setSortBy(sortBy));

    return new ResponseEntity<List<Student>>(students.getContent(), HttpStatus.OK);
  }

}
