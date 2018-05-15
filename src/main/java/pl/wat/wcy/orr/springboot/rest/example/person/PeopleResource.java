package pl.wat.wcy.orr.springboot.rest.example.person;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/people")
public class PeopleResource {

	@Autowired
	private PeopleRepository peopleRepository;

	@GetMapping
	public List<Person> getAllPeoples() {
		return peopleRepository.findAll();
	}

	@GetMapping("/{id}")
	public Person getPerson(@PathVariable long id) {
		Optional<Person> people = peopleRepository.findById(id);

		if (!people.isPresent())
			throw new PersonNotFoundException("id-" + id);
		return people.get();
	}

	@DeleteMapping("/{id}")
	public void deletePerson(@PathVariable long id) {
		peopleRepository.deleteById(id);
	}

	@PostMapping
	public ResponseEntity<Object> createPerson(@RequestBody Person person) {
		Person savedStudent = peopleRepository.save(person);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedStudent.getId()).toUri();

		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateStudent(@RequestBody Person person, @PathVariable long id) {
		Optional<Person> studentOptional = peopleRepository.findById(id);

		if (!studentOptional.isPresent())
			return ResponseEntity.notFound().build();

		person.setId(id);
		
		peopleRepository.save(person);

		return ResponseEntity.ok().build();
	}
}