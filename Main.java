
package com.anfy.java2.lesson3;

import java.util.*;

public class Main {

    //Классы и методы первого задания

    public static HashMap<String, Integer> getWordsCounter(String str){
        String[] wordArray = str.split("\\s+");
        HashMap<String, Integer> wordsHashMap = new HashMap<String, Integer>();
        Integer gotValue;
        for (int i = 0; i < wordArray.length; i++) {
            gotValue = wordsHashMap.get(wordArray[i]);
            if(gotValue == null){
                wordsHashMap.put(wordArray[i], 1);
            }else{
                wordsHashMap.put(wordArray[i], ++gotValue);
            }
        }
        return wordsHashMap;
    }

    public static void echoWordsCounter(HashMap<String, Integer> map){
        System.out.println("contains words: ");

        for (Map.Entry<String, Integer> entry: map.entrySet()) {
            System.out.printf("%10s: %2d\n", entry.getKey(), entry.getValue());
        }

    }

    //Классы и методы второго задания

    static class Person{
        static final String SPLIT_RULE = "\\s*[;||,]\\s*";
        private String lastName;
        private String otherNames;

        private HashSet<String> emails;
        private HashSet<String> phones;

        public void setNames(String lastName, String otherNames){
            this.lastName = lastName;
            this.otherNames = otherNames;
        }

        public void setName(String name){
            String[] tmpArr = name.split("\\s+",2);
            if(tmpArr.length > 1){
                setNames(tmpArr[0], tmpArr[1]);
            }else{
                setNames(name, "");
            }
        }

        public void setEmails(String emails){
            this.emails.addAll(Arrays.asList(emails.split(SPLIT_RULE)));
        }

        public void setPhones(String phones){
            this.phones.addAll(Arrays.asList(phones.split(SPLIT_RULE)));
        }

        public void set(String name, String emails, String phones){
            setName(name);
            setEmails(emails);
            setPhones(phones);
        }

        void init(){
            emails = new HashSet<>();
            phones = new HashSet<>();
        }

        Person(String name, String emails, String phones){
            init();
            set(name, emails, phones);
        }

        Person(String name){
            init();
            setName(name);
        }

        String getLastName(){
            return lastName;
        }

        String getName(){
            return getLastName() + " " + otherNames;
        }

        ArrayList<String> getPhones(){
            return new ArrayList<String>(phones);
        }

        ArrayList<String> getEmails(){
            return new ArrayList<String>(emails);
        }

    }

    static class PhoneBook{

        private HashMap<String, ArrayList<Person>> entries;

        PhoneBook(){
            entries = new HashMap<>();
        }

        void add(String name, String emails, String phones){
            Person person = new Person(name, emails, phones);
            ArrayList<Person> persons = entries.get(person.lastName);
            if(persons == null){
                persons = new ArrayList<>();
                persons.add(person);
                entries.put(person.lastName, persons);
            }else {
                persons.add(person);
            }
        }

        ArrayList<Person> get(String lastName){
            return get(lastName, true);
        }

        ArrayList<Person> get(String lastName, boolean echo){
            ArrayList<Person> persons = entries.get(lastName);

            if(persons == null) persons = new ArrayList<Person>();

            if(echo){
                System.out.println("\nsearching results for \"" + lastName + "\":");
                int i = 1;
                for (Person person: persons) {
                    System.out.printf("\n%5d: %s\n",i, person.getName());
                    System.out.printf("          emails: %s\n", person.getEmails());
                    System.out.printf("          phones: %s\n", person.getPhones());
                    i++;
                }
            }

            return persons;
        }
    }

    public static void main(String[] args) {

        //Создать массив с набором слов (20-30 слов, должны встречаться повторяющиеся):
        //Найти список слов, из которых состоит текст (дубликаты не считать);
        //Посчитать сколько раз встречается каждое слово (использовать HashMap);

        echoWordsCounter(getWordsCounter("qwe qwe gfdg gtrgtrg e tgtrh tnet rbethtre gbthrt qwe fd fd 343 fd rbethtre"));


        //Написать простой класс PhoneBook(внутри использовать HashMap):
        //В качестве ключа использовать фамилию
        //В каждой записи всего два поля: phone, e-mail
        //Отдельный метод для поиска номера телефона по фамилии (ввели фамилию, получили ArrayList телефонов), и отдельный метод для поиска e-mail по фамилии.
        //* Следует учесть, что под одной фамилией может быть несколько записей. Итого должно получиться 3 класса Main, PhoneBook, Person.

        PhoneBook phoneBook = new PhoneBook();
        phoneBook.add("Иванов Иван Иванович", "ivanov@mail.ru; ivanov@gmail.com; ivanov@gmail.com", "8-123-433-54-34, 456-33-45");
        phoneBook.add("Иванов Семен Петрович", "sema@mail.ru; sema@gmail.com; sema123@gmail.com", "8-634-645-78-12, 745-74-23");
        phoneBook.add("Петров Петр Петрович", "petr@mail.ru; petr@gmail.com", "8-634-543-47-32, 634-65-46");

        phoneBook.get("Иванов");
        phoneBook.get("Петров");

    }
}
