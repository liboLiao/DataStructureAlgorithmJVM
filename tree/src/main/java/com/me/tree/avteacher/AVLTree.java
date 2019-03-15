package com.me.tree.avteacher;

import java.util.*;

/**
 * Created by llb on 2018/3/8.
 */
public class AVLTree<T extends Comparable<T>> {
    private Node<T> root;
    private int size;
    private static final int LH = 1;
    private static final int RH = -1;
    private static final int EH = 0;

    public static void main(String[] args) {
        //		Integer[] nums = {5, 8, 2, 0, 1, -2, -9, 100};
        Integer[] nums = {5, 8, 2, 0, 1, -2};
        AVLTree<Integer> vAvlTree = new AVLTree();
        for (int i = 0; i < nums.length; i++) {
            vAvlTree.insertData(nums[i]);
        }
//		vAvlTree.midOrderDisplay(vAvlTree.root);
        vAvlTree.levelDisplay(vAvlTree.root);
        System.out.println(vAvlTree.root.data);

    }

    private List<Node<T>> initData() {
        return null;
    }

    /**
     * 左旋
     */
    public void left_rotate(Node x) {
        if (x != null) {
            Node xRight = x.rightChild;
            // step 1
            x.rightChild = xRight.leftChild;
            if (xRight.leftChild != null) {
                xRight.leftChild.parent = x;
            }
            // step2
            xRight.parent = x.parent;
            if (x.parent == null) {
                root = xRight;
            } else {
                if (x.parent.leftChild == x) {
                    x.parent.leftChild = xRight;

                } else if (x.parent.rightChild == x) {
                    x.parent.rightChild = xRight;
                }
            }
            //step 3
            xRight.leftChild = x;
            x.parent = xRight;
        }
    }

    /**
     * 右旋
     */
    public void right_rotate(Node y) {
        if (y != null) {
            Node yLeft = y.leftChild;
            y.leftChild = yLeft.rightChild;
            if (yLeft.rightChild != null) {
                yLeft.rightChild.parent = y;
            }

            yLeft.parent = y.parent;
            if (y.parent == null) {
                root = yLeft;
            } else {
                if (y.parent.leftChild == y) {
                    y.parent.leftChild = yLeft;
                } else if (y.parent.rightChild == y) {
                    y.parent.rightChild = yLeft;
                }
            }

            yLeft.rightChild = y;
            y.parent = yLeft;
        }
    }

    /**
     * 给t节点的右子树的左子树或右子树插入节点的情况
     *
     * @param t 最小不平衡子树的根节点
     */
    public void rightBalance(Node t) {
        Node tr = t.rightChild;
        switch (tr.balance){
            case LH:
                Node trl = tr.leftChild;
                right_rotate(tr);
                left_rotate(t);
                trl.balance = EH;
                switch (trl.balance){
                    case LH:
                        t.balance = EH;
                        tr.balance = RH;
                        break;
                    case RH:
                        t.balance = LH;
                        tr.balance = EH;
                        break;
                    case EH:
                        t.balance = EH;
                        tr.balance = EH;
                        break;
                }
                break;
            case RH:
                left_rotate(t);
                t.balance = EH;
                tr.balance = EH;
                break;
            case EH:
                break;
        }
    }

    /**
     * 给t节点的左子树的左子树或右子树插入节点的情况
     *
     * @param t 最小不平衡子树的根节点
     */
    public void leftBalance(Node t) {
        Node tl = t.leftChild;
        switch (tl.balance) {
            case LH: // t 的左子树的左边有问题，直接右旋
                right_rotate(t);
                tl.balance = EH;
                t.balance = EH;
                break;
            case RH:
                Node tlr = tl.rightChild;
                left_rotate(t.leftChild);
                right_rotate(t);
                tlr.balance = EH;
                switch (tlr.balance) {
                    case RH:
                        t.balance = EH;
                        tl.balance = LH;
                        break;
                    case LH:
                        t.balance = RH;
                        tl.balance =EH;
                        break;
                    case EH:
                        t.balance = EH;
                        tl.balance = EH;
                        break;
                    //统一旋转
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    public boolean insertData(T data) {
        if (root == null) {
            Node node = new Node(data);
            root = node;
            root.balance = 0;
            size++;
            return true;
        }

        //寻找parent，把data关键字对应的Node放到parent的孩子引用里
        Node parent = null;
        Node node = root;
        int tem;
        //寻找parent
        while (node != null) {
            parent = node;
            tem = data.compareTo((T) node.data);
            if (tem < 0) {
                node = node.leftChild;
            } else if (tem > 0) {
                node = node.rightChild;
            } else {
                return false;
            }
        }

        //把data关键字对应的Node放到parent的孩子引用里
        node = new Node(data);
        tem = data.compareTo((T) parent.data);
        if (tem < 0) {
            parent.leftChild = node;
        } else {
            parent.rightChild = node;
        }
        //置放双亲
        node.parent = parent;

        //节点已经插入，回溯查找检查平衡
        while (parent != null) {
            tem = data.compareTo((T) parent.data);
            if (tem < 0) {
                parent.balance++;
            } else {
                parent.balance--;
            }
            if(parent.balance == 0){
                break;
            }
            if(Math.abs(parent.balance) == 2){
                //出现平衡问题
                fixAfterInsertion(parent);
                break;
            }
            parent = parent.parent;
        }
        size++;
        return true;
    }

    private void fixAfterInsertion(Node parent) {
        if(parent.balance == 2){
            leftBalance(parent);
        }else{
            rightBalance(parent);
        }
    }

    public void levelDisplay(Node root) {
        LinkedList<NodeBF> list = new LinkedList<NodeBF>();
        NodeBF nodeBF = new NodeBF(root, 0);
        list.offer(nodeBF);
        while (!list.isEmpty()) {
            NodeBF node = list.pop();
            System.out.println(node.node.data + " level: " + node.level);
            if (node.node.leftChild != null) {
                NodeBF nodel = new NodeBF(node.node.leftChild, node.level + 1);
                list.offer(nodel);
            }
            if (node.node.rightChild != null) {
                NodeBF noder = new NodeBF(node.node.rightChild, node.level + 1);
                list.offer(noder);
            }
        }
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

    public class Node<T extends Comparable<T>> {
        T data;
        int balance;
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

        public Node(T data, int balance) {
            this.data = data;
            this.balance = balance;
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

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        @Override
        public String toString() {
            return data.toString() + " ";
        }

    }


    class NodeBF {
        int level;
        Node node;
        public NodeBF(Node node, int lev) {
            this.node = node;
            level = lev;
        }
    }
}
