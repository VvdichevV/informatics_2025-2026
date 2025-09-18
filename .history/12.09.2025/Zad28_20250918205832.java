public class Zad28 {
    public static void main(String[] args) {
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
        System.out.println("Shifts of Experience: " + yearsOfExperience);    
    }
}