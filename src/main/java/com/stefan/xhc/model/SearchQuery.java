package com.stefan.xhc.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchQuery {

    @JsonProperty("query")
    private Query query;

    public SearchQuery(String query) {
        this.query = new Query(query);
    }

    public Query getQuery() {
        return query;
    }
}

class Query {
    @JsonProperty("query_string")
    private QueryString queryString;

    public Query(String query) {
        this.queryString = new QueryString(query);
    }

    public QueryString getQueryString() {
        return queryString;
    }
}


class QueryString {
    @JsonProperty("query")
    private String query;

    public QueryString(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}

