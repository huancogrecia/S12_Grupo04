package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.mapper.VisitMapper;
import com.tecsup.petclinic.services.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;
    private final VisitMapper visitMapper;

    @GetMapping
    public ResponseEntity<List<VisitDTO>> findAllVisits() {
        return ResponseEntity.ok(visitMapper.mapToDtoList(visitService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitDTO> findVisitById(@PathVariable Long id) {
        return visitService.findById(id)
                .map(visitMapper::mapToDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VisitDTO> create(@RequestBody Visit visit) {
        Visit created = visitService.create(visit);
        return ResponseEntity.status(HttpStatus.CREATED).body(visitMapper.mapToDto(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VisitDTO> update(
            @PathVariable Long id,
            @RequestBody Visit visit) {
        Visit updated = visitService.update(id, visit);
        return ResponseEntity.ok(visitMapper.mapToDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        visitService.delete(id);
        return ResponseEntity.ok("Deleted visit with ID: " + id);
    }
}
