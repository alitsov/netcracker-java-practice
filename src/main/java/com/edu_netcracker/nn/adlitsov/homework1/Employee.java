package com.edu_netcracker.nn.adlitsov.homework1;

import java.util.Random;

public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private int salary;

    public Employee(int id, String firstName, String lastName, int salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getAnnualSalary() {
        return 12 * salary;
    }

    public int raiseSalary(int percent) {
        salary *= 1 + percent / 100.0;
        return salary;
    }

    @Override
    public String toString() {
        return "Employee[id=" + id + ", name=" + firstName + " " + lastName + ", salary=" + salary + "]";
    }

    public static void main(String[] args) {
        Random rnd = new Random();
        Employee emp = new Employee(rnd.nextInt(Integer.MAX_VALUE), "Steve", "Jobs", 1_000_000);
        System.out.println(emp);
        emp.raiseSalary(20);
        System.out.println(emp);
        System.out.println("Annual salary is:" + emp.getAnnualSalary());
    }
}