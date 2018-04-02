package io.github.originalalex.filmdex.server.dto;

import java.util.ArrayList;
import java.util.List;

public class RequestResult {

    private List<String> results = new ArrayList<>();

    public void addResult(String result) {
        results.add(result);
    }

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }

}
