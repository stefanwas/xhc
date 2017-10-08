package com.stefan.xhc.web;

import com.stefan.xhc.model.Document;
import com.stefan.xhc.repository.DocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/document")
public class DocumentController {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentController.class);

    //TODO all void methods should return sth to confirm operation success

    @Autowired
    private DocumentRepository documentRepository;

    @RequestMapping(value="/ping", method = RequestMethod.GET)
    public String ping() {
        return "OK";
    }

    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public Document getDocument(@PathVariable String id) {
        LOG.debug("GET /document/{}", id);
        Document document = documentRepository.getDocument(id);
        return document;
    }

    @RequestMapping(value="/titles/{ids}", method= RequestMethod.GET)
    public List<String> getTitlesForIds(String ids) {
        LOG.debug("GET /document titles/{ids}", ids);
        // TODO change it to POST
        String[] idsx = ids.split(",");
        List<String> titles = documentRepository.getDocumentTitlesForIds(Arrays.asList(idsx));
        return titles;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createDocument(Document document) {
        LOG.debug("CREATE /document: {}", document.getTitle());
        documentRepository.createDocument(document);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateDocument(Document document) {
        LOG.debug("UPDATE /document: {}", document.getTitle());
        // TODO is it necessary to have separate id here?
        documentRepository.updateDocument(document.getId(), document);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void deleteDocument(@PathVariable String id) {
        LOG.debug("DELETE /document: {}", id);
        documentRepository.deleteDocument(id);
    }

}
