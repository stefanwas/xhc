package com.stefan.xhc.web;

import com.stefan.xhc.model.Document;
import com.stefan.xhc.repository.DocumentRepository;
import com.stefan.xhc.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/document")
public class DocumentController {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentController.class);

    //TODO all void methods should return sth to confirm operation success

    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private SearchService searchService;


    @RequestMapping(value="/search", method= RequestMethod.GET)
    public List<Document> search(@RequestParam (value = "phrase", required = false) String phrase) {
        LOG.debug("GET /document/search?phrase=", phrase);

        List<Document> documents;
        if (StringUtils.isEmpty(phrase)) {
            documents = documentRepository.getAllLiteDocuments();
        } else {
            List<String> documentIds = searchService.findDocumentIds(phrase);
            documents = documentRepository.getLiteDocumentsForIds(documentIds);
        }
        return documents;
    }

//    @RequestMapping(value="/list", method= RequestMethod.GET)
//    public List<Document> getTitlesForIds(@RequestParam String ids) {
//        LOG.debug("GET /document/list?ids={}", ids);
//        // TODO change it to POST
//        List<Document> liteDocs; // only titles and ids
//        if (StringUtils.isEmpty(ids)) {
//            liteDocs = documentRepository.getAllLiteDocuments();
//        }
//        String[] idsx = ids.split(",");
//        liteDocs = documentRepository.getLiteDocumentsForIds(Arrays.asList(idsx));
//        return liteDocs;
//    }

    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public Document getDocument(@PathVariable String id) {
        LOG.debug("GET /document/{}", id);
        Document document = documentRepository.getDocument(id);
        return document;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createDocument(@RequestBody Document document) {
        LOG.debug("CREATE /document: {}", document.getTitle());
        documentRepository.createDocument(document);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateDocument(@RequestBody Document document) {
        LOG.debug("UPDATE /document: {}", document.getTitle());
        documentRepository.updateDocument(document);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void deleteDocument(@PathVariable String id) {
        LOG.debug("DELETE /document: {}", id);
        documentRepository.deleteDocument(id);
    }

}
