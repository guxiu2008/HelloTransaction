package com.guxiu.transaction.dao.transaction;

/**
 * Package: com.guxiu.transaction.dao.transaction
 * DESCRIPTION:
 *
 * @author guxiu2008
 * @create 2018-10-17 22:11
 **/
public interface AccountTransferDao {
    public void transfer(String outUser, String inUser, Double money);
}
