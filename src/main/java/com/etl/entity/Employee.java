package com.etl.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Employee")
@Data
public class Employee {
    @Id
    private Long id;
    private String name;
    private String address;
    private Double salary;
    private String department;
    private Integer age;
}
