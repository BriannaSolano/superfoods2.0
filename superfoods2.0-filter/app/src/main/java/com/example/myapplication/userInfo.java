package com.example.myapplication;

public class userInfo {
    private String Name, BirthdayMonth, BirthdayDay, BirthdayYear, Customer, Employee;


    public userInfo() {
    }

    public userInfo(String name, String birthdayMonth, String birthdayDay, String birthdayYear, String customer, String employee) {
        Name = name;
        BirthdayMonth = birthdayMonth;
        BirthdayDay = birthdayDay;
        BirthdayYear = birthdayYear;
        Customer = customer;
        Employee = employee;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBirthdayMonth() {
        return BirthdayMonth;
    }

    public void setBirthdayMonth(String birthdayMonth) {
        BirthdayMonth = birthdayMonth;
    }

    public String getBirthdayDay() {
        return BirthdayDay;
    }

    public void setBirthdayDay(String birthdayDay) {
        BirthdayDay = birthdayDay;
    }

    public String getBirthdayYear() {
        return BirthdayYear;
    }

    public void setBirthdayYear(String birthdayYear) {
        BirthdayYear = birthdayYear;
    }

    public String getCustomer() {
        return Customer;
    }

    public void setCustomer(String customer) {
        Customer = customer;
    }

    public String getEmployee() {
        return Employee;
    }

    public void setEmployee(String employee) {
        Employee = employee;
    }
}
