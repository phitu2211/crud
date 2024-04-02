package com.anhphi.crudstudent.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.anhphi.crudstudent.model.Student;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class StudentSpec implements Specification<Student> {
  private StudentDto dto;

  public StudentSpec(StudentDto dto) {
    this.dto = dto;
  }

  @Override
  public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<Predicate>();

    if (dto.getName() != null) {
      predicates.add(
          criteriaBuilder.like(root.get("name"),
              "%" + dto.getName() + "%"));
    }

    if (dto.getAge() != 0) {
      predicates.add(
          criteriaBuilder.equal(root.get("age"), dto.getAge()));
    }

    if (!predicates.isEmpty()) {
      query.where(predicates.toArray(new Predicate[0]));
    }

    return query.getRestriction();
  }
}
