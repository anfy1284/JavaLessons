package com.company.Emploee;

public class Emploee {
    String name;
    String position;
    String email;
    String phoneNumber;
    double salary;
    int age;

    public Emploee(String name, String position, String email, String phoneNumber, double salary, int age) {
        this.name = name;
        this.position = position;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        this.age = age;
    }

    public void print() {
        String str =
                "ФИО:       " + name + "\n" +
                        "Должность: " + position + "\n" +
                        "E-mail:    " + email + "\n" +
                        "Телефон    " + phoneNumber + "\n" +
                        "Зарплата:  " + salary + "\n" +
                        "Возраст:   " + age + "\n\n";
        System.out.print(str);
    }
}