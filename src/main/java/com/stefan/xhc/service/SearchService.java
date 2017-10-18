package com.stefan.xhc.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SearchService {

    // TODO implement it!
    public List<String> findDocumentIds(String searchPhrase) {
        return Arrays.asList("59d912c8a21dea36dbda335b", "59da0cce53806abe0540f19f", "59da921577cb3a3748e4f859");
    }
}
