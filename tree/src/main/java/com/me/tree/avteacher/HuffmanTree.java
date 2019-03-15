package com.me.tree.avteacher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Created by llb on 2018/3/8.
 */
public class HuffmanTree {
    private List<Node> list;

    public static void main(String[] args) {
        HuffmanTree tree = new HuffmanTree();
        List<Node> list = tree.initData();

        //
        Node HuffmanTree = tree.createHuffmanTree(list);

        //
        tree.preOrderTraversal(HuffmanTree);

        //
        tree.getLeafEnCode();
    }

    private void getLeafEnCode() {
        System.out.println("");
        for (Node n : list) {
            getEnCode(n);
        }
    }

    private List<Node> initData() {
        list = new ArrayList<Node>();
        list.add(new Node<String>("|21|", 21));
        list.add(new Node<String>("|10|", 10));
        list.add(new Node<String>("|32|", 32));
        list.add(new Node<String>("|41|", 41));
        list.add(new Node<String>("|18|", 18));
        list.add(new Node<String>("|70|", 70));
        List<Node> retList = new ArrayList<Node>();
        retList.addAll(list);
        return retList;
    }

    /**
     * 创建一个哈夫曼树，用的是顺序表存储节点；
     * 但是可以使用小顶堆来存储节点排序和查询效率更高
     *
     * @param list
     * @return
     */
    public Node<String> createHuffmanTree(List<Node> list) {
        //
        while (list.size() > 1) {
            Collections.sort(list);
            Node<String> leftNode = list.get(list.size() - 1);
            Node<String> rightNode = list.get(list.size() - 2);
            int newWeight = leftNode.weight + rightNode.weight;
            Node<String> node = new Node<String>("" + newWeight, newWeight);
            node.leftChild = leftNode;
            node.rightChild = rightNode;
            leftNode.parent = node;
            rightNode.parent = node;
            list.remove(leftNode);
            list.remove(rightNode);
            list.add(node);
        }
        return list.get(0);
    }

    /**
     * 打印叶子节点的前缀编码
     *
     * @param leafNode
     */
    public void getEnCode(Node leafNode) {
        System.out.print(leafNode.data + " ");
        Stack<String> stack = new Stack<String>();
        while (leafNode != null && leafNode.parent != null) {
            if (leafNode.parent.leftChild == leafNode) {
                stack.push("0");
            }
            if (leafNode.parent.rightChild == leafNode) {
                stack.push("1");
            }
            leafNode = leafNode.parent;
        }
        while (stack.size() != 0) {
            System.out.print(stack.pop());
        }
        System.out.println("");
    }

    /**
     * 先序
     *
     * @param node
     */

    public void preOrderTraversal(Node node) {
        if (node == null) return;
        System.out.print(node);
        preOrderTraversal(node.leftChild);
        preOrderTraversal(node.rightChild);
    }

    public static class Node<T> implements Comparable<Node<T>> {
        T data;
        int weight;
        Node<T> leftChild;
        Node<T> rightChild;
        Node<T> parent;

        public Node(T data) {
            super();
            this.data = data;
            this.leftChild = null;
            this.rightChild = null;
            this.parent = null;
        }

        public Node(T data, int weight) {
            this.data = data;
            this.weight = weight;
        }

        public Node(T data, Node<T> leftChild, Node<T> rightChild, Node<T> parent) {
            this.data = data;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
            this.parent = parent;
        }

        public Node(T data, Node<T> leftChild, Node<T> rightChild) {
            this.data = data;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Node getLeftChild() {
            return leftChild;
        }

        public void setLeftChild(Node leftChild) {
            this.leftChild = leftChild;
        }

        public Node getRightChild() {
            return rightChild;
        }

        public void setRightChild(Node rightChild) {
            this.rightChild = rightChild;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        @Override
        public String toString() {
            return data.toString() + " ";
        }

        public int compareTo(Node<T> o) {
            if (this.weight > o.weight) {
                return -1;
            } else if (this.weight < o.weight) {
                return 1;
            }
            return 0;
        }
    }
}
