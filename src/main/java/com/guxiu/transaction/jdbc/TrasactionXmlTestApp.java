package com.guxiu.transaction.jdbc;

import com.guxiu.transaction.dao.transaction.AccountTransferDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Package: com.guxiu.transaction.jdbc
 * DESCRIPTION:
 *
 * @author guxiu2008
 * @create 2019-02-02 22:44
 **/
public class TrasactionXmlTestApp {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("transactionXml/applicationContext.xml");
        // 获取AccountDao实例
        AccountTransferDao accountTransationDao = (AccountTransferDao) applicationContext.getBean("accountTransationDao");
        // 调用实例中的转账方法
        accountTransationDao.transfer("Eva", "guxiu", 100.0);
        // 输出提示信息
        System.out.println("转账成功");
    }
}
