package com.example.springmongo.services;


import com.example.springmongo.commands.UnitOfMeasureCommand;
import com.example.springmongo.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.example.springmongo.repositories.UnitOfMeasureReactiveRepository;
import com.example.springmongo.repositories.UnitOfMeasureRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 01/10/21
 */
@Service
@AllArgsConstructor
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService{
    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @Override
    public Flux<UnitOfMeasureCommand> listAllUoms() {

        return unitOfMeasureReactiveRepository.findAll().map(unitOfMeasureToUnitOfMeasureCommand::convert);

//        return StreamSupport.stream(unitOfMeasureRepository.findAll()
//                        .spliterator(), false)
//                .map(unitOfMeasureToUnitOfMeasureCommand::convert)
//                .collect(Collectors.toSet());
    }
}
