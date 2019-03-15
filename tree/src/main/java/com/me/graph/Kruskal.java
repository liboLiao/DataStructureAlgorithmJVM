package com.me.graph;

/**
 * Created by llb on 2018/4/9.
 * 无向图
 */
public class Kruskal {
    public static final int MAX_WEIGHT = 0xFFF8;
    private int[] vertices;
    private int verticesSize;
    private int[][] matrix;
    private Edge[] edges;
    private int edgesSize;

    public Kruskal(int verticesSize) {
        this.verticesSize = verticesSize;
        this.vertices = new int[verticesSize];
        this.matrix = new int[verticesSize][verticesSize];
        for (int i = 0; i < verticesSize; i++) {
            vertices[i] = i;
        }
    }

    /**
     * 获取途中所有的无向边
     *
     * @return
     */
    private Edge[] getEdges() {
        int index = 0;
        //用对角线"\"斜着划出一半,不包含对角线上的边
        Edge[] edges = new Edge[(verticesSize * verticesSize-verticesSize)/2];
        for (int i = 0; i < verticesSize; i++) {
            for (int j = i; j < verticesSize; j++) {
                if (matrix[i][j] != 0 && matrix[i][j] != MAX_WEIGHT) {
                    edges[index++] = new Edge(i, j, matrix[i][j]);
                }
            }
        }
        edgesSize = index;
        return edges;
    }

    // 该算法可以应用于相似度分组，而生成最小树的分组就只有一个连通分量了
    public void kruskal() {
        sortEdges();
        //edge_temp,下表表示起始点，值表示终止点
        int[] edge_temp = new int[edgesSize];
        //最小生成树的边数为顶点数-1
        Edge[] rets = new Edge[verticesSize-1];
        int index = 0;
        for (int i = 0; i < edgesSize; i++) {
            //
            int p1 = edges[i].start;
            int p2 = edges[i].end;
            //开始检查p1,p2在"已有的最小生成树"中是否联通
            int m = getEnd(edge_temp, p1);                 // 获取p1在"已有的最小生成树"中的终点
            int n = getEnd(edge_temp, p2);                 // 获取p2在"已有的最小生成树"中的终点
            // 如果m!=n，意味着"边i"与"已经添加到最小生成树中的顶点"没有形成环路
            if (m != n) {
                edge_temp[m] = n;                       // 设置m在"已有的最小生成树"中的终点为n
                rets[index++] = edges[i];           // 保存结果
            }
        }

        //打印测试
        int sum = 0;
        for (int i = 0; i < index; i++) {
            System.out.println(rets[i]);
            sum += rets[i].weight;
        }
        System.out.println("最小生成树的权值:" + sum);
    }

    //获取顶点i在"已有的最小生成树"中的终点
    private int getEnd(int[] edge_temp, int i) {
        while (edge_temp[i] != 0)
            i = edge_temp[i];
        return i;
    }

    private void sortEdges() {
        edges = getEdges();
        for (int i = 0; i < edgesSize; i++) {
            for (int j = i + 1; j < edgesSize; j++) {
                if (edges[i].weight > edges[j].weight) {
                    Edge tmp = edges[i];
                    edges[i] = edges[j];
                    edges[j] = tmp;
                }
            }
        }
    }

    public class Edge {
        int start;
        int end;
        int weight;

        public Edge(int start, int end, int weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "(" + (start+1) + "-" + (end+1) + ")边的权值是:" + weight;
        }
    }

    public static void main(String[] agrs) {
        //例子见屈婉玲视频
        Kruskal graph = new Kruskal(6);
        int[] v0 = new int[]{0, 6, 1, 5, MAX_WEIGHT,MAX_WEIGHT};
        int[] v1 = new int[]{6, 0, 5, MAX_WEIGHT, 3,MAX_WEIGHT};
        int[] v2 = new int[]{1, 5, 0, 5, 6,4};
        int[] v3 = new int[]{5, MAX_WEIGHT, 5, 0, MAX_WEIGHT,2};
        int[] v4 = new int[]{MAX_WEIGHT, 3, 6, MAX_WEIGHT, 0,6};
        int[] v5 = new int[]{MAX_WEIGHT, MAX_WEIGHT, 4, 2, 6,0};
        graph.matrix[0] = v0;
        graph.matrix[1] = v1;
        graph.matrix[2] = v2;
        graph.matrix[3] = v3;
        graph.matrix[4] = v4;
        graph.matrix[5] = v5;
        graph.kruskal();
    }
}
