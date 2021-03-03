package com.ehigon.tasks.tasks;

import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/repeat_options")
@ExposesResourceFor(RepeatOptionsModel.class)
public class RepeatOptionsController {

    @GetMapping()
    public ResponseEntity<RepeatOptionsModel> getOptions() {
        RepeatOptionsModel repeatOptionsModel = new RepeatOptionsModel();
        repeatOptionsModel.add(
                linkTo(methodOn(RepeatOptionsController.class).getOptions()).withSelfRel()
        );
        return ResponseEntity.ok(repeatOptionsModel);
    }
}
