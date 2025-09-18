// Victor Dichev 12/4 Problem 28 Hospital Staff Management System
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Zad28 {
    public static void main(String[] args) {
        getHospitalStaffInfo();
    }
    
    public static void getHospitalStaffInfo(){
        File file = new File("12.09.2025/info_2.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                if (line!=null) {
                    String[] data = line.split(",");
                    
                    if (data.length == 6) {
                        String specialization = data[2];
                        if (i >) {
                            Nurse nurse = new Nurse(data[0], data[1], Integer.parseInt(data[3]), 
                                    Double.parseDouble(data[4]), data[2], Integer.parseInt(data[5]));
                            nurse.info();
                        } else {
                            Doctor doctor = new Doctor(data[0], data[1], Integer.parseInt(data[3]), 
                                    Double.parseDouble(data[4]), data[2], Integer.parseInt(data[5]));
                            doctor.info();
                        }
                    } else if (data.length == 5) {
                        Janitor janitor = new Janitor(data[0], data[1], Integer.parseInt(data[2]), 
                                Double.parseDouble(data[3]), Integer.parseInt(data[4]));
                        janitor.info();
                    }
                    System.out.println("********************");
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading file");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Missing data fields");
        }
    }
}

class HospitalStaff{
    String firstName;
    String lastName;
    int age;
    double salary;

    public HospitalStaff(String firstName, String lastName, int age, double salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        if(age < 0) System.out.println("Age must be a positive number.");
        else this.age = age;
        if(salary < 0) System.out.println("Salary must be a positive number!");
        else this.salary = salary;
    }
    public void info(){
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Age: " + age);
        System.out.println("Salary: " + salary);
    }
}

class Doctor extends HospitalStaff{
    String specialization;
    int patientsTreated;
    public Doctor(String firstName, String lastName, int age, double salary, String specialization, int patientsTreated) {
        super(firstName, lastName, age, salary);
        this.specialization = specialization;
        this.patientsTreated = patientsTreated;
    }
    @Override
    public void info(){
        System.out.println("Doctor: " + firstName + " " + lastName + " - " + specialization);
        System.out.print("Salary: ");
        System.out.printf("%.2f", salary);
        System.out.println(" lv.");
        System.out.println("Age: " + age);
        System.out.println("Patients Treated: " + patientsTreated);    
    }
}

class Nurse extends HospitalStaff{
    String department;
    int shiftsWorked;
    public Nurse(String firstName, String lastName, int age, double salary, String department, int shiftsWorked) {
        super(firstName, lastName, age, salary);
        this.department = department;
        this.shiftsWorked = shiftsWorked;
    }
    @Override
    public void info(){
        System.out.println("Nurse: " + firstName + " " + lastName + " - " + department);
        System.out.print("Salary: ");
        System.out.printf("%.2f", salary);
        System.out.println(" lv.");
        System.out.println("Age: " + age);
        System.out.println("Shifts worked: " + shiftsWorked);
    }
}

class Janitor extends HospitalStaff{
    int areaCovered;
    public Janitor(String firstName, String lastName, int age, double salary, int areaCovered) {
        super(firstName, lastName, age, salary);
        this.areaCovered = areaCovered;
    }
    @Override
    public void info(){
        System.out.println("Janitor: " + firstName + " " + lastName);
        System.out.print("Salary: ");
        System.out.printf("%.2f", salary);
        System.out.println(" lv.");
        System.out.println("Age: " + age);
        System.out.println("Area covered: " + areaCovered + "sqm");
    }
}