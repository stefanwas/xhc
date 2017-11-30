package com.stefan.xhc.repository;

import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.stefan.xhc.model.Document;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class DocumentRepository {

    private static final int MAX_RESULTS = 100;

    @Autowired
    private MongoClient mongoClient;

    private MongoCollection documents;

    public static void main(String[] args) {
        DocumentRepository repo = new DocumentRepository();
        repo.initDB();

        System.out.println("DB Initialized");

        Document document = repo.getDocument("59d912c8a21dea36dbda335b");
        System.out.println("DOC=" + document);
    }

    @PostConstruct
    public void initDB() {
//        mongoClient = new MongoClient("127.0.0.1", 27017);
        Jongo jongo = new Jongo(mongoClient.getDB("xhc"));
        documents = jongo.getCollection("documents");
    }

    public Document getDocument(String id) {
        return documents.findOne("{ _id: #}", new ObjectId(id)).as(Document.class);
    }

    public Document createDocument(Document document) {
        LocalDateTime dateTime = LocalDateTime.now();
        document.setCreateDate(dateTime);
        document.setUpdateDate(dateTime);
        documents.insert(document);
        return document;
    }

    public Document updateDocument(Document document) {
        LocalDateTime dateTime = LocalDateTime.now();
        document.setUpdateDate(dateTime);
        documents.update(new ObjectId(document.getId())).with(document);
        return document;
    }

    public void deleteDocument(String id) {
        documents.remove(new ObjectId(id));
    }

    public List<Document> getLiteDocumentsForIds(List<String> ids) {
        List<ObjectId> objectIds = ids.stream().map(ObjectId::new).collect(Collectors.toList());

        // returns only attributes: id, title
        MongoCursor<Document> allDocuments = documents
                .find("{_id: {$in: #}}", objectIds)
                .projection("{title:1}")
                .as(Document.class);

        List<Document> results = StreamSupport.stream(allDocuments.spliterator(), false).collect(Collectors.toList());
        return results;
    }

    public List<Document> getAllLiteDocuments() {

        // returns only attributes: id, title
        MongoCursor<Document> allDocuments = documents
                .find()
                .projection("{title:1}")
                .as(Document.class);

        List<Document> results = StreamSupport.stream(allDocuments.spliterator(), false).collect(Collectors.toList());
        return results;
    }
}
