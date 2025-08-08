package sto.study_plaza.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HealthCheckService {

    private final JdbcTemplate jdbcTemplate;

    public Map<String, Object> runHealthCheck(String input) {
        Map<String, Object> result = new HashMap<>();

        String processed = processString(input);
        result.put("backendTest", processed);

        try {
            checkAndCreateTable();
            insertTestData(input);
            List<Map<String, Object>> rows = readTestData();
            result.put("dbTest", rows);
        } catch (Exception e) {
            result.put("dbError", e.getMessage());
        }

        return result;
    }

    private String processString(String input) {
        StringBuilder odd = new StringBuilder();
        StringBuilder even = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            if (i % 2 == 0) {
                odd.append(input.charAt(i));
            } else {
                even.append(input.charAt(i));
            }
        }
        even.reverse();

        StringBuilder result = new StringBuilder();
        int oddIndex = 0, evenIndex = 0;
        for (int i = 0; i < input.length(); i++) {
            if (i % 2 == 0) {
                result.append(odd.charAt(oddIndex++));
            } else {
                result.append(even.charAt(evenIndex++));
            }
        }
        return result.toString();
    }

    private void checkAndCreateTable() {
        jdbcTemplate.execute("""
            CREATE TABLE IF NOT EXISTS test_table (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(255),
                created_at DATETIME
            )
        """);
    }

    private void insertTestData(String name) {
        jdbcTemplate.update(
                "INSERT INTO test_table (name, created_at) VALUES (?, ?)",
                name, LocalDateTime.now()
        );
    }

    private List<Map<String, Object>> readTestData() {
        return jdbcTemplate.queryForList("SELECT * FROM test_table ORDER BY id DESC LIMIT 5");
    }
}