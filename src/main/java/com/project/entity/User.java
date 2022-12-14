package com.project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @SequenceGenerator(name = "no", sequenceName = "no", initialValue = 101, allocationSize = 1)
    @GeneratedValue(generator = "no", strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate dob;
    private String gender;
    @OneToMany(mappedBy = "sender")
    private List<Messages> messageList1;
    @OneToMany(mappedBy = "receiver")
    private List<Messages> messageList2;

}
