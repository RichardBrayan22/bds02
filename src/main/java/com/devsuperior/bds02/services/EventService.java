package com.devsuperior.bds02.services;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class EventService {
    
    @Autowired
    EventRepository repository;

    @Transactional
    public EventDTO update(Long id, EventDTO dto){
        dto.setId(id);
        ModelMapper mapper = new ModelMapper();
        Event event = mapper.map(dto, Event.class);
        Optional<Event> findEvent = repository.findById(event.getId());
        if(findEvent.isEmpty()){
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        event = repository.save(event);
        return dto;
    }
}
