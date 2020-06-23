import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerService {
    Scanner scanner = new Scanner(System.in);

    public Employee addEmployee() {
        String name = checkDataString("Podaj imię pracownika.");
        String surname = checkDataString("Podaj nazwisko pracownika.");
        Sex sex = checkSex("Podaj płeć pracownika. K - kobieta, M - mężczyzna.");
        int numberOfDivision = checkDataInt("Podaj numer działu pracownika.");
        float salary = checkDataFloat("Podaj wysokość pensji pracownika.");
        int age = checkDataInt("Podaj wiek pracownika.");
        int kids = checkDataInt("Podaj ile dzieci ma pracownik.");
        boolean maritalStatus = checkDataBoolean("Podaj stan cywilny pracownika. 1 - mężatka/żonaty, 0 - w przeciwnym razie.");
        return new Employee(name, surname, sex, numberOfDivision, salary, age, kids, maritalStatus);
    }

    public String checkDataString(String statement) {
        boolean flag = false;
        String name = "";
        while (!flag) {
            System.out.println(statement);
            String choice = scanner.nextLine().trim().toLowerCase();
            if (!choice.matches("[a-złąęćśżźó]+")) {
                System.out.println("Wprowadzono błędne dane.");
            } else {
                flag = true;
                name = choice.substring(0, 1).toUpperCase() + choice.substring(1).toLowerCase();
            }
        }
        return name;
    }

    public Sex checkSex(String statement) {
        boolean flag = false;
        Sex sex = Sex.M;
        while (!flag) {
            System.out.println(statement);
            String choice = scanner.nextLine().trim();
            if (!choice.matches("[mMkK]")) {
                System.out.println("Wprowadzono błędne dane. Wybierz K lub M");
            } else {
                flag = true;
                choice = choice.toUpperCase();
                if (choice.equals("K")) {
                    sex = Sex.K;
                }
            }
        }
        return sex;
    }

    public int checkDataInt(String statement) {
        boolean flag = false;
        int number = 0;
        while (!flag) {
            System.out.println(statement);
            String choice = scanner.nextLine().trim();
            if (!choice.matches("[0-9]+")) {
                System.out.println("Wprowadzono błędne dane.");
            } else {
                flag = true;
                number = Integer.parseInt(choice);
            }
        }
        return number;
    }

    public float checkDataFloat(String statement) {
        boolean flag = false;
        float number = 0;
        while (!flag) {
            System.out.println(statement);
            String choice = scanner.nextLine().trim();
            if (!choice.matches("^[0-9]+$|^[0-9]+\\.[0-9][0-9]?$")) {
                System.out.println("Wprowadzono błędne dane.");
            } else {
                flag = true;
                number = Float.parseFloat(choice);
            }
        }
        return number;
    }

    public boolean checkDataBoolean(String statement) {
        boolean flag = false;
        boolean number = false;
        while (!flag) {
            System.out.println(statement);
            String choice = scanner.nextLine().trim();
            if (!choice.matches("[01]")) {
                System.out.println("Wprowadzono błędne dane.");
            } else {
                flag = true;
                if (choice.equals("1")) {
                    number = true;
                }
            }
        }
        return number;
    }

    public void eksportToFile(Employee employee) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(employee.getSurname()).append(employee.getName()).append(employee.getSex()).append(employee.getNumberOfDivision())
                .append(employee.getAge()).append(employee.getKids());
        String filePath = stringBuilder.toString();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(employee.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Employee employeeChoice(ListofEmployee listofEmployee) {
        System.out.println(listofEmployee.toString());
        boolean flag = false;
        Employee employee = null;
        while (!flag) {
            int number = scanner.nextInt();
            if (number < 1 || number > listofEmployee.getNumberOfEmployee()) {
                System.out.println("Nie ma pracownika o takim numerze. Podaj poprawny numer.");
            } else {
                employee = listofEmployee.getListOfEmployee()[number - 1];
                flag = true;
            }
        }
        return employee;
    }

    public static void saveListOfEmployee (String fileName, ListofEmployee listofEmployee){
        try{
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName));
            outputStream.writeObject(listofEmployee);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ListofEmployee readListOfEmployee(String fileName){
        ListofEmployee listofEmployee = null;
        try{
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName));
            listofEmployee = (ListofEmployee) inputStream.readObject();
            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return listofEmployee;
    }

    public void eksportListofEmployeeToFile(ListofEmployee listofEmployee, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < listofEmployee.getNumberOfEmployee(); i++){
                writer.write(listofEmployee.getListOfEmployee()[i].toStringToFile());
                writer.write("\n");
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sortTheFileSalary (ListofEmployee listofEmployee, boolean ascending, String filePath){
        ListofEmployee sortedList = new ListofEmployee();
        for (int i = 0; i < listofEmployee.getNumberOfEmployee(); i++){
            sortedList.add(listofEmployee.getListOfEmployee()[i]);
        }
        for (int j = 0; j < sortedList.getNumberOfEmployee() - 1; j++) {
            for (int i = 0; i < sortedList.getNumberOfEmployee() - 1; i++) {
                if ((ascending == true && sortedList.getListOfEmployee()[i].getSalary() > sortedList.getListOfEmployee()[i + 1].getSalary()) ||
                        ((ascending == false) && sortedList.getListOfEmployee()[i].getSalary() < sortedList.getListOfEmployee()[i + 1].getSalary())) {
                    Employee r = sortedList.getListOfEmployee()[i + 1];
                    Employee z = sortedList.getListOfEmployee()[i];
                    sortedList.getListOfEmployee()[i + 1] = z;
                    sortedList.getListOfEmployee()[i] = r;
                }
            }
        }
        CustomerService.saveListOfEmployee(filePath,sortedList);
    }

    public void sortTheFileName (ListofEmployee listofEmployee, boolean ascending, String filePath) {
        ListofEmployee sortedList = new ListofEmployee();
        for (int i = 0; i < listofEmployee.getNumberOfEmployee(); i++) {
            sortedList.add(listofEmployee.getListOfEmployee()[i]);
        }
        for (int j = 0; j < sortedList.getNumberOfEmployee() - 1; j++) {
            for (int i = 0; i < sortedList.getNumberOfEmployee() - 1; i++) {
                if ((ascending == true && sortedList.getListOfEmployee()[i].getSurname().compareTo(sortedList.getListOfEmployee()[i + 1].getSurname()) > 0) ||
                        ((ascending == false) && sortedList.getListOfEmployee()[i].getSurname().compareTo(sortedList.getListOfEmployee()[i + 1].getSurname()) < 0)) {
                    Employee r = sortedList.getListOfEmployee()[i + 1];
                    Employee z = sortedList.getListOfEmployee()[i];
                    sortedList.getListOfEmployee()[i + 1] = z;
                    sortedList.getListOfEmployee()[i] = r;
                }
            }
        }
        CustomerService.saveListOfEmployee(filePath,sortedList);
    }

    public void theLongestSurname (String filePath){
        String line;
        String[] resultArray = new String[]{"","","","","","","","",""};
        String result = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            while ((line = bufferedReader.readLine())!= null){
                String[] array = line.split(",");
                if (array[1].length() > resultArray[1].length()){
                    result = line;
                    resultArray = array;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }
    public void  avgAgePeopleWithKids (String filePath){
        String line;
        int numberOfpeopleWithKids = 0;
        float age = 0;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            while ((line = bufferedReader.readLine())!= null){
                String[] array = line.split(",");
                if (Integer.parseInt(array[6]) > 0){
                    numberOfpeopleWithKids = numberOfpeopleWithKids + 1;
                    age = age + Integer.parseInt(array[5]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (numberOfpeopleWithKids > 0){
            double results = age/numberOfpeopleWithKids;
            results*=100;
            results = Math.round(results);
            results /=100;
            System.out.println("Średnia wieku osób posiadających dzieci wynosi: " + results);
        } else {
            System.out.println("Żaden pracownik nie posiada dzieci");
        }
    }

    public void codingOfSurname(String filePath){
        List<String[]> list = new ArrayList<>();
        String line;
        int numberOfEmployees = 0;
        double sumOfSalary = 0;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            while ((line = bufferedReader.readLine())!= null){
                String[] array = line.split(",");
                numberOfEmployees = numberOfEmployees + 1;
                sumOfSalary = sumOfSalary + Double.parseDouble(array[4]);
                list.add(array);
                }
            } catch (IOException ex) {
            ex.printStackTrace();
        }
        double avg = sumOfSalary/numberOfEmployees;
        for (String[] row : list){
            if (Double.parseDouble(row[4]) < avg){
                String codeSurname;
                int lenght = row[1].length() - 2;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(row[1].substring(0,1));
                for (int i = 0; i < lenght; i++){
                    stringBuilder.append("*");
                }
                stringBuilder.append(row[1].substring(row[1].length() - 1,row[1].length()));
                row[1] = stringBuilder.toString();
            }
        }

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
            StringBuilder stringBuilder = new StringBuilder();
            for (String[] row : list){
                for (String part : row){
                    stringBuilder.append(part).append(",");
                }
                stringBuilder.append("\n");
            }
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToHTML(String filePath, String filePathHTML){
        String option = "<style> td { border: 1px solid black; } </style>\n";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(option).append("<table>\n");
        String headers = "<tr>\n <td> 'Nazwisko' </td> <td> 'Imię' </td> <td> 'Płeć' </td> <td> 'Numer działu' </td> <td> 'Płaca' </td> <td> 'Wiek' </td> \n </tr>\n";
        stringBuilder.append(headers);
        String line;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePathHTML));

            while ((line = bufferedReader.readLine())!= null){
                String[] array = line.split(",");
                stringBuilder.append("<tr>\n <td>" + array[1]+ "</td> <td>" + array[0] +"</td> <td>" + array[2] +
                        "</td> <td>" + array[3] + "</td> <td>" + array[4] + "</td> <td>" + array[5] + "</td> \n </tr>\n");
            }
            stringBuilder.append("</table>");
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}