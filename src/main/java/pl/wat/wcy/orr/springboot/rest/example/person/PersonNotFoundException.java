package pl.wat.wcy.orr.springboot.rest.example.person;

public class PersonNotFoundException extends RuntimeException {

	public PersonNotFoundException(String exception) {
		super(exception);
	}

}
