package io.github.keeeper.learningspring;

import io.github.keeeper.learningspring.model.Task;
import io.github.keeeper.learningspring.model.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.*;

@Configuration
public class TestConfiguration {

    @Bean
    @Primary
    @Profile("!integration")
    DataSource eteTestDataSource(){
        var result = new DriverManagerDataSource("j2bc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
        result.setDriverClassName("org.h2.Driver");
        return result;
    }

    @Bean
    @Primary
    @Profile("integration")
    TaskRepository testRepo(){
        return new TaskRepository() {

            private Map<Integer,Task> tasks = new HashMap<>();
            @Override
            public List<Task> findAll() {
                return new ArrayList<>(tasks.values());
            }

            @Override
            public Page<Task> findAll(Pageable page) {
                return null;
            }

            @Override
            public Optional<Task> findById(Integer i) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Integer id) {
                return tasks.containsKey(id);
            }

            @Override
            public boolean existsByDoneIsFalseAndGroup_Id(Integer groupId) {
                return false;
            }

            @Override
            public Task save(Task entity) {
                return tasks.put(tasks.size() + 1, entity);
            }

            @Override
            public List<Task> findByDone(boolean done) {
                return null;
            }
        };
    }
}
