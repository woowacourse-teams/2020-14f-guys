package com.woowacourse.pelotonbackend;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

public class DataInitializeExecutionListener implements TestExecutionListener {
    @Override
    public void afterTestMethod(TestContext testContext) {
        ApplicationContext applicationContext = testContext.getApplicationContext();
        final JdbcTemplate jdbcTemplate = applicationContext.getBean(JdbcTemplate.class);
        jdbcTemplate.execute("truncate table MEMBER");
        jdbcTemplate.execute("alter table MEMBER alter column ID restart with 1");
        jdbcTemplate.execute("truncate table RACE");
        jdbcTemplate.execute("alter table RACE alter column ID restart with 1");
        jdbcTemplate.execute("truncate table REPORT");
        jdbcTemplate.execute("alter table REPORT alter column ID restart with 1");
        jdbcTemplate.execute("truncate table RIDER");
        jdbcTemplate.execute("alter table RIDER alter column ID restart with 1");
        jdbcTemplate.execute("truncate table CERTIFICATION");
        jdbcTemplate.execute("alter table CERTIFICATION alter column ID restart with 1");
        jdbcTemplate.execute("truncate table MISSION");
        jdbcTemplate.execute("alter table MISSION alter column ID restart with 1");
        jdbcTemplate.execute("truncate table CALCULATION");
        jdbcTemplate.execute("alter table CALCULATION alter column ID restart with 1");
    }
}
