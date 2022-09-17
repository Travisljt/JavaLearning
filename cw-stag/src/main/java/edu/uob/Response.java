package edu.uob;

import java.util.ArrayList;

//This is the sever response
public class Response {
    private ArrayList<String> result = new ArrayList<>();
    public Response(){}

    public void setResult(ArrayList<String> result) {
        this.result = result;
    }
    public void addResult(String str){
        this.result.add(str);
    }

    public ArrayList<String> getResult() {
    return result;
    }
}
