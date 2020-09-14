package com.company;

//* Создать класс "Сотрудник" с полями: ФИО, должность, email, телефон, зарплата, возраст;
//* Конструктор класса должен заполнять эти поля при создании объекта;
//* Внутри класса «Сотрудник» написать метод, который выводит информацию об объекте в консоль;
//* Создать массив из 5 сотрудников

import com.company.Emploee.Emploee;

public class Main {

    public static void main(String[] args) {
        Emploee[] employees = new Emploee[5];

        employees[0] = new Emploee("Вольфганг Амадей Моцарт", "Композитор", "Amadeus2701@gmail.com", "777-11-22-33", 1000, 264);
        employees[1] = new Emploee("Вашингтон Джордж", "Президент Конгресса Конфедерации", "George1732@mail.ru", "777-44-55-66", 2000, 231);
        employees[2] = new Emploee("Курт Дональд Кобейн", "Певец", "Kurt20@list.ru", "777-21-42-37", 3000, 60);
        employees[3] = new Emploee("Джугашвили Иосиф Виссарионович", "Секретарь ЦК ВКП — КПСС", "Stalin@kremlin.ru", "777-00-00-01", 4000, 142);
        employees[4] = new Emploee("Гай Юлий Цезарь", "Великий понтифик", "venividivici@roma.it", "777-43-76-53", 5000, 2120);

        for (int i = 0; i < employees.length; i++) {
            employees[i].print();
        }
    }
}

