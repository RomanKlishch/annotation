package com.rk.domain;

import com.rk.annotation.Column;
import com.rk.annotation.Id;
import com.rk.annotation.Table;

@Table("entity")
public class Entity {

    @Column("id")
    @Id
    private long id;
    @Column()
    private String firstName;
    @Column("salary")
    private double salary;
    private String lastName;


    public void setId(long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
