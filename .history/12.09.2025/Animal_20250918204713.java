// Victor Dichev 12/4 Animal Task
public class Animal {
    int age;
    String gender;


    public boolean isMammal() {
        return true;
    }


    public void mate() {
        System.out.println("Mating");
    }
}


class Duck extends Animal {
    String beakColor = "yellow";


    public void quack() {
        System.out.println("Quack");
    }


    public void swim() {
        System.out.println("Swimming");
    }


    public boolean isMammal() {
        return false;
    }
}


class Fish extends Animal {
    int sizeInFt;
    boolean canEat;


    public void swim() {
        System.out.println("Swimming");
    }
    public boolean isMammal() {
        return false;
    }
}


class Zebra extends Animal {
    boolean is_wind = true;


    public void run() {
        System.out.println("Running");
    }
}



