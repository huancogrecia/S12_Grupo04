package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.repositories.PetRepository;
import com.tecsup.petclinic.repositories.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final PetRepository petRepository;

    @Override
    @Transactional
    public Visit create(Visit visit) {
        attachExistingPet(visit);
        visit.setId(null);
        return visitRepository.save(visit);
    }

    @Override
    @Transactional
    public Visit update(Long id, Visit visit) {
        Visit existing = visitRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Visit not found: " + id));

        existing.setVisitDate(visit.getVisitDate());
        existing.setDescription(visit.getDescription());
        if (visit.getPet() != null) {
            attachExistingPet(visit);
            existing.setPet(visit.getPet());
        }

        return visitRepository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Visit not found: " + id));
        visitRepository.delete(visit);
    }

    private void attachExistingPet(Visit visit) {
        if (visit.getPet() == null || visit.getPet().getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "A visit must reference an existing pet");
        }
        Pet pet = petRepository.getReferenceById(visit.getPet().getId());
        visit.setPet(pet);
    }
}
