//  Victor Dichev 12/4 Problem 1 Students Work
import java.util.ArrayList;
import java.util.Scanner;
public class StudentsWork {
    int n;
    ArrayList<Integer> results = new ArrayList<Integer>();
    //int[] validResults = new int[n];
    public void ReadPoints(){
        System.out.print("How many results are there? ");
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        for(int i = 0; i < n; i++){
            int num = sc.nextInt();
            if(num > 0) results.add(num);
        }
        results.sort(null);
        sc.close();
        System.out.println("valid works - " + results.size());


    }
    public int МinDpoints(){
        int min = 100;
        int prev = results.get(0);
        for(int i = 1; i < results.size(); i++){
            int now = results.get(i) - prev;
            prev = results.get(i);
            if(now < min) min = now;
        }
        return min;
    }
    public int Laureates(){
        int count = 1;
        int num = 2;
        for(int i = results.size() - 2; num!=0 && i>=0; i--){
            count++;
            if(results.get(i) != results.get(i + 1)){
                num--;
            }
        }
        return count;
    }
    public double getAverage(){
        double sum = 0;
        for(int i = 0; i < results.size(); i++){
            sum += results.get(i);
        }
        return sum / results.size();
    }
    public static void main(String[] args) {
        StudentsWork first = new StudentsWork();
        first.ReadPoints();
        System.out.println("minimal difference - " + first.МinDpoints());
        System.out.println("laureates - " + first.Laureates());
        System.out.println("average result - " + first.getAverage());
    }
}
