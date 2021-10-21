package com.github.tjayr.controller;

import com.github.tjayr.model.Module;
import com.github.tjayr.services.CurriculumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CurriculumController  {

    @Autowired
    private CurriculumService curriculumService;

    @Cacheable(cacheNames = "modules", keyGenerator = "modulesKeyGenerator" )
    @GetMapping(path = "/modules", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Module> getModules() {
        return curriculumService.loadModules();
    }

}
