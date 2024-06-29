package co.wali;

public class JavaFun2 {

    int f(int x) {
        return x*2 - 1;
    }

    int h() {
        int a = 3;
        int b = f(a) + f(4);
        return b;
    }

    int g (int a) {
        if (a < 9) {
            return 9;
        }

        if (a < 7) {
            return 7;
        }

        if (a < 4) {
            return 4;
        }

        return 0;
    }


    int k (int a, int b) {
        if (a < b) {
            if (b > 4) {
                return 0 ;
            }
            else {
                return 1;
            }
        }
        else {
            if (a > 4) {
                return 2;
            }
            else {
                return 3;
            }
        }
    }

}
