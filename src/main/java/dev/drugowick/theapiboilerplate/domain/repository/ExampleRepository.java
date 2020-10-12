package dev.drugowick.theapiboilerplate.domain.repository;

import dev.drugowick.theapiboilerplate.domain.model.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExampleRepository extends JpaRepository<Example, Long> {

}
