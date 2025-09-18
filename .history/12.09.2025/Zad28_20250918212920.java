// Victor Dichev 12/4 Problem 28 Hospital Staff Management System
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Zad28 {
    public static void main(String[] args) {
        getHospitalStaffInfo();
    }
    
    public static void getHospitalStaffInfo(){
        ArrayList<HospitalStaff> employees = new ArrayList<>();
        File file = new File("12.09.2025/info_2.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                i++;
                if (line!=null) {
                    String[] data = line.split(",");
                    if (data.length == 6) {
                        if (i >=5 && i < 10) {
                            Nurse nurse = new Nurse(data[0], data[1], Integer.parseInt(data[3]),
                                    Double.parseDouble(data[4]), data[2], Integer.parseInt(data[5]));
                            employees.add(nurse);
                            nurse.info();
                        } else {
                            Doctor doctor = new Doctor(data[0], data[1], Integer.parseInt(data[3]), 
                                    Double.parseDouble(data[4]), data[2], Integer.parseInt(data[5]));
                            employees.add(doctor);
                            doctor.info();
                        }
                    } else if (data.length == 5) {
                        Janitor janitor = new Janitor(data[0], data[1], Integer.parseInt(data[2]), 
                                Double.parseDouble(data[3]), Integer.parseInt(data[4]));
                        employees.add(janitor);
                        janitor.info();
                    }
                    System.out.println("********************");
                }
            }
            reader.close();
            printEmployeesSortedByAge(employees);
            printEmployeeWithHighestSalary(employees);
            printDoctorWithMostPatientsTreated(employees);
            printMostHardworkingNurse(employees);
            printMostHardworkingJanitor(employees);
        } catch (IOException e) {
            System.out.println("Error reading file");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Missing data fields");
        }
    }
    
    public static void printMostHardworkingJanitor(ArrayList<HospitalStaff> employees){
        Janitor topJanitor = new Janitor("", "", 0, 0, 0);
        for (HospitalStaff employee : employees) {
            if (employee instanceof Janitor) {
                Janitor janitor = (Janitor) employee;
                if (janitor.areaCovered > topJanitor.areaCovered) {
                    topJanitor = janitor;
                }
            }
        }
        System.out.println("\nMost hardworking Janitor:\n");
        topJanitor.info();
    }


    public static void printMostHardworkingNurse(ArrayList<HospitalStaff> employees){
        Nurse topNurse = new Nurse("", "", 0, 0, "", 0);
        for (HospitalStaff employee : employees) {
            if (employee instanceof Nurse) {
                Nurse nurse = (Nurse) employee;
                if (nurse.shiftsWorked > topNurse.shiftsWorked) {
                    topNurse = nurse;
                }
            }
        }
        System.out.println("\nMost hardworking Nurse:\n");
        topNurse.info();
    }

    public static void printDoctorWithMostPatientsTreated(ArrayList<HospitalStaff> employees){
        Doctor topDoctor = new Doctor("", "", 0, 0, "", 0);
        for (HospitalStaff employee : employees) {
            if (employee instanceof Doctor) {
                Doctor doctor = (Doctor) employee;
                if (doctor.patientsTreated > topDoctor.patientsTreated) {
                    topDoctor = doctor;
                }
            }
        }
        System.out.println("\nDoctor with the most patients treated:\n");
        topDoctor.info();
    }

    public static void printEmployeeWithHighestSalary(ArrayList<HospitalStaff> employees){
        HospitalStaff highestPaid = new HospitalStaff("", "", 0, 0);
        for (HospitalStaff employee : employees) {
            if (employee.salary > highestPaid.salary) {
                highestPaid = employee;
            }
        }
        System.out.println("\nEmployee with the highest salary:\n");
        highestPaid.info();
    }

    public static void printEmployeesSortedByAge(ArrayList<HospitalStaff> employees){
        System.out.println("\nEmployees sorted by age:\n");
        Collections.sort(employees);
        
        for (HospitalStaff employee : employees) {
            employee.info();
            System.out.println("********************");
        }
    }
}

class HospitalStaff implements Comparable<HospitalStaff>{
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

    @Override
    public int compareTo(HospitalStaff other) {
        return Integer.compare(this.age, other.age);
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
        System.out.println("Area covered: " + areaCovered + " sqm");
    }
}