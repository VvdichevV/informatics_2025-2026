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
    
    public Doctor(String firstName, String lastName, int age, double salary, String specialization) {
        super(firstName, lastName, age, salary);
        this.specialization = specialization;
    }
    @Override
    public void info(){
        super.info();
        System.out.println("Specialization: " + specialization);
    }
}