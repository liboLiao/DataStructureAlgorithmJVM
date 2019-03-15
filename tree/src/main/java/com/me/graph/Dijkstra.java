package com.me.graph;

/**
 * Created by llb on 2018/4/10.
 * 单源最短路径算法，在有向带权图中从某一点出发到达其他所有节点的最短路径
 * <p>
 * 伪码表示见我笔记
 * <p>
 * 抽象S顶点集合和V顶点集合，V就是下面的vertices，S集合是算法的辅助集合，
 * 算法每次从V选一个顶点u，然后找出从源点s到所选顶点u相对于S集合的最短路径，
 * 选完之后，把该顶点u从V集合删除并加入S集合，直到V中顶点选完为止，或者
 * 直到S集合和V集合相等为止
 * <p>
 * 从源点s到所选顶点相对于S集合的最短路径：从源点s出发只经过S集合的顶点到所
 * 选顶点u之间的路径(若有路径的话)的权值最小的那个路径
 */
public class Dijkstra {
    public static final int MAX_WEIGHT = 0xFFF8;
    private int[] vertices;
    private int verticesSize;
    private int[][] matrix;
    int s;//出发的源点
    boolean[] retVertices;//表示下标代表的顶点是否被加入具有最短路径的顶点集合S
    int[] distance;//从源点s到V中各个顶点相对于S集合的最短路径,其中下标表示到达的点
    /**
     * 由<值-->角标>连起来的有向边
     * 存储的是當前从源点s到該顶点相对于S集合的最短路径中最邻近该顶点父顶点
     * 算法执行完毕后，数组存储的就是单源最短路径中的边
     */
    int[] edges;

    public Dijkstra(int verticesSize, int source) {
        this.s = source;
        this.verticesSize = verticesSize;
        this.vertices = new int[verticesSize];
        this.matrix = new int[verticesSize][verticesSize];
        for (int i = 0; i < verticesSize; i++) {
            vertices[i] = i;
        }
        this.retVertices = new boolean[verticesSize];
        this.distance = new int[verticesSize];

        //初始化边集合
        edges = new int[verticesSize];
    }

    public void dijkstra() {
        //初始化，S集合中只有s一个顶点
        int i = 0;
        for (; i < verticesSize; i++) {
            distance[i] = matrix[s][i];
            //默认自身到自身不可达
            if(s!=i&&matrix[s][i] < MAX_WEIGHT){//如果可达
                edges[i] = s;
            }else{
                edges[i] = -s;//表示s顶点不可达i顶点
            }
        }
        retVertices[s] = true;

        int min = MAX_WEIGHT;
        int u = 0;//V集合中的顶点
        while (true) {
            min = MAX_WEIGHT;

            //选择源点s到V集合中各个顶点路径最小的那个顶点
            for (i = 0; i < verticesSize; i++) {
                if (!retVertices[i] && distance[i] < min) {
                    min = distance[i];
                    u = i;
                }
            }

            if (min == MAX_WEIGHT) {
                break;
            }

            //从V集合删除并加入S集合
            retVertices[u] = true;


            //S集合添加了新顶点，V集合删除了该顶点，则从源点s到V集合中各个顶点
            // 相对于S集合的最短路径会有变化，所以要更新distance[]
            for (i = 0; i < verticesSize; i++) {
                if (!retVertices[i]) {//i顶点是否还在V中
                    if(matrix[u][i] < MAX_WEIGHT){//是否可达i顶点
                        if (distance[u] + matrix[u][i] < distance[i]) {
                            distance[i] = distance[u] + matrix[u][i];
                            edges[i] = u;//更新父顶点
                        }
                    }else{
                        edges[i] = -u;//表示u顶点不可达i顶点
                    }
                }
            }
        }

        //打印测试
        test();
    }

    private void test(){
        for (int i = 0; i < distance.length; i++) {
            System.out.println((s+1)+"顶点到"+(i+1)+"顶点的最短路径长度:"+distance[i]);
        }
    }

    public static void main(String[] agrs) {

        //例子见屈婉玲视频
        Dijkstra graph = new Dijkstra(6,0);
        int[] v0 = new int[]{0, 10, MAX_WEIGHT, MAX_WEIGHT, MAX_WEIGHT,3};
        int[] v1 = new int[]{MAX_WEIGHT, 0, 7, 5, MAX_WEIGHT,MAX_WEIGHT};
        int[] v2 = new int[]{MAX_WEIGHT, MAX_WEIGHT, 0, MAX_WEIGHT, MAX_WEIGHT,MAX_WEIGHT};
        int[] v3 = new int[]{3, MAX_WEIGHT, 4, 0, 7,MAX_WEIGHT};
        int[] v4 = new int[]{MAX_WEIGHT, MAX_WEIGHT, MAX_WEIGHT, MAX_WEIGHT, 0,MAX_WEIGHT};
        int[] v5 = new int[]{MAX_WEIGHT, 2, MAX_WEIGHT, 6, 1,0};
        graph.matrix[0] = v0;
        graph.matrix[1] = v1;
        graph.matrix[2] = v2;
        graph.matrix[3] = v3;
        graph.matrix[4] = v4;
        graph.matrix[5] = v5;
        graph.dijkstra();
    }

    /**
     * 用于该算法中的路径
     */
    public class Vertex {
        int index;
        int nextWeight;
        int parentWeight;
        Vertex parentVertex;
        Vertex nextVertex;

        public Vertex(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getNextWeight() {
            return nextWeight;
        }

        public void setNextWeight(int nextWeight) {
            this.nextWeight = nextWeight;
        }

        public int getParentWeight() {
            return parentWeight;
        }

        public void setParentWeight(int parentWeight) {
            this.parentWeight = parentWeight;
        }

        public Vertex getParentVertex() {
            return parentVertex;
        }

        public void setParentVertex(Vertex parentVertex) {
            this.parentVertex = parentVertex;
        }

        public Vertex getNextVertex() {
            return nextVertex;
        }

        public void setNextVertex(Vertex nextVertex) {
            this.nextVertex = nextVertex;
        }
    }

}
