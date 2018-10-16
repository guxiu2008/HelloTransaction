package com.guxiu.transation.jdbc;

import com.guxiu.transation.dao.AccountDao;
import com.guxiu.transation.domain.Account;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Package: com.guxiu.transation.jdbc
 * DESCRIPTION:
 *
 * @author guxiu2008
 * @create 2018-10-11 21:52
 **/
public class MethodUpdateTestApp {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("update/applicationContext.xml");
        AccountDao accountDao = (AccountDao) applicationContext.getBean("accountDao");
        Account account = new Account();
        account.setId(1);
        account.setUsername("guxiu");
        account.setBalance(5.0);

        int num = accountDao.addAccount(account);
        if (num > 0) {
            System.out.println(String.format("成功插入了[%s]条记录!", num));
        } else {
            System.out.println("数据插入失败!");
        }
    }
}
