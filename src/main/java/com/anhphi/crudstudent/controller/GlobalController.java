package com.anhphi.crudstudent.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.anhphi.crudstudent.dto.StudentDto;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalController {
  @ModelAttribute("uriBuilder")
  public ServletUriComponentsBuilder getUriBuilder() {
    return ServletUriComponentsBuilder.fromCurrentRequest();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public String handleValidateException(MethodArgumentNotValidException e, HttpServletRequest request,
      RedirectAttributes redirectAttributes) {
    String uri = request.getRequestURI();
    redirectAttributes.addFlashAttribute("error", e);
    return "redirect:" + uri;
  }
}
