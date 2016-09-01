package de.hrw.bpm.exmatrikulation.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class ExampleEntity {

	@Id
	private int id;

	private String exampleString;

	public ExampleEntity() {
		super();
	}

	public ExampleEntity(String exampleString) {
		super();
		this.exampleString = exampleString;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getExampleString() {
		return exampleString;
	}

	public void setExampleString(String exampleString) {
		this.exampleString = exampleString;
	}

}
