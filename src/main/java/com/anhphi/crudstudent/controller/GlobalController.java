package com.anhphi.crudstudent.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@ControllerAdvice
public class GlobalController {
  @ModelAttribute("uriBuilder")
  public ServletUriComponentsBuilder getUriBuilder() {
    return ServletUriComponentsBuilder.fromCurrentRequest();
  }
}
