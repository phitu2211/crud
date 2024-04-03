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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.anhphi.crudstudent.dto.PaginateRequest;
import com.anhphi.crudstudent.dto.StudentDto;
import com.anhphi.crudstudent.model.Student;
import com.anhphi.crudstudent.service.StudentService;

import jakarta.validation.Valid;

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
      @RequestParam(name = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
      @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
      Model model) {
    Page<Student> students = studentService.findAll(
        new StudentDto().setAge(age).setName(name),
        new PaginateRequest(page, size).setDirection(direction).setSortBy(sortBy));

    model.addAttribute("direction", direction);
    model.addAttribute("reverseDirection", direction.equals("asc") ? "desc" : "asc");
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
  public String create(@Valid @ModelAttribute StudentDto dto, RedirectAttributes redirectAttributes) {
    studentService.getStudentRepo().save(dto.toStudent());
    redirectAttributes.addFlashAttribute("msg", "Tạo mới sinh viên thành công");
    return "redirect:/student";
  }

  @GetMapping("/delete/{id}")
  public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    if (studentService.getStudentRepo().findById(id).isPresent()) {
      studentService.getStudentRepo().deleteById(id);
    }
    redirectAttributes.addFlashAttribute("msg", "Xóa sinh viên thành công");

    return "redirect:/student";
  }

  @GetMapping("/edit/{id}")
  public String editForm(@PathVariable("id") Long id, Model model) {
    Student student = studentService.getStudentRepo().findById(id).get();
    model.addAttribute("student", student);
    return "student/edit";
  }

  @PostMapping("/edit/{id}")
  public String update(@PathVariable("id") Long id, @Valid @ModelAttribute StudentDto dto, Model model,
      RedirectAttributes redirectAttributes) {
    if (studentService.getStudentRepo().findById(id).isPresent()) {
      Student student = studentService.getStudentRepo().findById(id).get();
      student.setAge(dto.getAge());
      student.setName(dto.getName());
      studentService.getStudentRepo().save(student);
    }
    redirectAttributes.addFlashAttribute("msg", "Sửa sinh viên thành công");
    // redirectAttributes.addFlashAttribute("status", "error");

    return "redirect:/student";
  }
}
