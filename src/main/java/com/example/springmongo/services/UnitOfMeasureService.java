package com.example.springmongo.services;

import com.example.springmongo.commands.UnitOfMeasureCommand;
import reactor.core.publisher.Flux;

import java.util.Set;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 01/10/21
 */
public interface UnitOfMeasureService {

	Flux<UnitOfMeasureCommand> listAllUoms();
}
