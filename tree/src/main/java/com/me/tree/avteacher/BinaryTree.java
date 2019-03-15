package com.me.tree.avteacher;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by llb on 2018/3/5.
 */
public class BinaryTree {
    private Node<String> root;

    public Node initData(){
        Node G = new Node("G", null, null);
        Node H = new Node("H", null, null);
        Node I = new Node("I", null, null);
        Node D = new Node("D", null, null);
        Node E = new Node("E", G, H);
        Node F = new Node("F", null, I);
        Node B = new Node("B", D, null);
        Node C = new Node("C", E, F);
        Node A = new Node("A", B, C);
        return A;
    }

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        tree.root = tree.initData();

        //
        System.out.print("树深："+tree.getTreeDepth(tree.root));
        System.out.println("");

        //
        System.out.print("先序递归遍历：");
        tree.preOrderTraversal(tree.root);
        System.out.println("");

        //
        System.out.print("中序递归遍历：");
        tree.midOrderTraversal(tree.root);
        System.out.println("");

        //
        System.out.print("后序递归遍历：");
        tree.afterOrderTraversal(tree.root);
        System.out.println("");

        System.out.print("先序非递归遍历：");
        tree.preOrder1(tree.root);
        System.out.println("");

        System.out.print("中序非递归遍历：");
        tree.midOrder1(tree.root);
        System.out.println("");

        System.out.print("后序非递归遍历：");
        tree.posOrder1(tree.root);
        System.out.println("");


    }

    //==========以下递归表示=========
    /**
     * 先序
     * @param node
     */
    public void preOrderTraversal(Node node){
        if(node == null)return;
        System.out.print(node);
        preOrderTraversal(node.leftChild);
        preOrderTraversal(node.rightChild);
    }

    /**
     * 中序
     * @param node
     */
    public void midOrderTraversal(Node node){
        if(node == null)return;
        midOrderTraversal(node.leftChild);
        System.out.print(node);
        midOrderTraversal(node.rightChild);
    }

    /**
     * 后序
     * @param node
     */
    public void afterOrderTraversal(Node node){
        if(node == null)return;
        afterOrderTraversal(node.leftChild);
        afterOrderTraversal(node.rightChild);
        System.out.print(node);
    }

    /**
     * 返回当前数的深度
     *  说明:
     *  1、如果一棵树只有一个结点，它的深度为1。
     *  2、如果根结点只有左子树而没有右子树，那么树的深度是其左子树的深度加1；
     *  3、如果根结点只有右子树而没有左子树，那么树的深度应该是其右子树的深度加1；
     *  4、如果既有右子树又有左子树，那该树的深度就是其左、右子树深度的较大值再加1。
     *
     * @return
     */
    public int getTreeDepth(Node node) {
        if(node.leftChild == null && node.rightChild == null){
            return 1;
        }
        int left=0,right = 0;
        if(node.leftChild!=null){
            left = getTreeDepth(node.leftChild);
        }
        if(node.rightChild!=null){
            right = getTreeDepth(node.rightChild);
        }
        return left>right?left+1:right+1;
    }

    //==========以下非递归表示=========

    /**
     * 前序遍历
     * 非递归
     */
    public void preOrder1(Node<String> node)
    {
        Stack<Node> stack = new Stack<Node>();
        while(node != null || !stack.empty())
        {
            while(node != null)
            {
                System.out.print(node);
                stack.push(node);
                node = node.leftChild;
            }
            if(!stack.empty())
            {
                node = stack.pop();
                node = node.rightChild;
            }
        }
    }

    /**
     * 中序遍历
     * 非递归
     */
    public void midOrder1(Node<String> node)
    {
        Stack<Node> stack = new Stack<Node>();
        while(node != null || !stack.empty())
        {
            while (node != null)
            {
                stack.push(node);
                node = node.leftChild;
            }
            if(!stack.empty())
            {
                node = stack.pop();
                System.out.print(node);
                node = node.rightChild;
            }
        }
    }

    /**
     * 后序遍历
     * 非递归
     */
    public void posOrder1(Node<String> node)
    {
        Stack<Node> stack1 = new Stack<Node>();
        Stack<Integer> stack2 = new Stack<Integer>();
        int i = 1;
        while(node != null || !stack1.empty())
        {
            while (node != null)
            {
                stack1.push(node);
                stack2.push(0);
                node = node.leftChild;
            }

            while(!stack1.empty() && stack2.peek() == i)
            {
                stack2.pop();
                System.out.print(stack1.pop());
            }

            if(!stack1.empty())
            {
                stack2.pop();
                stack2.push(1);
                node = stack1.peek();
                node = node.rightChild;
            }
        }
    }

    /*
     * 层序遍历
     * 非递归
     */
    public void levelOrder1(Node<String> node) {
        if (node == null) {
            return;
        }

        Queue<Node> queue = new LinkedList<Node>();
        queue.add(node);

        while (queue.size() != 0) {
            node = queue.poll();

            System.out.print(node);

            if (node.leftChild != null) {
                queue.offer(node.leftChild);
            }
            if (node.rightChild != null) {
                queue.offer(node.rightChild);
            }
        }
    }



    public class Node<T>{
        T data;
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

        @Override
        public boolean equals(Object obj) {
            T t = (T)obj;
            return data.equals(t);
        }
    }
}
