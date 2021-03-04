package com.ehigon.tasks.index;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {

    private final IndexResourceAssembler indexResourceAssembler;

    public IndexController(IndexResourceAssembler indexResourceAssembler) {
        this.indexResourceAssembler = indexResourceAssembler;
    }
    
    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<IndexModel> index() {
        return ResponseEntity.ok(indexResourceAssembler.buildIndex());
    }
}
