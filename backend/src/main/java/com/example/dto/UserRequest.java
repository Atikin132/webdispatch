package com.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

public class UserRequest {
    private Integer id;
    @NotBlank(message = "{validationLoginRequired}")
    @Size(max = 50, message = "{validationLoginTooLong}")
    private String login;

    @NotBlank(message = "{validationPasswordRequired}")
    @Size(min = 6, message = "{validationPasswordTooShort}")
    private String password;

    @NotBlank(message = "{validationNameRequired}")
    @Size(max = 100, message = "{validationNameTooLong}")
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotNull(message = "{validationAgeRequired}")
    @Min(value = 19, message = "{validationAgeTooYoung}")
    private Integer age;

    @NotNull(message = "{validationSalaryRequired}")
    @DecimalMin(value = "0", message = "{validationSalaryNoNegative}")
    @DecimalMax(value = "99999999.99", message = "{validationSalaryTooLarge}")
    @Digits(integer = 8, fraction = 2, message = "{validationSalaryTwoDecimal}")
    private BigDecimal salary;

    @NotEmpty(message = "{validationRolesOneRequired}")
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
        return "UserRequest{" + "id=" + id + ", login='" + login + '\'' + ", password='" +
                password + '\'' + ", name='" + name + '\'' + ", birthDate=" + birthDate + ", age=" +
                age + ", salary=" + salary + ", roleIds=" + Arrays.toString(roles) + '}';
    }
}
