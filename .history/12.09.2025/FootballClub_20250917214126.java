
// Victor Dichev 12/4 Problem 2 Football Club
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;

public class FootballClub {
    public static void main(String[] args) {
        File file = new File("12.09.2025/info.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line != null) {
                    String[] data = line.split(",");
                    String[] names = data[0].split(" ");
                    if (data.length == 7) {

                        FootballPlayer player = new FootballPlayer(names[0], names[1],
                                Integer.parseInt(data[2]), Double.parseDouble(data[3]),
                                data[1], 0, Integer.parseInt(data[6]),
                                Integer.parseInt(data[4]), Integer.parseInt(data[5]));
                        player.info();
                    } else if (data.length == 5) {
                        Coach coach = new Coach(names[0], names[1],
                                Integer.parseInt(data[2]), Double.parseDouble(data[3]),
                                data[1], 0);
                        coach.info();
                    } else {
                        Director director = new Director(names[0], names[1],
                                Integer.parseInt(data[2]), Double.parseDouble(data[3]),
                                data[1]);
                        director.info();
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
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
