

// Victor Dichev 12/4 Problem 2 Football Club
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class FootballClub {
    public static void main(String[] args) {
        getTeam();
    }


    public static void getTeam() {
        File file = new File("12.09.2025/info.txt");
        ArrayList<FootballPlayer> players = new ArrayList<>();
        ArrayList<Coach> coaches = new ArrayList<>();
        ArrayList<Director> directors = new ArrayList<>();


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


                        players.add(player);
                        player.info();
                    } else if (data.length == 5) {
                        Coach coach = new Coach(names[0], names[1],
                                Integer.parseInt(data[2]), Double.parseDouble(data[3]),
                                data[1], 0);
                        coaches.add(coach);
                        coach.info();
                    } else {
                        Director director = new Director(names[0], names[1],
                                Integer.parseInt(data[2]), Double.parseDouble(data[3]),
                                data[1]);


                        directors.add(director);
                        director.info();
                    }
                }
                System.out.println("********************");
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading file");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Missing data fields");
        }
        getHighestSalary(players, coaches, directors);
        getTopScorer(players);
    }


    public static void getTopScorer(ArrayList<FootballPlayer> players) {
        int maxGoals = 0;
        String name1 = "", name2 = "";
        for (FootballPlayer player : players) {
            if (maxGoals < player.goals) {
                maxGoals = player.goals;
                name1 = player.name;
                name2 = player.lastName;
            }
        }
        System.out.println("The team's top scorer is " + name1 + " " + name2 + " with " + maxGoals + " goals.");
    }


    public static void getHighestSalary(ArrayList<FootballPlayer> players, ArrayList<Coach> coaches,
            ArrayList<Director> directors) {
        double maxSalary = 0;
        String name1 = "", name2 = "";
        for (FootballPlayer player : players) {
            if (player.salary > maxSalary) {
                maxSalary = player.salary;
                name1 = player.name;
                name2 = player.lastName;
            }
        }
        for (Coach coach : coaches) {
            if (coach.salary > maxSalary) {
                maxSalary = coach.salary;
                name1 = coach.name;
                name2 = coach.lastName;
            }
        }
        for (Director director : directors) {
            if (director.salary > maxSalary) {
                maxSalary = director.salary;
                name1 = director.name;
                name2 = director.lastName;


            }
        }
        System.out.println("The person with the highest salary in the club is " + name1 + " " + name2 + " with "
                + maxSalary + " lv salary.");
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
            System.out.println("The name cant be an empty string!");
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
        System.out.print(directorType + " director: " + name + " " + lastName);
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
        System.out.print(coachType + " coach: " + name + " " + lastName);
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
        System.out.print(" age: " + age + " years ");
        System.out.println(goals + " goals, " + assists + " assists in " + matches + " matches");
    }


}