package com.gungoren.huffman;

public class LNR {

    public Header encode(Node tree) {
        Header header = new Header();
        encode(tree, header);
        return header;
    }

    private void encode(Node node, Header header) {
        if (node == null)
            return;

        if (node.isLeaf()) {
            header.addNode(node.getValue());
        }

        if (node.getLeft() != null) {
            header.addToPath("0");
        }
        encode(node.getLeft(), header);

        if (node.getRight() != null) {
            header.addToPath("1");
        }
        encode(node.getRight(), header);
    }

    public Node decode (Header header) {
        if (header.getPath().length() == 0)
            return new Node(null, "");
        Node lastNode = new Node(null, "");
        Node root = lastNode;
        for (int i = 0, j = 0; i < header.getPath().length(); i++) {
            String n = String.valueOf(header.getPath().charAt(i));
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
        Node root = new Node (null, "-29");
        Node B = new Node(root, "-15");
        Node A = new Node(B, "e");
        Node D = new Node(B, "-7");
        Node C = new Node(D, "n");
        Node E = new Node(D, "-2");
        Node F = new Node(E, "m");
        Node I = new Node(E, "l");

        Node G = new Node(root, "a");

        Header header = lnr.encode(root);
        Node node = lnr.decode(header);

        if (node.equals(root)) {
            System.out.println("Encoding and decoding tree is equal");
        } else {
            System.out.println("Encoding and decoding tree is not equal");
        }
    }
}
