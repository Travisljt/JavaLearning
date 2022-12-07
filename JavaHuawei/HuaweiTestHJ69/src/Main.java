import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*
        矩阵相乘：
        如果A是个x行y列的矩阵，B是个y行z列的矩阵，把A和B相乘，
        其结果将是另一个x行z列的矩阵C。这个矩阵的每个元素是由下面的公式决定的
        示例1
        输入：
        2
        3
        2
        1 2 3
        3 2 1
        1 2
        2 1
        3 3
        输出：
        14 13
        10 11

        说明：
        1 2 3
        3 2 1
        乘以
        1 2
        2 1
        3 3
        等于
        14 13
        10 11
         */
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            int z = sc.nextInt();
            int[][] matrix1 = new int[x][y];
            int[][] matrix2 = new int[y][z];
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    matrix1[i][j] = sc.nextInt();
                }
            }
            for (int i = 0; i < y; i++) {
                for (int j = 0; j < z; j++) {
                    matrix2[i][j] = sc.nextInt();
                }
            }
            int[][] res = myMulti(matrix1, matrix2);
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < z; j++) {
                    System.out.print(res[i][j] + " ");
                }
                System.out.println();
            }
        }
    }

    private static int[][] myMulti(int[][] matrix1, int[][] matrix2) {
        int x = matrix1.length, y = matrix2.length, z = matrix2[0].length;
        int[][] res = new int[x][z];
        //以下完全就是矩阵乘法的公式
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < z; j++) {
                for (int k = 0; k < y; k++) {
                    res[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return res;
    }
}