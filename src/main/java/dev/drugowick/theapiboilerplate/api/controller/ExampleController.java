package dev.drugowick.theapiboilerplate.api.controller;

import dev.drugowick.theapiboilerplate.api.model.ExampleModel;
import dev.drugowick.theapiboilerplate.api.model.input.ExampleInput;
import dev.drugowick.theapiboilerplate.api.model.mapper.GenericMapper;
import dev.drugowick.theapiboilerplate.domain.model.Example;
import dev.drugowick.theapiboilerplate.domain.repository.ExampleRepository;
import dev.drugowick.theapiboilerplate.domain.service.ExampleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/examples")
@RequiredArgsConstructor
public class ExampleController {

    private final ExampleRepository exampleRepository;
    private final ExampleService exampleService;
    private final GenericMapper<Example, ExampleModel, ExampleInput> mapper;

    @GetMapping
    @ApiOperation(value = "Lists examples", authorizations = @Authorization(value = "Bearer"))
    public List<ExampleModel> list() {
        return mapper.toCollectionModel(exampleRepository.findAll(), ExampleModel.class);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Gets one specific example", authorizations = @Authorization(value = "Bearer"))
    public ExampleModel get(@PathVariable Long id) {
        Example example = exampleService.findOrElseThrow(id);
        return mapper.toModel(example, ExampleModel.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Creates an example", authorizations = @Authorization(value = "Bearer"))
    public ExampleModel create(@Valid @RequestBody ExampleInput exampleInput) {
        Example example = mapper.toDomain(exampleInput, Example.class);
        return mapper.toModel(exampleService.create(example), ExampleModel.class);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Modifies an example", authorizations = @Authorization(value = "Bearer"))
    public ExampleModel update(@PathVariable Long id, @Valid @RequestBody ExampleInput exampleInput) {
        Example example = exampleService.findOrElseThrow(id);
        mapper.copyToDomainObject(exampleInput, example);
        return mapper.toModel(exampleService.createOrUpdate(example), ExampleModel.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Deletes an example", authorizations = @Authorization(value = "Bearer"))
    public void delete(@PathVariable Long id) {
        exampleService.delete(id);
    }
}
