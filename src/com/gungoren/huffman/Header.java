package com.gungoren.huffman;

import java.util.ArrayList;
import java.util.List;

public class Header {

    private StringBuilder path = new StringBuilder();
    private List<String> nodeValues = new ArrayList<>();

    public Header() {
    }

    public String getPath() {
        return path.toString();
    }

    public void setPath(String path) {
        this.path = new StringBuilder(path);
    }

    public void addToPath(String path){
        this.path.append(path);
    }

    public List<String> getNodeValues() {
        return nodeValues;
    }

    public void setNodeValues(List<String> nodeValues) {
        this.nodeValues = nodeValues;
    }

    public void addNode(String nodeValue) {
        getNodeValues().add(nodeValue);
    }
}
