package de.hrw.bpm.exmatrikulation.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.hrw.bpm.exmatrikulation.model.ExampleEntity;
import de.hrw.bpm.exmatrikulation.repository.ExampleRepository;

@Stateless
public class ExampleService {

	@Inject
	ExampleRepository exampleRepository;

	@Inject
	EntityManager em;

	public boolean checkIfExampleEntityStringValueContainsWord(int id, String word) {
		ExampleEntity exampleEntity = exampleRepository.loadExampleEntityFromDb(id);
		String stringToInvert = exampleEntity.getExampleString();
		return stringToInvert.contains(word);
	}

	public void createNewExampleEntity(String value) {
		em.persist(new ExampleEntity(value));
	}

}
