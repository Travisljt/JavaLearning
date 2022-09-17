package vo;

import java.util.ArrayList;
import java.util.List;

public class Response {
    private String status;

    private List<String> results;

    public Response() {
        this.results = new ArrayList<>();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }

    
}
