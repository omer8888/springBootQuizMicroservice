package com.omerProject.quizapp.dao;

import com.omerProject.quizapp.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

//becouse im using data jpa i can create interface that dao extends
//table name is Question same as the class name
@Repository
public interface QuestionDAO extends JpaRepository<Question, Integer> {
    public List<Question> findByCategory(String category);

    @Query(value = "select * from question q where q.category=:category order by RAND() Limit :numberOfQuestions", nativeQuery = true)
    public List<Question> findRandomQuestionsByCategory(String category, Integer numberOfQuestions);
}
