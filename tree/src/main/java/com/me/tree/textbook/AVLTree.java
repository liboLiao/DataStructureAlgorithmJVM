package com.me.tree.textbook;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by llb on 2018/3/8.
 * 和avteacher package里的AVLTree 的Node节点不同，该节点使用树高来结算平衡因子
 */
public class AVLTree<T extends Comparable<T>> {
    private Node<T> root;
    private int size;
    private static final int LH = 1;
    private static final int RH = -1;
    private static final int EH = 0;

    public static void main(String[] args) {
        //		Integer[] nums = {5, 8, 2, 0, 1, -2, -9, 100};
//        Integer[] nums = {5, 8, 2, 0, 1, -2};
        int[] nums = {17, 12, 19, 10, 15, 18, 25, 8, 11, 13, 16, 22};
        AVLTree<Integer> vAvlTree = new AVLTree();
        for (int i = 0; i < nums.length; i++) {
            vAvlTree.insertData(nums[i]);
        }
        vAvlTree.midOrderTraversal(vAvlTree.root);

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
            Node y = x.rightChild;
            // step 1
            x.rightChild = y.leftChild;
            if (y.leftChild != null) {
                y.leftChild.parent = x;
            }
            // step2
            y.parent = x.parent;
            if (x.parent == null) {
                root = y;
            } else {
                if (x.parent.leftChild == x) {
                    x.parent.leftChild = y;

                } else if (x.parent.rightChild == x) {
                    x.parent.rightChild = y;
                }
            }
            //step 3
            y.leftChild = x;
            x.parent = y;

            //
            x.height = max(getHeight(x.leftChild), getHeight(x.rightChild)) + 1;
            y.height = max(getHeight(y.leftChild), getHeight(y.rightChild)) + 1;
        }
    }

    /**
     * 右旋
     */
    public void right_rotate(Node y) {
        if (y != null) {
            Node x = y.leftChild;
            y.leftChild = x.rightChild;
            if (x.rightChild != null) {
                x.rightChild.parent = y;
            }

            x.parent = y.parent;
            if (y.parent == null) {
                root = x;
            } else {
                if (y.parent.leftChild == y) {
                    y.parent.leftChild = x;
                } else if (y.parent.rightChild == y) {
                    y.parent.rightChild = x;
                }
            }

            x.rightChild = y;
            y.parent = x;

            //重新计算高度值
            y.height = max(getHeight(y.leftChild), getHeight(y.rightChild)) + 1;
            x.height = max(getHeight(x.leftChild), getHeight(x.rightChild)) + 1;
        }
    }

    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    private int getHeight(Node node) {
        if (node == null)
            return 0;
        return node.height;
    }

    /**
     * 获取平衡因子
     *
     * @return
     */
    private int getBalance(Node node) {
        return getHeight(node.leftChild) - getHeight(node.rightChild);
    }

    /**
     * 给t节点的右子树的左子树或右子树插入节点的情况
     *
     * @param t 最小不平衡子树的根节点
     */
    public void rightBalance(Node t) {
        Node tr = t.rightChild;
        switch (getBalance(tr)) {
            case LH:
                right_rotate(tr);
                left_rotate(t);
                break;
            case RH:
                left_rotate(t);
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
        switch (getBalance(tl)) {
            case LH: // t 的左子树的左边有问题，直接右旋
                right_rotate(t);
                break;
            case RH:
                left_rotate(tl);
                right_rotate(t);
                break;
            default:
                break;
        }
    }

    public boolean insertData(T data) {
        if (root == null) {
            Node node = new Node(data);
            root = node;
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

        //节点已经插入，回溯查找检查并调整平衡
        while (parent != null) {
            int balance = getBalance(parent);
            if (balance == 0) {
                break;
            } else if (Math.abs(balance) == 2) {
                //出现平衡问题,去做旋转操作，无论是单旋还是双旋都不会改变树的高度
                fixAfterInsertion(parent);
                break;
            }
            parent.height++;
            parent = parent.parent;
        }
        size++;
        return true;
    }

    private void fixAfterInsertion(Node parent) {
        int balance = getBalance(parent);
        if (balance == 2) {
            leftBalance(parent);
        } else {
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
     * 中序
     *
     * @param node
     */
    public void midOrderTraversal(Node node) {
        if (node == null) return;
        midOrderTraversal(node.leftChild);
        System.out.print(node);
        midOrderTraversal(node.rightChild);
    }

    public class Node<T extends Comparable<T>> {
        T data;
        int height;//left tree height - right tree height
        Node<T> leftChild;
        Node<T> rightChild;
        Node<T> parent;

        public Node(T data) {
            super();
            this.data = data;
            height = 1;
        }

        @Override
        public String toString() {
            return data.toString() + "[height:" + height + "] ";
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
