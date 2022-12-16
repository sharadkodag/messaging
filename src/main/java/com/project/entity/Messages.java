package com.project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Messages {

    @Id
    @SequenceGenerator(name = "sr", sequenceName = "sr", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "sr", strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String message;
    private LocalDateTime dateTime;
    @ManyToOne
    private User sender;
    @ManyToOne
    private User receiver;

    public Messages(String message, LocalDateTime dateTime, User sender, User receiver){
        this.message = message;
        this.dateTime = dateTime;
        this.sender = sender;
        this.receiver = receiver;
    }
}
