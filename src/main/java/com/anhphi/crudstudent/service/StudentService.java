package com.anhphi.crudstudent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.anhphi.crudstudent.dto.PaginateRequest;
import com.anhphi.crudstudent.dto.StudentDto;
import com.anhphi.crudstudent.dto.StudentSpec;
import com.anhphi.crudstudent.model.Student;
import com.anhphi.crudstudent.repository.StudentRepo;

@Service
public class StudentService {
  @Autowired
  private StudentRepo studentRepo;

  public StudentRepo getStudentRepo() {
    return studentRepo;
  }

  public Page<Student> findAll(StudentDto dto, PaginateRequest paginateRequest) {
    return studentRepo.findAll(new StudentSpec(dto),
        PageRequest.of(paginateRequest.getPage(), paginateRequest.getSize()));
  }
}
