public class Java05 {
    public static void main(String[] args) {
        // 2차원 배열
        int[][] a = new int[3][3];
        a[0][0] = 1;
        a[0][1] = 2;
        a[0][2] = 3;

        a[1][0] = 1;
        a[1][1] = 2;
        a[1][2] = 3;

        a[2][0] = 1;
        a[2][1] = 2;
        a[2][2] = 3;

        for (int i = 0; i < a.length ; i++) {
            for (int j = 0; j < a[i].length; j++) {
                System.out.print(a[i][j] + "\t");
            }
            System.out.println();
        }

        // 가변길이 배열
        int[][] star = new int[5][];
        star[0] = new int[1];
        star[1] = new int[2];
        star[2] = new int[3];
        star[3] = new int[4];
        star[4] = new int[5];

        for (int i = 0; i <star.length ; i++) {
            for (int j = 0; j < star[i].length ; j++) {
                star[i][j] = '*';
                System.out.print((char) star[i][j]);
            }
            System.out.println();
        }
    }
}
