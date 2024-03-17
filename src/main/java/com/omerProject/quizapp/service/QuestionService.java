package com.omerProject.quizapp.service;

import com.omerProject.quizapp.Question;
import com.omerProject.quizapp.dao.QuestionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionDAO questionDAO;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDAO.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDAO.findByCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(questionDAO.findByCategory(category), HttpStatus.BAD_GATEWAY);
        }
    }


    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionDAO.save(question);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("fail to add", HttpStatus.BAD_GATEWAY);
        }
    }

    public List<Question> getLimitedQuestionsByCategory(String category, Integer numberOfQuestions) {
        return questionDAO.findRandomQuestionsByCategory(category,numberOfQuestions);
    }

    public Optional<Question> findQuestionById(Integer id) {
        return questionDAO.findById(id);
    }
}
