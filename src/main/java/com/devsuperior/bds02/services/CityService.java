package com.devsuperior.bds02.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.services.exceptions.DatabaseException;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class CityService {
    

    @Autowired
    CityRepository repository;

    @Transactional(readOnly = true)
    public List<CityDTO> findAll(){
        List<City> cities = repository.findAll(Sort.by("name"));
        return cities.stream().map(city -> new ModelMapper().map(city, CityDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public CityDTO insert(CityDTO dto){
        dto.setId(null);
        ModelMapper mapper = new ModelMapper();
        City entity = mapper.map(dto, City.class);
        entity = repository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    
    public void delete(Long id){
        try {
            repository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Id not found " + id);
        }
        catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
    }

}
