import java.util.Scanner;

public class Engine {
    Scanner scanner = new Scanner(System.in);
    Menu menu = new Menu();
    CustomerService customerService = new CustomerService();
    String filePath = "baza.dat";
    ListofEmployee listofEmployee;
    String textFilePath = "lista pracowników";

    public void start() {
        listofEmployee = CustomerService.readListOfEmployee(filePath);
        boolean flag = false;
        while (!flag) {
            menu.mainMenu();
            int choice = choice();
            switch (choice) {
                case 0:
                    CustomerService.saveListOfEmployee(filePath,listofEmployee);
                    System.exit(0);
                    flag = true;
                    break;
                case 1:
                    System.out.println(listofEmployee.toString());
                    break;
                case 2:
                    listofEmployee.add(customerService.addEmployee());
                    break;
                case 3:
                    System.out.println("Wybierz numer pracownika, którego dane chcesz eksportować do pliku.");
                    Employee employee2 = customerService.employeeChoice(listofEmployee);
                    customerService.eksportToFile(employee2);
                    break;

                case 4:
                    System.out.println("Wybierz numer pracownika, którego chcesz usunąć.");
                    Employee employee = customerService.employeeChoice(listofEmployee);
                    listofEmployee.cancel(employee);
                    break;

                case 5:
                    System.out.println("Wybierz numer pracownika, którego dane chcesz edytować.");
                    Employee employee1 = customerService.employeeChoice(listofEmployee);
                    startEditMenu(employee1);
                    break;

                case 6:
                    startMenu1();
                    break;
                case 7:
                    customerService.eksportListofEmployeeToFile(listofEmployee,textFilePath);
                    startMenu2();
                    break;
                case 8:
                    System.out.println("Program wspomagający organizację w firmie.");
                    System.out.println("Autor: Małgorzata Cholewa");
                    break;
                case 9:
                    System.out.println("Podaj własną nazwę pliku.");
                    filePath = scanner.nextLine();
                    break;

                default:
                    System.out.println("Nie ma takiego punktu.");
                    break;
            }

        }
    }

    public int choice() {
        boolean flag = false;
        int number = 0;
        while (!flag) {
            String choice = scanner.nextLine();
            if (!choice.matches("[0-9]")) {
                System.out.println("Nie ma takiego punktu.");
            } else {
                flag = true;
                number = Integer.parseInt(choice);
            }
        }
        return number;
    }

    public void startEditMenu(Employee employee) {
        boolean flag = false;
        while (!flag) {
            menu.editEmployee();
            int choice = choice();
            switch (choice) {
                case 0:
                    flag = true;
                    break;
                case 1:
                    if (employee.getSex() == Sex.M) {
                        System.out.println("Nie można zmienić nazwiska dla mężczyzny.");
                        break;
                    } else {
                        System.out.println("Aktualna wartość: " + employee.getSurname());
                        employee.setSurname(customerService.checkDataString("Podaj nowe nazwisko pracownika."));
                    }
                    break;
                case 2:
                    System.out.println("Aktualna wartość: " + employee.getNumberOfDivision());
                    employee.setNumberOfDivision(customerService.checkDataInt("Podaj nowy numer działu."));
                    break;
                case 3:
                    System.out.println("Aktualna wartość: " + employee.getSalary());
                    employee.setSalary(customerService.checkDataFloat("Podaj wysokość pensji."));
                    break;
                case 4:
                    System.out.println("Aktualna wartość: " + employee.getAge());
                    employee.setAge(customerService.checkDataInt("Podaj wiek pracownika."));
                    break;
                case 5:
                    System.out.println("Aktualna wartość: " + employee.getKids());
                    employee.setKids(customerService.checkDataInt("Podaj liczbę dzieci."));
                    break;
                case 6:
                    System.out.println("Aktualna wartość: " + employee.isMaritalStatus());
                    employee.setMaritalStatus(customerService.checkDataBoolean("Podaj stan cywilny pracownika. 1 - mężatka/żonaty, 0 - w przeciwnym razie."));
                    break;
                default:
                    System.out.println("Nie ma takiego punktu.");
                    break;
            }
        }
    }
    public void startMenu1() {
        boolean flag = false;
        while (!flag) {
            menu.extraFunction();
            int choice = choice();
            switch (choice) {
                case 0:
                    flag = true;
                    break;
                case 1:
                    float salary = customerService.checkDataFloat("Podaj pensję.");
                    int number = listofEmployee.salaryHigherThan(salary);
                    System.out.println("Pracowników z pensją wyższą niż " + salary + "zł jest " + number);
                    break;
                case 2:
                    int division = customerService.checkDataInt("Podaj numer działu.");
                    float avgSalary = listofEmployee.avgSalaryInDivison(division);
                    System.out.println("Średnia wysokość pensji w dziale: " + division + " wynosi: " + avgSalary + "zł.");
                    break;
                case 3:
                    listofEmployee.theHighestSalary();
                    break;
                case 4:
                    listofEmployee.dataAboutDivision();
                    break;
                case 5:
                    System.out.println("Stosunek średniej płacy kobiet do średniej płacy mężczyzn wynosi: " + listofEmployee.menToWomen());
                    break;

                case 6:
                    float procent = customerService.checkDataFloat("Podaj procent o jaki chcesz zwiększyć pensje (wartość wprowadź w systemie dziesiętnym np 5% = 0.05.");
                    listofEmployee.riseSalary(procent);
                    break;

                case 7:
                    float value = customerService.checkDataFloat("Podaj wartość o jaką chcesz zwiększyć pensję wszystkim pracownikom.");
                    listofEmployee.riseSalaryByValue(value);
                    break;

                case 8:
                    boolean ascending = customerService.checkDataBoolean("Wybierz 1 jeżeli chcesz posortować rosnąco, a 0 jeżeli malejąco.");
                    customerService.sortTheFileName(listofEmployee,ascending,filePath);
                    listofEmployee = CustomerService.readListOfEmployee(filePath);
                    break;

                case 9:
                    boolean ascending2 = customerService.checkDataBoolean("Wybierz 1 jeżeli chcesz posortować rosnąco, a 0 jeżeli malejąco.");
                    customerService.sortTheFileSalary(listofEmployee,ascending2,filePath);
                    listofEmployee = CustomerService.readListOfEmployee(filePath);
                    break;

                default:
                    System.out.println("Nie ma takiego punktu.");
                    break;
            }
        }
    }
    public void startMenu2() {
        boolean flag = false;
        while (!flag) {
            menu.extraFunction7();
            int choice = choice();
            switch (choice) {
                case 0:
                    flag = true;
                    break;
                case 1:
                    customerService.theLongestSurname(textFilePath);
                    break;
                case 2:
                    customerService.avgAgePeopleWithKids(textFilePath);
                    break;
                case 3:
                    customerService.codingOfSurname(textFilePath);
                    break;
                case 4:
                    customerService.saveToHTML(textFilePath,"pracownicy.html");
                    break;
                default:
                    System.out.println("Nie ma takiego punktu.");
                    break;
            }
        }
    }
}
