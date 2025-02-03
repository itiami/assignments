package co.wali;

public class RecursiveFunction {
    public void run() {
        int number = 10;
        int result = factorial(number);  // Calling the recursive function
        System.out.println("Factorial of " + number + " is: " + result);
    }

    /*
    factorial math function is
    Factorial of n = n! = n × (n – 1) × (n – 2) × … × 1
        Examples:
        0! = 1
        1! = 1
        3! = 3 x 2 x 1 = 6
        4! = 4 x 3 x 2 x 1 =  24
     */
    // Recursive function to calculate factorial
    private  int factorial(int n) {
        if (n == 0) {
            return 1;  // Base case: factorial of 0 is 1
        } else {
            int res = n * factorial(n - 1);
            System.out.println("Results: " + res);
            return res;  // Recursive call
        }
    }


}
