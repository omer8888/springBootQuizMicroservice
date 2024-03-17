package com.omerProject.quizapp.service;

import com.omerProject.quizapp.Question;
import com.omerProject.quizapp.QuestionWrapper;
import com.omerProject.quizapp.Quiz;
import com.omerProject.quizapp.Response;
import com.omerProject.quizapp.dao.QuizDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizService {

    @Autowired
    QuizDAO quizDAO;

    @Autowired
    QuestionService questionService;

    public ResponseEntity<String> createQuiz(String category, Integer numberOfQuestions, String title) {
        List<Question> questions = getLimitedQuestionsForQuiz(category, numberOfQuestions);
        Quiz quiz = new Quiz();
        quiz.setQuestions(questions);
        quiz.setTitle(title);

        quizDAO.save(quiz);
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

    private List<Question> getLimitedQuestionsForQuiz(String category, Integer numberOfQuestions) {
        return questionService.getLimitedQuestionsByCategory(category, numberOfQuestions);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizWrappedQuestions(Integer id) {
        // Check if the quiz with the provided ID exists
        Optional<Quiz> quizOptional = quizDAO.findById(id);
        if (quizOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 if quiz not found
        }

        Quiz quiz = quizOptional.get();
        List<Question> questions = quiz.getQuestions();

        // Convert questions to QuestionWrapper objects
        List<QuestionWrapper> questionsWrapper =
                questions.stream()
                        .map(this::questionToWrapper)
                        .collect(Collectors.toList());

        return new ResponseEntity<>(questionsWrapper, HttpStatus.OK);
    }

    // Convert Question to QuestionWrapper
    private QuestionWrapper questionToWrapper(Question question) {
        QuestionWrapper questionWrapper = new QuestionWrapper();
        questionWrapper.setQuestionTitle(question.getQuestionTitle());
        questionWrapper.setOption1(question.getOption1());
        questionWrapper.setOption2(question.getOption2());
        questionWrapper.setOption3(question.getOption3());
        questionWrapper.setOption4(question.getOption4());

        return questionWrapper;
    }

    public ResponseEntity<List<Quiz>> getAllQuiz() {
        return new ResponseEntity<>(quizDAO.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Optional<Quiz> quizOptional = quizDAO.findById(id);
        if (quizOptional.isEmpty()) {
            return new ResponseEntity<>(0, HttpStatus.NOT_FOUND); // Return 404 if quiz not found
        }
        Quiz quiz = quizOptional.get();
        List<Question> questions = quiz.getQuestions();

        Integer score = 0;
        for (Response response : responses) {
            // Get the question corresponding to the response ID
            Optional<Question> optionalQuestion = questionService.findQuestionById(response.getId());
            if (optionalQuestion.isEmpty()) {
                continue;
            }
            Question question = optionalQuestion.get();
            // Compare response with the right answer of the corresponding question
            if (response.getResponse().equals(question.getRightAnswer())) {
                score++;
            }
        }
        return new ResponseEntity<>(score, HttpStatus.OK);

    }
}
