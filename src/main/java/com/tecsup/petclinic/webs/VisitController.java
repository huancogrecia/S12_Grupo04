package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.mapper.VisitMapper;
import com.tecsup.petclinic.services.VisitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VisitController {

    private final VisitService visitService;
    private final VisitMapper visitMapper;

    public VisitController(VisitService visitService, VisitMapper visitMapper) {
        this.visitService = visitService;
        this.visitMapper = visitMapper;
    }

    @GetMapping("/visits")
    public ResponseEntity<List<VisitDTO>> findAllVisits() {
        return ResponseEntity.ok(visitMapper.mapToDtoList(visitService.findAll()));
    }

    @GetMapping("/visits/{id}")
    public ResponseEntity<VisitDTO> findVisitById(@PathVariable Long id) {
        return visitService.findById(id)
                .map(visitMapper::mapToDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
