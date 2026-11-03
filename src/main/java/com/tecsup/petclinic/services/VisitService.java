package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Visit;

import java.util.List;
import java.util.Optional;

public interface VisitService {

    List<Visit> findAll();

    Optional<Visit> findById(Long id);
}
