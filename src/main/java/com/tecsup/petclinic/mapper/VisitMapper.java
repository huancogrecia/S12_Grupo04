package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Visit;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VisitMapper {

    VisitDTO mapToDto(Visit visit);

    List<VisitDTO> mapToDtoList(List<Visit> visits);
}
