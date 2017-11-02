package com.gungoren.huffman;

import java.util.ArrayList;
import java.util.List;

public class LNR {

    public Header encode(Node tree) {
        Header header = new Header();
        encode(tree, header);
        return header;
    }

    private void encode(Node root, Header header) {
        if (root == null)
            return;

        if (root.getLeft() == null && root.getRight() == null) {
            header.getNodeValues().add(root.getValue());
        }

        if (root.getLeft() != null) {
            header.addToPath("0");
        }
        encode(root.getLeft(), header);

        if (root.getRight() != null) {
            header.addToPath("1");
        }
        encode(root.getRight(), header);
    }

    public Node decode (Header header) {
        if (header.getTree().length() == 0)
            return new Node(null, "");
        Node lastNode = new Node(null, "");
        Node root = lastNode;
        for (int i = 0, j = 0; i < header.getTree().length(); i++) {
            String n = String.valueOf(header.getTree().charAt(i));
            if ("0".equals(n)) {
                lastNode = new Node(lastNode, "");
            } else {
                if (lastNode.getParent().getRight() == null) {
                    lastNode = new Node(lastNode.getParent(), header.getNodeValues().get(j++));
                } else {
                    while (lastNode.getParent().getRight() != null) {
                        lastNode = lastNode.getParent();
                    }
                    lastNode = new Node(lastNode.getParent(), header.getNodeValues().get(j++));
                }
            }
        }
        return root;
    }

    public static void main(String[] args) {
        LNR lnr = new LNR();
        Node root = new Node (null, "F");
        Node B = new Node(root, "B");
        Node A = new Node(B, "A");
        Node D = new Node(B, "D");
        Node C = new Node(D, "C");
        Node E = new Node(D, "E");

        Node G = new Node(root, "G");
        Node I = new Node(G, "I");
        Node H = new Node(I, "H");

        Header header = lnr.encode(root);
        Node node = lnr.decode(header);

        if (node.equals(root)) {
            System.out.println("Encoding and decoding tree is equal");
        } else {
            System.out.println("Encoding and decoding tree is not equal");
        }
    }
}
