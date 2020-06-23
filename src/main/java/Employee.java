import java.io.Serializable;

public class Employee implements Serializable {

    private String name;
    private String surname;
    private Sex sex;
    private int numberOfDivision;
    private float salary;
    private int age;
    private int kids;
    private boolean maritalStatus;

    public Employee(String name, String surname, Sex sex, int numberOfDivision, float salary, int age, int kids, boolean maritalStatus) {
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.numberOfDivision = numberOfDivision;
        this.salary = salary;
        this.age = age;
        this.kids = kids;
        this.maritalStatus = maritalStatus;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Sex getSex() {
        return sex;
    }

    public int getNumberOfDivision() {
        return numberOfDivision;
    }

    public float getSalary() {
        return salary;
    }

    public int getAge() {
        return age;
    }

    public int getKids() {
        return kids;
    }

    public boolean isMaritalStatus() {
        return maritalStatus;
    }

    public void setNumberOfDivision(int numberOfDivision) {
        this.numberOfDivision = numberOfDivision;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setKids(int kids) {
        this.kids = kids;
    }

    public void setMaritalStatus(boolean maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "name = " + name + '\'' +
                ", surname = '" + surname + '\'' +
                ", sex = " + sex +
                ", numberOfDivision = " + numberOfDivision +
                ", salary = " + salary +
                ", age = " + age +
                ", kids = " + kids +
                ", maritalStatus = " + maritalStatus;
    }
    public String toStringToFile(){
        return name + "," + surname + "," + sex + "," + numberOfDivision + "," + salary + "," + age + "," + kids + "," + maritalStatus;
    }

    public String displayNameSalary() {
        return name + " " + surname + ", wysokość pensji: " + salary + "zł.";
    }

    public String displayName() {
        return name.toUpperCase() + " " + surname.toUpperCase();
    }

    public boolean isHigher(float value) {
        return salary > value;
    }

    public float rise(float percent) {
        float finalPercent = percent;
        if (kids > 0) {
            finalPercent = finalPercent + 0.02f * kids;
        }
        if (maritalStatus) {
            finalPercent = finalPercent + 0.03f;
        }
        return salary * (1 + finalPercent);
    }

}


