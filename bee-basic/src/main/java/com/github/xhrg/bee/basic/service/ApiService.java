package com.github.xhrg.bee.basic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.xhrg.bee.basic.ApiMapper;

@Service
public class ApiService {

    @Autowired
    private ApiMapper apiMapper;
    
    public void getAll() {
        
        
    }
    
}
