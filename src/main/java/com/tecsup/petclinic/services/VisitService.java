package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Visit;

import java.util.List;
import java.util.Optional;

public interface VisitService {

    List<Visit> findAll();

    Optional<Visit> findById(Long id);

    Visit create(Visit visit);

    Visit update(Long id, Visit visit);

    void delete(Long id);
}
