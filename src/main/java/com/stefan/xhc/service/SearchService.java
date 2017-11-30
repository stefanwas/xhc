package com.stefan.xhc.service;

import com.stefan.xhc.model.Document;
import com.stefan.xhc.model.SearchQuery;
import com.stefan.xhc.model.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private static final Logger LOG = LoggerFactory.getLogger(SearchService.class);

    private RestTemplate restTemplate = new RestTemplate();

    public List<String> findDocumentIds(String searchPhrase) {

        List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(acceptableMediaTypes);

        SearchQuery searchQuery = new SearchQuery(searchPhrase);
        HttpEntity<SearchQuery> request = new HttpEntity<>(searchQuery, headers);

        // TODO move address to config file
        ResponseEntity<SearchResult> response = restTemplate.postForEntity("http://localhost:9200/xhc/document/_search", request, SearchResult.class);

        LOG.info("Search response StatusCode {}, total hits={}", response.getStatusCode(), response.getBody().getHits().getTotal());

        List<String> ids = response.getBody().getHits().getHits().stream().map(SearchResult.Hit::getId).collect(Collectors.toList());

        return ids;

//        return Arrays.asList("59d912c8a21dea36dbda335b", "59da0cce53806abe0540f19f", "59da921577cb3a3748e4f859");
    }

    public void indexDocument(Document document) {


        restTemplate.put("localhost:1234/xhc/document/" + document.getId(), document);
    }


}
