package com.omerProject.quizapp.controller;

import com.omerProject.quizapp.QuestionWrapper;
import com.omerProject.quizapp.Quiz;
import com.omerProject.quizapp.Response;
import com.omerProject.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category,
                                             @RequestParam Integer numberOfQuestions,
                                             @RequestParam String title) {

        return quizService.createQuiz(category, numberOfQuestions, title);
    }

    @GetMapping("getAllQuiz")
    public ResponseEntity<List<Quiz>> getAllQuiz(){
        return quizService.getAllQuiz();
    }

    @GetMapping("getQuestionsByQuizId={id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(@PathVariable Integer id){
        return quizService.getQuizWrappedQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submit(@PathVariable Integer id,
    @RequestBody List<Response> responses) {
        return quizService.calculateResult(id, responses);

    }


}
