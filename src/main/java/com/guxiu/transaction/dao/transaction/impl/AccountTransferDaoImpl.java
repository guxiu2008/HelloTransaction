package com.guxiu.transaction.dao.transaction.impl;

import com.guxiu.transaction.dao.transaction.AccountTransferDao;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Package: com.guxiu.transaction.dao.transaction.impl
 * DESCRIPTION:
 *
 * @author guxiu2008
 * @create 2018-10-17 22:14
 **/
public class AccountTransferDaoImpl implements AccountTransferDao {
    // 声明JdbcTemplate属性及其setter方法
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void transfer(String outUser, String inUser, Double money) {
        // 收款时,收款用户的余额=现有余额+所汇余额
        String increaseSql = "update account set balance = balance + ? " +
                "where username = ?";
        this.jdbcTemplate.update(increaseSql, money, inUser);

        // 汇款时,汇款用户的余额=现有余额+所汇余额
        String decreaseSql = "update account set balance = balance - ?" +
                "where username = ?";
        this.jdbcTemplate.update(decreaseSql, money, outUser);
    }
}
