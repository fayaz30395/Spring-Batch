package com.batch;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="user_data")
@Data
public class User {
    @Id
    private Integer id;
    private String name;
    private Integer salary;
    private String dep;
}
