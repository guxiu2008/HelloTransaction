package com.guxiu.transation.dao.impl;

import com.guxiu.transation.dao.AccountDao;
import com.guxiu.transation.domain.Account;
import com.sun.rowset.internal.Row;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

/**
 * Package: com.guxiu.transation.dao.impl
 * DESCRIPTION:
 *
 * @author guxiu2008
 * @create 2018-10-11 22:03
 **/
public class AccountDaoImpl implements AccountDao {
    // 声明JdbcTemplate属性及其setter方法
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //添加账户
    public int addAccount(Account account) {
        String insertSql = "insert into account(username,balance) values (?,?)";
        Object [] objects = new Object[] {
                account.getUsername(),
                account.getBalance()
        };
        //执行添加操作,返回的是受SQL语句影响的记录条数
        int num = this.jdbcTemplate.update(insertSql, objects);
        return num;
    }

    //更新账户
    public int updateAccount(Account account) {
        String updateSql = "update account set username=?, balance=? where id=?";
        Object [] objects = new Object[] {
                account.getUsername(),
                account.getBalance(),
                account.getId()
        };
        //执行更新操作,返回的是受SQL语句影响的记录条数
        int num = this.jdbcTemplate.update(updateSql, objects);
        return num;
    }

    public int deleteAccount(Account account) {
        String deleteSql = "delete from account where id = ?";
        Object [] objects = new Object[] {
                account.getId()
        };
        //执行删除操作,返回的是受SQL语句影响的记录条数
        int num = this.jdbcTemplate.update(deleteSql, objects);
        return num;
    }

    public Account findAccountById(int id) {
        // 定义SQL语句
        String selectSql = "select * from account where id = ?";
        // 创建一个新的BeanPropertyRowMapper对象
        RowMapper<Account> rowMapper = new BeanPropertyRowMapper<Account>(Account.class);
        // 将id绑定到SQL语句中,并通过RowMapper返回一个Object类型的单行记录
        return this.jdbcTemplate.queryForObject(selectSql, rowMapper, id);
    }

    public List<Account> findAllAccount() {
        // 定义SQL语句
        String selectSql = "select * from account";
        // 创建一个新的BeanPropertyRowMapper对象
        RowMapper<Account> rowMapper = new BeanPropertyRowMapper<Account>(Account.class);
        // 执行静态的SQL查询,并通过RowMapper返回结果
        return this.jdbcTemplate.query(selectSql, rowMapper);
    }
}
