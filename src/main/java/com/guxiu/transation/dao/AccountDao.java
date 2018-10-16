package com.guxiu.transation.dao;

import com.guxiu.transation.domain.Account;

import java.util.List;

/**
 * Package: com.guxiu.transation.dao
 * DESCRIPTION:
 *
 * @author guxiu2008
 * @create 2018-10-11 22:01
 **/
public interface AccountDao {
    // 添加
    public int addAccount(Account account);
    // 更新
    public int updateAccount(Account account);
    // 删除
    public int deleteAccount(Account account);
    // 通过ID查询
    public Account findAccountById(int id);
    // 查询所有账户
    public List<Account> findAllAccount();
}
