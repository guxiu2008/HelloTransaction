package com.guxiu.transation.jdbc;

import com.guxiu.transation.dao.AccountDao;
import com.guxiu.transation.domain.Account;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Package: com.guxiu.transation.jdbc
 * DESCRIPTION:
 *
 * @author guxiu2008
 * @create 2018-10-15 22:35
 **/
public class MethodQueryTestApp {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("query/applicationContext.xml");
        AccountDao accountDao = (AccountDao) applicationContext.getBean("accountDao");
        Account account = accountDao.findAccountById(1);
        if (account != null) {
            System.out.println(String.format("通过id[%d]查询获得账号信息:name=[%s],balance=[%f]", account.getId(), account.getUsername(), account.getBalance()));
        }

        List<Account> list = accountDao.findAllAccount();
        for (Account account1: list) {
            System.out.println(String.format("全量查询出账号按信息:name=[%s],balance=[%f]", account.getUsername(), account.getBalance()));
        }
    }
}
