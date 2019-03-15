package com.me.astart;

/**
 * Created by 48608 on 2017/12/15.
 */

public class MapInfo {
    public int[][] map;
    public int width;
    public int hight;
    public Node start;
    public Node end;

    public MapInfo(int[][] map, int width, int hight, Node start, Node end) {
        this.map = map;
        this.width = width;
        this.hight = hight;
        this.start = start;
        this.end = end;
    }
}
