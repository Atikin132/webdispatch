package com.example.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

public class UserFormDTO {
    private Integer id;

    @NotBlank(message = "Login is required")
    @Size(max = 50, message = "Login cannot be longer than 50 characters")
    private String login;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must contain at least 6 characters")
    private String password;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name cannot be longer than 100 characters")
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotNull(message = "Age is required")
    @Min(value = 19, message = "User must be older than 18")
    private Integer age;

    @NotNull(message = "Salary is required")
    @DecimalMin(value = "0", message = "Salary cannot be negative")
    @DecimalMax(value = "99999999.99", message = "Salary is too large")
    @Digits(integer = 8, fraction = 2, message = "Salary can have max 2 decimal places")
    private BigDecimal salary;

    @NotEmpty(message = "At least one role must be selected")
    private String[] roles;

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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
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

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserFormDTO{" + "id=" + id + ", login='" + login + '\'' + ", password='" +
                password + '\'' + ", name='" + name + '\'' + ", birthDate=" + birthDate + ", age=" +
                age + ", salary=" + salary + ", roleIds=" + Arrays.toString(roles) + '}';
    }
}
