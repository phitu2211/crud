package com.anhphi.crudstudent.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.anhphi.crudstudent.dto.PaginateRequest;
import com.anhphi.crudstudent.dto.StudentDto;
import com.anhphi.crudstudent.model.Student;
import com.anhphi.crudstudent.service.StudentService;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("student")
public class StudentController {
  @Autowired
  private StudentService studentService;

  @GetMapping
  public String list(
      @RequestParam(name = "page", required = false, defaultValue = "0") int page,
      @RequestParam(name = "size", required = false, defaultValue = "5") int size,
      @RequestParam(name = "name", required = false) String name,
      @RequestParam(name = "age", required = false) String age,
      Model model) {
    // new StudentDto()
    Page<Student> students = studentService.findAll(
        new StudentDto().setAge(age).setName(name),
        new PaginateRequest(page, size));

    model.addAttribute("pageBegin", Math.max(1, page));
    model.addAttribute("pageEnd", Math.min(page + 2, students.getTotalPages()));
    model.addAttribute("totalPages", students.getTotalPages());
    model.addAttribute("currentPage", page);
    model.addAttribute("size", size);
    model.addAttribute("students", students.getContent());
    return "student/list";
  }

  @GetMapping("/create")
  public String createForm(Model model) {
    return "student/create";
  }

  @PostMapping("/create")
  public String create(@ModelAttribute StudentDto dto) {
    studentService.getStudentRepo().save(dto.toStudent());
    return "redirect:/student";
  }

  @GetMapping("/delete/{id}")
  public String delete(@PathVariable("id") Long id) {
    if (studentService.getStudentRepo().findById(id).isPresent()) {
      studentService.getStudentRepo().deleteById(id);
    }

    return "redirect:/student";
  }

  @GetMapping("/edit/{id}")
  public String editForm(@PathVariable("id") Long id, Model model) {
    Student student = studentService.getStudentRepo().findById(id).get();
    model.addAttribute("student", student);
    return "student/edit";
  }

  @PostMapping("/edit/{id}")
  public String update(@PathVariable("id") Long id, @ModelAttribute StudentDto dto, Model model) {
    if (studentService.getStudentRepo().findById(id).isPresent()) {
      Student student = studentService.getStudentRepo().findById(id).get();
      student.setAge(dto.getAge());
      student.setName(dto.getName());
      studentService.getStudentRepo().save(student);
    }

    return "redirect:/student";
  }
}