package com.stefan.xhc.web;

import com.stefan.xhc.model.Document;
import com.stefan.xhc.repository.DocumentRepository;
import com.stefan.xhc.service.SearchService;
import com.stefan.xhc.utils.DocumentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/document")
public class DocumentController {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentController.class);

    //TODO all void methods should return sth to confirm operation success

    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private SearchService searchService;


    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<Document> search(@RequestParam(value = "phrase", required = false) String phrase) {
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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Document getDocument(@PathVariable String id) {
        LOG.debug("GET /document/{}", id);
        Document document = documentRepository.getDocument(id);
        return document;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Document saveDocument(@RequestBody Document document) {
        Document savedDocument;
        if (StringUtils.isEmpty(document.getId())) {
            LOG.debug("CREATE /document: {}", document.getTitle());
            savedDocument = documentRepository.createDocument(document);
        } else {
            LOG.debug("UPDATE /document: {}", document.getTitle());
            savedDocument = documentRepository.updateDocument(document);
        }
        return savedDocument;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteDocument(@PathVariable String id) {
        LOG.debug("DELETE /document: {}", id);
        documentRepository.deleteDocument(id);
    }

    //TODO check it working
    @RequestMapping(value = "/exportall", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> exportAllDocuments() throws Exception {
        List<Document> liteDocuments = documentRepository.getAllLiteDocuments();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ZipOutputStream zipOutput = new ZipOutputStream(output);

        for (Document lite : liteDocuments) {
            Document doc = documentRepository.getDocument(lite.getId());
            writeDocumentToZipStream(zipOutput, doc);
        }

        zipOutput.flush();
        zipOutput.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "all-documents.");


        return new ResponseEntity<>(output.toByteArray(), headers, HttpStatus.OK);
    }

    private void writeDocumentToZipStream(ZipOutputStream zipOutput, Document doc) throws IOException {
        String json = DocumentUtil.serialize(doc);
        zipOutput.putNextEntry(new ZipEntry(doc.getTitle()));
        zipOutput.write(json.getBytes());
        zipOutput.closeEntry();
    }


    public void importDocuments(byte[] data) {

    }

}
