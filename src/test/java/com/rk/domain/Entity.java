package com.rk.domain;

import com.rk.annotation.Column;
import com.rk.annotation.Table;

@Table("entity")
public class Entity {

    @Column("id")
    private long id;
    @Column()
    private String firstName;
    @Column("birthday")
    private double salary;
    private String lastName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
