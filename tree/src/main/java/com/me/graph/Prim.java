package com.me.graph;

import java.util.LinkedList;

/**
 * Created by llb on 2018/3/19.
 */
public class Prim {
    public static final int MAX_WEIGHT = 0xFFF8;
    private int[] edges;
    private int[][] matrix;
    private int verticeSize;
    private boolean[] isVisited;

    public Prim(int verticeSize) {
        this.verticeSize = verticeSize;
        this.edges = new int[verticeSize];
        this.matrix = new int[verticeSize][verticeSize];
        this.isVisited = new boolean[verticeSize];
        for (int i = 0; i < verticeSize; i++) {
            edges[i] = i;
            isVisited[i] = false;
        }
    }

    public int getWeight() {
        return -1;
    }

    public int[] getedges() {
        return edges;
    }

    /**
     * 遍历matrix,计算出度
     *
     * @param v
     * @return
     */
    public int getOutDegree(int v) {
        return -1;
    }

    /**
     * 遍历matrix,计算入度
     *
     * @param v
     * @return
     */
    public int getInDegree(int v) {
        return -1;
    }

    /**
     * 查找节点v 的邻接点 index的下一个邻接点
     *
     * @param v     节点
     * @param index 节点
     * @return
     */
    public int getNextNeightBor(int v, int index) {
        for (int j = index + 1; j < verticeSize; j++) {
            if (matrix[v][j] > 0 && matrix[v][j] != MAX_WEIGHT) {
                return j;
            }
        }
        return -1;
    }

    /**
     * 广度优先算法
     */
    public void bfs() {
        for (int i = 0; i < verticeSize; i++) {
            if (!isVisited[i]) {
                isVisited[i] = true;
                System.out.print(i + "--(访问顶点[" + i + "]的邻接点:)-->");
                bfs(edges[i]);
            }
        }
    }

    public void bfs(int v) {
        LinkedList<Integer> queue = new LinkedList<Integer>();
        // 访问第一个邻接节点
        int next = getNextNeightBor(v, -1);
        if (next == -1) {
            return;
        }

        if (!isVisited[next]) {
            isVisited[next] = true;
            System.out.print(next + "->");
            queue.offer(next);
        }
        // 访问其他邻接节点
        next = getNextNeightBor(v, next);
        while (next != -1) {
            if (!isVisited[next]) {
                isVisited[next] = true;
                System.out.print(next + "->");
                queue.offer(next);
            }
            next = getNextNeightBor(v, next);
        }

        while (!queue.isEmpty()) {
            int point = queue.poll();
            bfs(point);
        }
    }

    //start：从哪一个顶点作为开始 来生成 生成最小树
    //伪码表示见我的笔记
    //集合S是最小生成树的各个顶点的集合，也是图中所有的顶点
    //邻接矩阵存储
    public void prim(int start) {
        //minDistances记录S集合到V中各个对应顶点的最小权值(距离)
        //最小生成树生成之后，minDistances数组存储的是最小生成树每一条边的权值，对应边是edges中的边
        int[] minDistances = new int[verticeSize];
        //记录该顶点是否已经被选入到S集合中
        boolean[] addFlags = new boolean[verticeSize];
        //记录集合S中到该顶点具有最小权值的那个顶点，
        //最小生成树生成之后，edges数组存储的是最小生成树一条边(由角标和值连起来的)
        int[] edges = new int[verticeSize];
        //最小生成树的权值
        int sum = 0;

        //初始化
        addFlags[start] = true;
        int i = 0;
        for (; i < verticeSize; i++) {
            minDistances[i] = matrix[start][i];
            edges[i] = start;
        }

        while (true) {
            //step1 选出V集合中到集合S具有最小权值的那个顶点以及权值
            int minDistance = MAX_WEIGHT;
            int minId = 0;
            for (i = 0; i < verticeSize; i++) {
                if (!addFlags[i] && minDistances[i] < minDistance) {
                    minDistance = minDistances[i];
                    minId = i;
                }
            }

            if (minDistance == MAX_WEIGHT) {//没有最小值了，没有可选的顶点了
                break;
            }

            //step2 加入该顶点到集合S中
            addFlags[minId] = true;
            sum += minDistance;

            //step3 调整S集合到V集合中每一个顶点的最小距离、权值
            for (i = 0; i < verticeSize; i++) {
                if (!addFlags[i] && matrix[minId][i] < minDistances[i]) {
                    minDistances[i] = matrix[minId][i];
                    edges[i] = minId;
                }
            }
        }

        //测试打印
        System.out.println("最小生成树的权值:" + sum);
        sum = 0;
        for (i = 0; i < verticeSize; i++) {
            System.out.println("(" + (edges[i]+1) + "-" + (i+1) + ")边的权值是:" + minDistances[i]);
            sum += minDistances[i];
        }
        System.out.println("验证后的最小生成树的权值:" + sum);
    }

    public static void main(String[] agrs) {
      /*  Prim prim = new Prim(5);
        int[] v0 = new int[]{0, 1, 1, MAX_WEIGHT, MAX_WEIGHT};
        int[] v1 = new int[]{MAX_WEIGHT, 0, MAX_WEIGHT, 1, MAX_WEIGHT};
        int[] v2 = new int[]{MAX_WEIGHT, MAX_WEIGHT, 0, MAX_WEIGHT, MAX_WEIGHT};
        int[] v3 = new int[]{1, MAX_WEIGHT, MAX_WEIGHT, 0, MAX_WEIGHT};
        int[] v4 = new int[]{MAX_WEIGHT, MAX_WEIGHT, 1, MAX_WEIGHT, 0};
        prim.matrix[0] = v0;
        prim.matrix[1] = v1;
        prim.matrix[2] = v2;
        prim.matrix[3] = v3;
        prim.matrix[4] = v4;
        prim.bfs();*/

        //例子见屈婉玲视频
        Prim prim = new Prim(6);
        int[] v0 = new int[]{0, 6, 1, 5, MAX_WEIGHT,MAX_WEIGHT};
        int[] v1 = new int[]{6, 0, 5, MAX_WEIGHT, 3,MAX_WEIGHT};
        int[] v2 = new int[]{1, 5, 0, 5, 6,4};
        int[] v3 = new int[]{5, MAX_WEIGHT, 5, 0, MAX_WEIGHT,2};
        int[] v4 = new int[]{MAX_WEIGHT, 3, 6, MAX_WEIGHT, 0,6};
        int[] v5 = new int[]{MAX_WEIGHT, MAX_WEIGHT, 4, 2, 6,0};
        prim.matrix[0] = v0;
        prim.matrix[1] = v1;
        prim.matrix[2] = v2;
        prim.matrix[3] = v3;
        prim.matrix[4] = v4;
        prim.matrix[5] = v5;
        prim.prim(3);
    }

}
