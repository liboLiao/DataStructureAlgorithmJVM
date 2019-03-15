package com.me.backtrace;

/**
 * Created by llb on 2018/7/27.
 * 走迷宫
 */
public class Labyrinth {
    //深度优先的算法
    public static int startX=1;
    public static int startY=1;
    public static int endX=1;
    public static int endY=2;

    public static int[][] map=new int[][]{
            {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
            {3,0,0,0,0,0,0,0,3,3,0,0,0,3,3,0,3,3},
            {3,3,3,0,3,3,3,0,3,3,0,3,0,0,0,0,3,3},
            {3,3,3,0,3,3,3,0,0,0,0,3,0,3,3,3,3,3},
            {3,0,0,0,0,0,3,3,3,3,3,0,0,0,0,3,3,3},
            {3,0,3,3,3,0,0,0,0,0,3,0,3,3,0,0,0,3},
            {3,0,0,3,3,3,3,3,3,0,0,0,3,3,3,3,0,3},
            {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
    };

    public void test(){
        dfs(startX,startY);
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j]+" ");
            }
            System.out.println();
        }
    }

    //反回值表示是否可以继续走下去
    //x,y表示去摸索的位置是否可以走
    public boolean dfs(int x,int y){
        if(map[endX][endY]==2){//如果找到了一条到终点的通路，退出
            return true;
        }
        //用2表示自己走到的位置,用1表示走不通的时候，需要回退的位置
        if(map[x][y]==0){
            map[x][y]=2;
            //开始摸索自己周围有没有路可以走
            if(dfs(x,y-1)){//左
                return true;
            }else if(dfs(x+1,y)){//下
                return true;
            }else if(dfs(x-1,y)){//上
                return true;
            }else if(dfs(x,y+1)){//右
                return true;
            }else{//如果四个方向都不能走了
                //开始回退到之前能走的位置
                map[x][y]=1;
                return false;
            }
        }else{
            return false;
        }
    }


    public static void main(String[] agrs) {
        Labyrinth labyrinth = new Labyrinth();
        labyrinth.test();
    }
}
