package com.omerProject.quizapp;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data //because im using lombok i dont need to add getters and setters just data tag
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String title;

    @ManyToMany // becouse one Quiz have many questions (will create quiz_questions table when service runs)
    private List<Question> questions;

}
