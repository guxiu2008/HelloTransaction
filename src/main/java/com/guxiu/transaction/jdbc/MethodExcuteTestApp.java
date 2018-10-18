package com.guxiu.transaction.jdbc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Package: com.guxiu.transaction.jdbc
 * DESCRIPTION:
 *
 * @author guxiu2008
 * @create 2018-10-11 21:32
 **/
public class MethodExcuteTestApp {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("excute/applicationContext.xml");
        JdbcTemplate jdbcTemplate = (JdbcTemplate) applicationContext.getBean("jdbcTemplate");

        String dropSql = "drop table account";
        String creaSql = "create table account (" +
                "id int primary key auto_increment, " +
                "username varchar(50), " +
                "balance double)";
        try {
            jdbcTemplate.execute(dropSql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        jdbcTemplate.execute(creaSql);
        System.out.println("数据表account创建成功!");
    }
}
