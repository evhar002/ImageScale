package com.gungoren.huffman;

public class Node {

    private String value;
    private Node parent;
    private Node left;
    private Node right;

    public Node(Node parent, String value) {
        this.parent = parent;
        this.value = value;

        if (parent != null) {
            if (parent.getLeft() == null) {
                parent.setLeft(this);
            } else {
                parent.setRight(this);
            }
        }
    }

    public String getValue() {
        return value;
    }

    public Node getParent() {
        return parent;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public boolean isLeaf() {
        return getLeft() == null && getRight() == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return true;

        if (getClass() != o.getClass())
            return false;

        Node node = (Node) o;

        if ((parent != null && node.parent == null) ||
                (parent == null && node.parent != null))
            return false;
        if (left != null ? !left.equals(node.left) : node.left != null)
            return false;
        return right != null ? right.equals(node.right) : node.right == null;
    }
}
