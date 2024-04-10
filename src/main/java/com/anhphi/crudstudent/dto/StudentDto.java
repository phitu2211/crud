package com.anhphi.crudstudent.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.anhphi.crudstudent.model.Student;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class StudentDto {
  @NotEmpty
  private String name;
  private String date;

  public String getDate() {
    return date;
  }

  public LocalDateTime getStartDate() {
    String str = date.split("@")[0].trim();
    LocalDateTime dateTime = LocalDateTime.parse(str);
    return dateTime;
  }

  public LocalDateTime getEndDate() {
    String str = date.split("@")[1].trim();
    LocalDateTime dateTime = LocalDateTime.parse(str);
    return dateTime;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getName() {
    return name;
  }

  public StudentDto setName(String name) {
    this.name = name;
    return this;
  }

  @NotEmpty
  @Min(18)
  @Max(100)
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
