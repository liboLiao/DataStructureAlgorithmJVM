package com.me.astart;

/**
 * Created by 48608 on 2017/12/15.
 */

public class Node implements Comparable<Node>{
    public Coord coord;
    public Node parent;
    public int g;
    public int h;

    public Node(int x,int y){
        this.coord=new Coord(x,y);
    }

    public Node(Coord coord, Node parent, int g, int h) {
        this.coord = coord;
        this.parent = parent;
        this.g = g;
        this.h = h;
    }
    //需要一个方法用来比较当前节点和另一个节点到终点的距离，谁更短
    @Override
    public int compareTo( Node  o) {
       if(o==null) return -1;
        if(g+h>o.g+o.h){
            return 1;
        }else if(g+h<o.g+o.h){
            return -1;
        }

        return 0;
    }

}










