package com.me.tree.avteacher;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by llb on 2018/3/5.
 */
public class SearchBinaryTree {
    private Node<Integer> root;

    //创建二叉查找树
    public Node put(int data) {
        if (root == null) {
            Node node = new Node(data);
            root = node;
            return node;
        }

        //寻找parent，把data关键字对应的Node放到parent的孩子引用里
        Node parent = null;
        Node node = root;
        //寻找parent
        while (node != null) {
            parent = node;
            if (data < (Integer) node.data) {
                node = node.leftChild;
            } else if (data > (Integer) node.data) {
                node = node.rightChild;
            } else {
                return node;
            }
        }

        //把data关键字对应的Node放到parent的孩子引用里
        node = new Node(data);
        if (data < (Integer) parent.data) {
            parent.leftChild = node;
        }
        if (data > (Integer) parent.data) {
            parent.rightChild = node;
        }
        //置放双亲
        node.parent = parent;
        return node;
    }

    //查找节点
    public Node search(int data) {
        if (root == null) {
            return null;
        }
        Node node = root;
        while (node != null) {
            if (data == (Integer) node.data) {
                return node;
            } else if (data > (Integer) node.data) {
                node = node.rightChild;
            } else if (data < (Integer) node.data) {
                node = node.leftChild;
            }
        }
        return node;
    }

    //递归查找节点
    public Node search1(int data) {
        if (root == null) {
            return null;
        }
        return coreSearch(data, root);
    }

    public Node coreSearch(int data, Node node) {
        if (node == null || data == (Integer) node.data) {
            return node;
        } else if (data > (Integer) node.data) {
            node = coreSearch(data, node.rightChild);
        } else if (data < (Integer) node.data) {
            node = coreSearch(data, node.leftChild);
        }
        return node;
    }

    //删除要复杂一些,详见http://blog.csdn.net/google19890102/article/details/54378628
    public Node delNode(int data) {
        if (root == null) {
            return null;
        }
        //找到节点
        Node node = root;
//        Node parent = null;
        for (; node != null; ) {
//            parent = node.parent;
            if (data == (Integer) node.data) {
                break;
            } else if (data > (Integer) node.data) {
                node = node.rightChild;
            } else if (data < (Integer) node.data) {
                node = node.leftChild;
            }
        }
        if (node == null) {
            return null;
        }
        //1.没有孩子
        if (node.leftChild == null && node.rightChild == null) {
            if (node.parent != null) {
                if (node.parent.leftChild == node) {
                    node.parent.leftChild = null;
                } else if (node.parent.rightChild == node) {
                    node.parent.rightChild = null;
                }
                node.parent = null;
            } else {//root
                root = null;
            }
        }
        //2.只有一个孩子
        if (node.leftChild == null && node.rightChild != null) {
            if (node.parent != null) {
                if (node.parent.leftChild == node) {
                    node.parent.leftChild = node.rightChild;
                } else if (node.parent.rightChild == node) {
                    node.parent.rightChild = node.rightChild;
                }
            } else {//root
                root = node.rightChild;
            }
            node.parent = null;
            node.rightChild = null;
        }
        if (node.leftChild != null && node.rightChild == null) {
            if (node.parent != null) {
                if (node.parent.leftChild == node) {
                    node.parent.leftChild = node.leftChild;
                } else if (node.parent.rightChild == node) {
                    node.parent.rightChild = node.leftChild;
                }
            } else {//root
                root = node.leftChild;
            }
            node.parent = null;
            node.leftChild = null;
        }
        //3.有2个孩子
        if (node.leftChild != null && node.rightChild != null) {
            //找左子树最大节点
            Node leftMaxNode = node.leftChild;
            while (leftMaxNode != null) {
                leftMaxNode = leftMaxNode.rightChild;
            }
            if (node.parent != null) {
                //先替换需要删除的节点为leftMaxNode
                if (node.parent.leftChild == node) {
                    node.parent.leftChild = leftMaxNode;
                } else if (node.parent.rightChild == node) {
                    node.parent.rightChild = leftMaxNode;
                }
            } else {//root
                root = leftMaxNode;
            }

            //再将leftMaxNode的左子树作为leftMaxNode的父节点的右子树
            if (leftMaxNode == node.leftChild) {//最大的节点是根结点（无右子树）
                //leftMaxNode == node.leftChild
            } else {//最大的节点是最右的节点
                leftMaxNode.parent.rightChild = leftMaxNode.leftChild;
                leftMaxNode.leftChild = node.leftChild;
            }

            leftMaxNode.parent = node.parent;
            leftMaxNode.rightChild = node.rightChild;

            node.parent = null;
            node.leftChild = null;
            node.rightChild = null;
        }

        return node;
    }


    public static void main(String[] args) {
        System.out.println("创建二叉平衡树中......");
        int arrys[] = {19,28,10,1,2,22,33,100,18,9};
        SearchBinaryTree tree = new SearchBinaryTree();
        for (int i = 0; i < arrys.length; i++) {
            tree.put(arrys[i]);
            System.out.print("add:" + arrys[i] + " ");
        }

        System.out.println("");
        System.out.print("中序递归遍历：");
        tree.midOrderTraversal(tree.root);

//        System.out.println("");
//        System.out.print("查找节点：");
//        System.out.println("");
//        System.out.println(tree.search(22));
//        System.out.println(tree.search(33));
//
//        System.out.println("");
//        System.out.print("递归查找节点：");
//        System.out.println("");
//        System.out.println(tree.search1(22));
//        System.out.println(tree.search1(33));

        System.out.println("");
        System.out.print("删除节点：");
        System.out.println("");
        tree.delNode(22);
//
        System.out.println("");
        System.out.print("中序递归遍历：");
        tree.midOrderTraversal(tree.root);
    }

    //==========以下递归表示=========

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

    /**
     * 后序
     *
     * @param node
     */
    public void afterOrderTraversal(Node node) {
        if (node == null) return;
        afterOrderTraversal(node.leftChild);
        afterOrderTraversal(node.rightChild);
        System.out.print(node);
    }

    /**
     * 返回当前数的深度
     * 说明:
     * 1、如果一棵树只有一个结点，它的深度为1。
     * 2、如果根结点只有左子树而没有右子树，那么树的深度是其左子树的深度加1；
     * 3、如果根结点只有右子树而没有左子树，那么树的深度应该是其右子树的深度加1；
     * 4、如果既有右子树又有左子树，那该树的深度就是其左、右子树深度的较大值再加1。
     *
     * @return
     */
    public int getTreeDepth(Node node) {
        if (node.leftChild == null && node.rightChild == null) {
            return 1;
        }
        int left = 0, right = 0;
        if (node.leftChild != null) {
            left = getTreeDepth(node.leftChild);
        }
        if (node.rightChild != null) {
            right = getTreeDepth(node.rightChild);
        }
        return left > right ? left + 1 : right + 1;
    }

    //==========以下非递归表示=========

    /**
     * 前序遍历
     * 非递归
     */
    public void preOrder1(Node<String> node) {
        Stack<Node> stack = new Stack<Node>();
        while (node != null || !stack.empty()) {
            while (node != null) {
                System.out.print(node);
                stack.push(node);
                node = node.leftChild;
            }
            if (!stack.empty()) {
                node = stack.pop();
                node = node.rightChild;
            }
        }
    }

    /**
     * 中序遍历
     * 非递归
     */
    public void midOrder1(Node<String> node) {
        Stack<Node> stack = new Stack<Node>();
        while (node != null || !stack.empty()) {
            while (node != null) {
                stack.push(node);
                node = node.leftChild;
            }
            if (!stack.empty()) {
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
    public void posOrder1(Node<String> node) {
        Stack<Node> stack1 = new Stack<Node>();
        Stack<Integer> stack2 = new Stack<Integer>();
        int i = 1;
        while (node != null || !stack1.empty()) {
            while (node != null) {
                stack1.push(node);
                stack2.push(0);
                node = node.leftChild;
            }

            while (!stack1.empty() && stack2.peek() == i) {
                stack2.pop();
                System.out.print(stack1.pop());
            }

            if (!stack1.empty()) {
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


    public class Node<T> {
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

    }
}
