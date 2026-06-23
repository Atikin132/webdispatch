package com.example.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class User {
    private Integer id;
    private String login;
    private String password;
    private String name;
    private LocalDate birthday;
    private Integer age;
    private BigDecimal salary;
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(Integer id,
                String login,
                String password,
                String name,
                LocalDate birthday,
                Integer age,
                BigDecimal salary) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.birthday = birthday;
        this.age = age;
        this.salary = salary;
    }

    public User(Integer id,
                String login,
                String password,
                String name,
                LocalDate birthday,
                Integer age,
                BigDecimal salary,
                Set<Role> roles) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.birthday = birthday;
        this.age = age;
        this.salary = salary;
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Integer roleId) {
        this.roles.add(new Role(roleId));
    }

    public boolean hasRole(String roleName) {
        return roles.stream()
                .anyMatch(role -> roleName.equals(role.getName()));
    }

    public boolean hasRole(int roleId) {
        return roles.stream()
                .anyMatch(role -> roleId == role.getId());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", age=" + age +
                ", salary=" + salary +
                ", roles=" + roles +
                '}';
    }
}
