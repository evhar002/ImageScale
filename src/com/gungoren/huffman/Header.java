package com.gungoren.huffman;

import java.util.ArrayList;
import java.util.List;

public class Header {

    private StringBuilder tree = new StringBuilder();
    private List<String> nodeValues = new ArrayList<>();

    public Header() {
    }

    public Header(StringBuilder tree, List<String> nodeValues) {
        this.tree = tree;
        this.nodeValues = nodeValues;
    }


    public String getTree() {
        return tree.toString();
    }

    public void setTree(String tree) {
        this.tree = new StringBuilder(tree);
    }

    public void addToPath(String path){
        tree.append(path);
    }

    public List<String> getNodeValues() {
        return nodeValues;
    }

    public void setNodeValues(List<String> nodeValues) {
        this.nodeValues = nodeValues;
    }
}
