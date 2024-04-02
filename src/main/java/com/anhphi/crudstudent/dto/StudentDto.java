package com.anhphi.crudstudent.dto;

import com.anhphi.crudstudent.model.Student;

public class StudentDto {
  private String name;

  public String getName() {
    return name;
  }

  public StudentDto setName(String name) {
    this.name = name;
    return this;
  }

  private String age;

  public int getAge() {
    if (age == null || age.isEmpty())
      return 0;

    return Integer.parseInt(age);
  }

  public StudentDto setAge(String age) {
    this.age = age;
    return this;
  }

  public Student toStudent() {
    Student student = new Student();
    student.setName(name);
    student.setAge(getAge());
    return student;
  }
}
