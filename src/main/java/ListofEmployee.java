import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListofEmployee implements Serializable {
    private Employee[] listOfEmployee;
    private int numberOfEmployee;
    private int maxNumberOfEmployee;


    public ListofEmployee() {
        this.maxNumberOfEmployee = 100;
        this.listOfEmployee = new Employee[maxNumberOfEmployee];
        this.numberOfEmployee = 0;
    }

    ;

    public void add(Employee employee) {
        if (numberOfEmployee == maxNumberOfEmployee) {
            System.out.println("Lista jest pełna. Maksymalna liczba pracowników: " + maxNumberOfEmployee);
        } else {
            this.listOfEmployee[numberOfEmployee] = employee;
            numberOfEmployee++;
        }
    }

    public void cancel(Employee employee) {
        int position = 0;
        for (int i = 0; i < numberOfEmployee; i++) {
            if (employee.equals(listOfEmployee[i])) {
                position = i;
            }
        }
        if (position != -1) {
            for (int i = position; i < numberOfEmployee - 1; i++) {
                listOfEmployee[i] = listOfEmployee[i + 1];
            }
        }
        listOfEmployee[numberOfEmployee - 1] = null;
        numberOfEmployee--;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Lista pracowników: \n");
        for (int i = 0; i < numberOfEmployee; i++) {
            stringBuilder.append(i + 1 + ". " + listOfEmployee[i].displayNameSalary() + "\n");
        }
        return stringBuilder.toString();
    }

    public Employee[] getListOfEmployee() {
        return listOfEmployee;
    }

    public int getNumberOfEmployee() {
        return numberOfEmployee;
    }

    public int getMaxNumberOfEmployee() {
        return maxNumberOfEmployee;
    }

    public int salaryHigherThan(Float salary) {
        int number = 0;
        for (int i = 0; i < numberOfEmployee; i++) {
            if (listOfEmployee[i].getSalary() > salary) {
                number++;
            }
        }
        return number;
    }

    public Float avgSalaryInDivison(int division) {
        int number = 0;
        float salary = 0;
        for (int i = 0; i < numberOfEmployee; i++) {
            if (listOfEmployee[i].getNumberOfDivision() == division) {
                number++;
                salary = listOfEmployee[i].getSalary() + salary;
            }
        }
        return salary / number;
    }

    public void theHighestSalary() {
        float man = 0;
        float woman = 0;
        for (int i = 0; i < numberOfEmployee; i++) {
            if (listOfEmployee[i].getSex() == Sex.M && listOfEmployee[i].getSalary() > man) {
                man = listOfEmployee[i].getSalary();
            } else if (listOfEmployee[i].getSex() == Sex.K && listOfEmployee[i].getSalary() > woman) {
                woman = listOfEmployee[i].getSalary();
            }
        }
        System.out.println("Najwyższa pensja wśród: \n" + "mężczyzn: " + man + "zł \n" + "kobiet: " + woman + "zł. \n");
    }

    public int employee(Sex sex) {
        int number = 0;
        for (int i = 0; i < numberOfEmployee; i++) {
            if (listOfEmployee[i].getSex() == sex) {
                number++;
            }
        }
        return number;
    }

    public int employee(Sex sex, List<Employee> list) {
        int number = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getSex() == sex) {
                number++;
            }
        }
        return number;
    }

    public void dataAboutDivision() {
        Map<Integer, List<Employee>> map = new HashMap<>();
        for (int i = 0; i < numberOfEmployee; i++) {
            if (map.containsKey(listOfEmployee[i].getNumberOfDivision())) {
                List<Employee> list = map.get(listOfEmployee[i].getNumberOfDivision());
                list.add(listOfEmployee[i]);
                map.put(listOfEmployee[i].getNumberOfDivision(), list);
            } else {
                List<Employee> list = new ArrayList<>();
                list.add(listOfEmployee[i]);
                map.put(listOfEmployee[i].getNumberOfDivision(), list);
            }
        }
        for (Map.Entry<Integer, List<Employee>> entry : map.entrySet()) {
            int division = entry.getKey();
            List<Employee> employees = entry.getValue();
            System.out.println("Numer działu: " + division + "\n"
                    + "liczba kobiet: " + employee(Sex.K, employees) + "\n"
                    + "liczba mężczyzn: " + employee(Sex.M, employees) + "\n"
                    + "średnia wysokość pensji w dziale: " + avgSalaryInDivison(division) + "\n");
        }
    }

    public double menToWomen() {
        double salaryMen = 0;
        double salaryWomen = 0;
        int men = 0;
        int women = 0;
        for (int i = 0; i < numberOfEmployee; i++) {
            if (listOfEmployee[i].getSex() == Sex.M) {
                salaryMen = salaryMen + listOfEmployee[i].getSalary();
                men = men + 1;
            } else {
                salaryWomen = salaryWomen + listOfEmployee[i].getSalary();
                women = women + 1;
            }
        }
        double avgMen = salaryMen / men;
        double avgWomen = salaryWomen / women;
        return avgWomen / avgMen;
    }

    public void riseSalary(float percent){
        for (int i = 0; i < numberOfEmployee; i++){
            float newSalary = listOfEmployee[i].rise(percent);
            listOfEmployee[i].setSalary(newSalary);
        }
    }
    public float riseSalaryByValue (float value){
        for (int i = 0; i < numberOfEmployee; i++){
            float newSalary = listOfEmployee[i].getSalary() + value;
            listOfEmployee[i].setSalary(newSalary);
        }
        int numberOfWomen = employee(Sex.K);
        int numberOfMan = employee(Sex.M);
        int allEmployee = numberOfMan + numberOfWomen;
        float menToWomen = (numberOfWomen * value) / (numberOfMan * value);
        System.out.println("Suma podwyżek:" + allEmployee * value);
        System.out.println("Stosunek sumy podwyżek dla kobiet do sumy podwyżek dla mężczyzn wynosi: " + menToWomen);
        return menToWomen;
    }
}



