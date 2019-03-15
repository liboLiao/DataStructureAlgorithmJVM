package com.me.astart;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by 48608 on 2017/12/15.
 */

public class AStar {
    //障碍物
    public final static int BAR = 1;
    //径路
    public final static int PATH = 2;
    //横竖上的移动代价
    public final static int DIRECT_VALUE = 10;
    //斜移动的代价
    public final static int OBLIQUE_VALUE = 14;
    //为了不再自己再去排序
    Queue<Node> openList = new PriorityQueue<Node>();
    List<Node> closeList = new ArrayList<Node>();

    //定义一些辅助性的方法

    /**
     * 计算H值：曼哈顿方法
     */
    private int calcH(Coord end, Coord coord) {
        return Math.abs(end.x - coord.x) + Math.abs(end.y - coord.y);
    }

    /**
     * 判断结点是否是最终点
     */
    private boolean isEndNode(Coord end, Coord coord) {
        return coord != null && end.equals(coord);
    }

    /**
     * 开始算法的逻辑
     */
    public void start(MapInfo mapInfo) {
        //先判断地图是否存在
        if (mapInfo == null) return;
        //清空上一次操作的结果
        openList.clear();
        closeList.clear();
        //开始搜索
        //1.把开始节点加入到open表
        openList.add(mapInfo.start);
        //2.目标判定
        moveNodes(mapInfo);
    }

    /**
     * 用来移动当前结点
     */
    private void moveNodes(MapInfo mapInfo) {
        while (!openList.isEmpty()) {
            if (isCoordInClose(mapInfo.end.coord)) {//如果终点进入了close表示结束
                //表示已经完成，可以画出结果
                drawPath(mapInfo.map,mapInfo.end);
                //画结果
                break;
            }
            //把open中的第一个节点取出来放到close中
            Node current = openList.poll();
            closeList.add(current);
            //3.开始从current开始的地方找邻近的能走的节点
            addNeighborNodeInOpen(mapInfo, current);
        }
    }

    /**
     * 添加周围所有能加入的点
     */
    private void addNeighborNodeInOpen(MapInfo mapInfo, Node current) {
        int x = current.coord.x;
        int y = current.coord.y;
        //进行八次操作
        //定义一个方法来进行一个节点的判断
        //左
        addNeighborNodeInOpen(mapInfo, current, x - 1, y, DIRECT_VALUE);
        //上
        addNeighborNodeInOpen(mapInfo, current, x, y - 1, DIRECT_VALUE);
        //右
        addNeighborNodeInOpen(mapInfo, current, x + 1, y, DIRECT_VALUE);
        //下
        addNeighborNodeInOpen(mapInfo, current, x, y + 1, DIRECT_VALUE);
        //左上
        addNeighborNodeInOpen(mapInfo, current, x - 1, y - 1, OBLIQUE_VALUE);
        //右上
        addNeighborNodeInOpen(mapInfo, current, x + 1, y - 1, OBLIQUE_VALUE);
        //左下
        addNeighborNodeInOpen(mapInfo, current, x - 1, y + 1, OBLIQUE_VALUE);
        //右下
        addNeighborNodeInOpen(mapInfo, current, x + 1, y + 1, OBLIQUE_VALUE);

    }

    private void addNeighborNodeInOpen(MapInfo mapInfo, Node current, int x, int y, int value) {
        if (canAddNodeToOpen(mapInfo, x, y)) {//判断当前节点是否可以添加（不在close,也不是墙）
            Node end = mapInfo.end;
            Coord coord = new Coord(x, y);
            int g = current.g + value;//计算当前点到开始点的g值
            Node child = findNodeInOpen(coord);//查找当前点是否在open中
            if (child == null) {//不在open中
                //这个if中表示都是从来没搜过的路
                int h = calcH(end.coord, coord);//计算H值
                if (isEndNode(end.coord, coord)) {
                    child = end;
                    child.parent = current;
                    child.g = g;
                    child.h = h;
                } else {
                    child = new Node(coord, current, g, h);
                }
                openList.add(child);
            } else if(child.g>g){//如果在open中(只需要判断两个节点的G值 ,用小的换了
                child.g=g;
                child.parent=current;
                openList.add(child);
            }
        }
    }


    private Node findNodeInOpen(Coord coord) {
        if (coord == null || openList.isEmpty()) return null;
        for (Node node : openList) {
            if (node.coord.equals(coord)) {
                return node;
            }
        }
        return null;
    }

    private boolean canAddNodeToOpen(MapInfo mapInfo, int x, int y) {
        //是否在地图中
        if (x < 0 || x >= mapInfo.width || y < 0 || y >= mapInfo.hight) {
            return false;
        }
        //判断是否是不可通过的位置
        if (mapInfo.map[y][x] == BAR) {
            return false;
        }
        //判断是否在close表中
        if (isCoordInClose(x, y)) {
            return false;
        }
        return true;
    }

    /**
     * 判断坐标是否在close列队中
     */
    private boolean isCoordInClose(Coord coord) {
        return coord != null && isCoordInClose(coord.x, coord.y);
    }

    private boolean isCoordInClose(int x, int y) {
        if (closeList.isEmpty()) return false;
        for (Node node : closeList) {
            if (node.coord.x == x && node.coord.y == y) {
                return true;
            }
        }
        return false;
    }

    private void drawPath(int[][] map,Node end){
        if(end==null || map==null) return ;
        System.out.println("最短长度为:"+end.g);
        while(end!=null){
            Coord c=end.coord;
            map[c.y][c.x]=PATH;
            end= end.parent;
        }
    }
}







