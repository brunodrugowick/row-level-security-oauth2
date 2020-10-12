package dev.drugowick.theapiboilerplate.domain.service;

import dev.drugowick.theapiboilerplate.config.EntitySecurityInfo;
import dev.drugowick.theapiboilerplate.domain.auth.AuthUtils;
import dev.drugowick.theapiboilerplate.domain.exception.EntityNotFoundException;
import dev.drugowick.theapiboilerplate.domain.exception.ExampleNotFoundException;
import dev.drugowick.theapiboilerplate.domain.model.Example;
import dev.drugowick.theapiboilerplate.domain.repository.ExampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExampleService {

    private final ExampleRepository exampleRepository;

    public List<Example> findAll() {
        var userAllowedExampleIds = AuthUtils.getEntityIds(EntitySecurityInfo.EXAMPLE);
        return exampleRepository.findAllById(userAllowedExampleIds);
    }

    @Transactional
    public Example create(Example example) {
        example.setId(null);
        return exampleRepository.save(example);
    }

    @Transactional
    public Example createOrUpdate(Example example) {
        return exampleRepository.save(example);
    }

    @Transactional
    public void delete(Long id) {
        Example example = findOrElseThrow(id);
        exampleRepository.delete(example);
    }

    /**
     * Tries to find by ID or else throws the business exception @{@link EntityNotFoundException}.
     *
     * @param id of the entity to find.
     * @return the entity from the repository.
     */
    public Example findOrElseThrow(Long id) {
        return exampleRepository.findById(id)
                .orElseThrow(() -> new ExampleNotFoundException("Could not find Example with id " + id));
    }
}
