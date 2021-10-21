package com.github.tjayr.services;

import com.github.tjayr.model.Module;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CurriculumService {

    public List<Module> loadModules() {

        //Just make it randomly slow to test caching.
        try {
            Thread.sleep((long)(Math.random() * 10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList data = new ArrayList();

        for(int i=0; i < 100000; i++) {
            var module1 = new Module();
            module1.setAcademicYear("2021");
            module1.setCode("abc"+i);
            module1.setTitle("A module "+i);
            module1.setCourseReference(i);
            data.add(module1);
        }
        return data;
    }

}
