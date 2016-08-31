package de.hrw.bpm.exmatrikulation.repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.hrw.bpm.exmatrikulation.model.ExampleEntity;

public class ExampleRepository {

	@Inject
	EntityManager em;

	public ExampleEntity loadExampleEntityFromDb(int id) {
		ExampleEntity e = em.find(ExampleEntity.class, id);
		return e;
	}

}
