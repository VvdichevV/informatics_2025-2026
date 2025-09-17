
// Victor Dichev 12/4 Problem 2 Football Club
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;

public class FootballClub {
    public static void main(String[] args) {
        
    }
}

class ClubMember {
    String name;
    String lastName;
    int age;
    double salary;

    public ClubMember(String name, String lastName, int age, double salary) {
        this.name = name;
        this.lastName = lastName;
        if (name == null || lastName == null)
            System.out.println("The name can’t be an empty string!");
        this.age = age;
        if (age <= 17)
            System.out.println("Age must be greater than 17 years!");
        this.salary = salary;
        if (salary < 0)
            System.out.println("Salary must be a positive number");
    }

}

class Director extends ClubMember {
    String directorType;

    public Director(String name, String lastName, int age, double salary, String directorType) {
        super(name, lastName, age, salary);
        this.directorType = directorType;
    }

    void info() {
        System.out.print(directorType + "director: " + name + " " + lastName);
        System.out.print("salary: ");
        System.out.printf("%.2f", salary);
        System.out.print(" euro");
        System.out.println(" age: " + age + " years");
    }
}

class Coach extends ClubMember {
    String coachType;
    int coachLength;

    public Coach(String name, String lastName, int age, double salary, String coachType, int coachLength) {
        super(name, lastName, age, salary);
        this.coachType = coachType;
        this.coachLength = coachLength;
    }

    void info() {
        System.out.print(coachType + "coach: " + name + " " + lastName);
        System.out.print(" salary: ");
        System.out.printf("%.2f", salary);
        System.out.print(" lv");
        System.out.println("age: " + age + " years");
    }
}

class FootballPlayer extends ClubMember {
    String position;
    int contractLength;
    int matches;
    int goals;
    int assists;

    public FootballPlayer(String name, String lastName, int age, double salary, String position, int contractLength,
            int matches, int goals, int assists) {
        super(name, lastName, age, salary);
        this.position = position;
        this.contractLength = contractLength;
        this.matches = matches;
        this.goals = goals;
        this.assists = assists;

    }

    void info() {
        System.out.print(name + " " + lastName + " - " + position);
        System.out.print(" salary: ");
        System.out.printf("%.2f", salary);
        System.out.print(" lv");
        System.out.print(" age: " + age + " years");
        System.out.println(goals + " goals, " + assists + " assists in " + matches + " matches");
    }
}
