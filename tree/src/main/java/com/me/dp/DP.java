package com.me.dp;

/**
 * Created by llb on 2018/4/20.
 * 动态规划解背包问题
 * 笔记参考动态规划word文档
 * 全背包问题，各种物品数量不限，fi(x)为总重量不超过x，包中装有前i种物品的最大总价
 * 状态转移方程1<i<=n: fi(x)= max{ ci*y + fi-1(x-y*ai)} x<=a, y<= floor(x/ai)
 */
public class DP {
    double[] a = new double[]{3, 2, 5};//单重
    double[] c = new double[]{8, 5, 12};//单价
    double limit = 5;

    class Result {
        public double x;
        public double v; //总重量不超过x，包中只装有前i中物品的最大总价
        public int num;//第i中物品所装的件数
        public int i;//物品序号
        public Result pre;//第i-1种物品

        public Result( double x,double v, int num, int i,Result pre) {
            this.x = x;
            this.v = v;
            this.num = num;
            this.i = i;
            this.pre = pre;
           System.out.println(this);
        }

        @Override
        public String toString() {
            return "f"+(i+1)+"("+x+"):总重量不超过"+x+"，包中只装有前"+(i+1)+"种物品的最大总价为"+v;
        }
    }

    //递归解法
    /**
     * 求总重量不超过x，包中只装有前i中物品的最大总价
     * 时间复杂度O(2^n)
     * 递归解法解法适用于单价，重量是连续实数的情况
     * @param x
     * @param i
     * @return
     */
    Result f(double x, int i) {
        int limit = (int) Math.floor(x / a[i]);
        if(i==0){
            return new Result(x,c[i]*limit,limit,0,null);
        }
        int y = 0,num =0;
        double max = 0.0d, t = 0.0d;
        Result result = null;
        while (y <= limit) {
            Result r = f(x - y * a[i], i - 1);
            if (max < (t = c[i] * y + r.v)) {
                max = t;
                num = y;
                result = r;
            }
            y++;
        }
        return new Result(x,max,num,i,result);
    }

    public static void main(String[] agrs) {
        DP dp = new DP();
        Result r = dp.f(dp.limit,dp.a.length-1);

        System.out.println("最优装包方案可以装得"+r.v+"的价值，方案如下：");
        while (r!=null){
            System.out.print("第"+(r.i+1)+"种物品装"+r.num+"件; ");
            r = r.pre;
        }
    }

}
