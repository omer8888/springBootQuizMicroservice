package com.omerProject.quizapp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

//same as Question entity without rightAnswer category and difficultyLevel;
@Entity
@Data
public class QuestionWrapper {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
}
