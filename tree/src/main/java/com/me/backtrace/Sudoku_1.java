package com.me.backtrace;

/**
 * Created by llb on 2018/7/18.
 * 具体见Visual studio运行一个名叫sudoku的解决方案的项目
 */
public class Sudoku_1 {
    int rule[][][] = new int[3][9][9];
    int sudoku[][] = {
            {1, 2, 3, 4, 5, 6, 7, 8, 9},
            {4, 0, 0, 0, 0, 0, 0, 0, 0},
            {7, 0, 0, 0, 0, 0, 0, 0, 0},
            {2, 0, 0, 0, 0, 0, 0, 0, 0},
            {3, 6, 0, 8, 9, 7, 0, 1, 4},
            {8, 9, 0, 2, 1, 4, 0, 3, 5},
            {6, 4, 0, 9, 3, 0, 0, 0, 0},
            {9, 7, 0, 5, 4, 1, 0, 6, 8},
            {5, 3, 0, 6, 7, 8, 0, 4, 2}};
    ;
    int flag[][] = new int[9][9];
    int count_way;

   /* public static int[][] result = {
            {1, 2, 3, 4, 5, 6, 7, 8, 9},
            {4, 0, 0, 0, 0, 0, 0, 0, 0},
            {7, 0, 0, 0, 0, 0, 0, 0, 0},
            {2, 0, 0, 0, 0, 0, 0, 0, 0},
            {3, 6, 0, 8, 9, 7, 0, 1, 4},
            {8, 9, 0, 2, 1, 4, 0, 3, 5},
            {6, 4, 0, 9, 3, 0, 0, 0, 0},
            {9, 7, 0, 5, 4, 1, 0, 6, 8},
            {5, 3, 0, 6, 7, 8, 0, 4, 2}};*/

    int find_small_index(int y, int x) {
        return (3 * (y / 3) + (x / 3));
    }

    int find_next_value(int y, int x, int start) {
        int index = find_small_index(y, x);
        for (int i = start; i < 9; i++) {
            if (rule[0][y][i] == 1) continue;
            if (rule[1][x][i] == 1) continue;
            if (rule[2][index][i] == 1) continue;
            return i;
        }
        return -1;
    }

    void show() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(sudoku[i][j] + 1);
//                cout<<sudoku[i][j] + 1;
            }
//            cout<<endl;
            System.out.println();
        }
    }

    void change_rule(int style, int x, int y) {
        int index = find_small_index(y, x);
        rule[0][y][sudoku[y][x]] = style;
        rule[1][x][sudoku[y][x]] = style;
        rule[2][index][sudoku[y][x]] = style;
    }

    void init_sudoku() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
//                sudoku[i][j] =result[i][j] - '0';
                if (juge(juge(juge(sudoku[i][j])))) flag[i][j] = 1;
                else flag[i][j] = 0;
                if (juge(flag[i][j])) change_rule(1, j, i);
            }
//            getchar();
        }
    }

    int juge(boolean flag) {
        if (flag) {
            return 0;
        }
        return -1;
    }

    boolean juge(int flag) {
        if (flag == 0) {
            return true;
        }
        return false;
    }

    void solve(int y, int x) {
        if (x == 9) {
            y++;
            x = 0;
        }
        while (juge(flag[y][x]) && y <= 8) {
            x++;
            if (x == 9) {
                y++;
                x = 0;
            }
        }
        if (y > 8) {
            show();
            count_way++;
            return;
        }
        while ((sudoku[y][x] = find_next_value(y, x, sudoku[y][x] + 1)) != -1) {
            change_rule(1, x, y);
            solve(y, x + 1);      //若只想求出一个可行解就退出，可加上这代码：if(count_way)break;
            change_rule(0, x, y);
        }
    }

    int main() {
        init_sudoku();
        solve(0, 0);              //从(0,0)位置开始填数
        System.out.println("count_way:" + count_way);
        return 0;
    }

    public static void main(String[] agrs) {
        new Sudoku_1().main();
    }
}
