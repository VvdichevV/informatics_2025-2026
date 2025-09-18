// Victor Dichev 12/4, zadacha 1
import java.util.Arrays;
import java.util.Scanner;
public class App {
    int max;
    int nummy;
    public App() {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] arr = new int[N];
        max = Integer.MIN_VALUE;
        for (int i = 0; i < N; i++) {
            arr[i] = sc.nextInt();
            max = Math.max(max, arr[i]);
        }
        max++;
        sc.close();
    }
    int getMax() {
        return max;
    }
    int maxDigit() {
        String number = String.valueOf(max);
        char[] digits = number.toCharArray();


        Arrays.sort(digits);
        nummy = 0;
        for(int i = digits.length - 1; i >= 0; i--) {
            nummy = nummy * 10 + (digits[i] - '0');
        }
        return nummy;
    }
    int nextMaxDigit(){
        return nummy + 1;
    }
        public static void main(String[] args) throws Exception {
        App first = new App();
       
       
        System.out.println("Max Element = " + first.getMax());
        System.out.println("Max Digit = " + first.maxDigit());


       
        System.out.print("next Max Digit  = " + first.nextMaxDigit());
       
     }
}



