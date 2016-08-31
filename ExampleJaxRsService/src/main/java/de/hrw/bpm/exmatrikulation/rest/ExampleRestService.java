package de.hrw.bpm.exmatrikulation.rest;

import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Validator;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.hrw.bpm.exmatrikulation.model.ExampleEntity;
import de.hrw.bpm.exmatrikulation.repository.ExampleRepository;
import de.hrw.bpm.exmatrikulation.service.ExampleService;

@Path("/")
@RequestScoped
public class ExampleRestService {

	@Inject
	private Logger log;

	@Inject
	private Validator validator;

	@Inject
	ExampleService exampleService;

	@Inject
	ExampleRepository exampleRepository;

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public ExampleEntity lookupExampleEntityById(@PathParam("id") int id) {
		ExampleEntity exampleEntity = exampleRepository.loadExampleEntityFromDb(id);
		if (exampleEntity == null) {
			log.info("Entity with ID " + id + " not found!");
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		log.info("Loaded Entity with String: " + exampleEntity.getExampleString());
		return exampleEntity;
	}
}
