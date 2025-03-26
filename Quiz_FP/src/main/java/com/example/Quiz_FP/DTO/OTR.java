package com.example.Quiz_FP.DTO;

import java.util.List;

public class OTR {
    private int response_code;
    private List<QuestionReq> results;

    public int getResponse_code() { return response_code; }
    public void setResponse_code(int response_code) { this.response_code = response_code; }

    public List<QuestionReq> getResults() { return results; }
    public void setResults(List<QuestionReq> results) { this.results = results; }
}
