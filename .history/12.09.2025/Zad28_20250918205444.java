public class Zad28 {
    public static void main(String[] args) {
    }
}

class HospitalStaff{
    String FirstName;
    String LastName;
    int age;
    double salary;

    public HospitalStaff(String firstName, String lastName, int age, double salary) {
        this.FirstName = firstName;
        this.LastName = lastName;
        if(age < 0) System.out.println("Age must be a positive number.");
        else this.age = age;
        if(salary < 0) System.out.println("Salary must be a positive number!");
        else this.salary = salary;
    }
    public void info(){
        System.out.println("First Name: " + FirstName);
        System.out.println("Last Name: " + LastName);
        System.out.println("Age: " + age);
        System.out.println("Salary: " + salary);
    }
}

class Doctor extends HospitalStaff