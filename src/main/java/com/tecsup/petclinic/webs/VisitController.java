package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.services.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;

    @PostMapping
    public ResponseEntity<VisitResponse> create(@RequestBody Visit visit) {
        Visit created = visitService.create(visit);
        return ResponseEntity.status(HttpStatus.CREATED).body(VisitResponse.from(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VisitResponse> update(
            @PathVariable Long id,
            @RequestBody Visit visit) {
        Visit updated = visitService.update(id, visit);
        return ResponseEntity.ok(VisitResponse.from(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        visitService.delete(id);
        return ResponseEntity.ok("Deleted visit with ID: " + id);
    }

    public record VisitResponse(Long id, LocalDate visitDate, String description, Integer petId) {
        private static VisitResponse from(Visit visit) {
            Integer petId = visit.getPet() == null ? null : visit.getPet().getId();
            return new VisitResponse(visit.getId(), visit.getVisitDate(), visit.getDescription(), petId);
        }
    }
}
