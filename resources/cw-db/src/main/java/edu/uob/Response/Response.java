package edu.uob.Response;

import java.util.*;

public class Response {
  private ArrayList<String> consequence;
  private String status;

  public Response() {
    this.consequence = new ArrayList<>();
  }

  public ArrayList<String> getConsequence() {
    return consequence;
  }

  public void setConsequence(ArrayList<String> consequence) {
    this.consequence = consequence;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
