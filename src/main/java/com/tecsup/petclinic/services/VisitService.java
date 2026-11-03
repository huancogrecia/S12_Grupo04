package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Visit;

public interface VisitService {

    Visit create(Visit visit);

    Visit update(Long id, Visit visit);

    void delete(Long id);
}
