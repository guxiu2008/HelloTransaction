主要用于学习回顾SpringJDBC和Spring事务控制

SpringJDBC：
一、简述
    SpringJDBC一般不用，这里只做简单介绍
    Spring JDBC的核心类是JdbcTemplate类，此类继承于JdbcAccessor，并且实现JdbcOperations的接口
    JDBC的基本连接信息（driver、url、user、pwd）注入到DriverManagerDataSource中，然后再把DriverManagerDataSource对象注入到JdbcTemplate对象的dataSource属性，即可连接数据库
二、开发
    1、开发前需要引入spring-jdbc依赖
    2、JdbcTemplate类相关操作数据库的方法
        1）execute()    //最简单的方法,单纯执行一条字符串SQL，一般用于执行DDL语句
            execute(String sql)
        2）update()     //一般用于执行增删改的DML语句
            int update(String sql)    最简单的update，传入sql语句后直接执行，返回影响行数
            int update(PreparedStatementCreator psc)    执行预编译好的sql，返回影响行数
            int update(String sql, PrepareStatementSetter pss)    通过PrepareStatementSetter设置SQL语句中的参数，返回影响行数
            int update(String sql, Object ..args)    通过非食用Object..设置SQL语句中的参数，要求参数不能为NULL，返回影响行数
        3）query()    //一般用于查询语句的执行
            List query(String sql, RowMapper rowMapper)    执行String类型的SQL语句，并通过RowMapper返回一个List类型的结果
            List query(String sql, PreparedStatementSetter pss, RowMapper rowMapper)    根据String类型的SQL语句创建PreparedStatement对象，通过RowMapper将结果返回到List中
            List query(String sql, Object[] args, RowMapper rowMapper)    使用Object[]的值来设置SQL语句中的参数值，采用RowMapper回调方法可以直接返回List类型的数据
            queryForObjdect(String sql, RowMapper rowMapper, Object... args)    将args参数绑定到SQL语句中，并通过RowMapper返回一个Object类型的单行记录
            queryForList(String sql, Object[] args, class<T> elementType)    该方法可以返回多行数据的结果，但必须是返回列表，elementType参数返回的是List元素的类型

事务控制（Transation）
一、事务管理的核心接口
    事务管理主要有三个接口类，基本所有事务控制都是围绕这三个接口类进行实现控制
    1、PlatformTransactionManager
        1）Spring提供的平台事务管理器，主要用于管理事务，该接口提供了三个事务操作的方法，具体如下：
            TransationStatus getTransaction(TransationDefintion defintion：用于获取事务状态信息
            void commit(TransationStatus status)：用于提交事务
            void rollback(TransationStatus status)：用于回滚事务
            在上述的3个方法中，getTransation方法会根据TransationDefinition参数返回一个TransationStatus对象，该对象就表示一个事务，它被关联在当前执行的线程上。
        2）PlatformTransactionManager一般有三个实现类，分别如下：
            DataSourceTransactionManager：用于配置JDBC数据源的事务管理器
            HibernateTransactionManager：用于配置Hibername的事务管理器
            JtaTransactionManager：用于配置全局事务管理器（分布式事务）
    2、TransactionDefinition
        1）该接口是事务定义（描述）的对象，里面定义了事务规则，并提供了事务相关信息的方法
            String getName()：获取事务对象名称
            int getIsolationLevel()：获取事务的隔离级别
            int getPropagationBehavoior()：获取事务的传播行为
            int getTimeout()：获取事务的超时时间
            boolean isReadOnly()：获取事务是否只读
        2）事务的隔离级别
            --ISOLATION_READ_UNCOMMITTED
                这是事务最低的隔离级别，它充许别外一个事务可以看到这个事务未提交的数据。
                这种隔离级别会产生脏读，不可重复读和幻像读。
            --ISOLATION_READ_COMMITTED
                保证一个事务修改的数据提交后才能被另外一个事务读取。另外一个事务不能读取该事务未提交的数据。
                这种事务隔离级别可以避免脏读出现，但是可能会出现不可重复读和幻像读。
            --ISOLATION_REPEATABLE_READ
                这种事务隔离级别可以防止脏读，不可重复读。但是可能出现幻像读。
                它除了保证一个事务不能读取另一个事务未提交的数据外，还保证了避免下面的情况产生(不可重复读)。
            --ISOLATION_SERIALIZABLE
                这是花费最高代价但是最可靠的事务隔离级别。事务被处理为顺序执行。
                除了防止脏读，不可重复读外，还避免了幻像读。
        3）幻读、不可重复读取、脏读
            --幻读：
                理解1：事务1读取记录时事务2增加了记录并提交，事务1再次读取时可以看到事务2新增的记录
                理解2：在一个事务内读取了别的事务插入的数据，导致前后读取不一致(insert)
            --不可重复读取：
                理解1：事务1读取记录时，事务2更新了记录并提交，事务1再次读取时可以看到事务2修改后的记录
                理解2：在一个事务内读取表中的某一行数据,多次读取结果不同.一个事务读取到了另一个事务提交后的数据
            --脏读：
                理解1：事务1更新了记录，但没有提交，事务2读取了更新后的行，然后事务T1回滚，现在T2读取无效:
                理解2：指一个事务读取了一个未提交事务的数据
        4）事务的传播行为
            1）PROPAGATION_REQUIRED – 支持当前事务，如果当前没有事务，就新建一个事务。这是最常见的选择。
            2）PROPAGATION_SUPPORTS – 支持当前事务，如果当前没有事务，就以非事务方式执行。
            3）PROPAGATION_MANDATORY – 支持当前事务，如果当前没有事务，就抛出异常。
            4）PROPAGATION_REQUIRES_NEW – 新建事务，如果当前存在事务，把当前事务挂起。
            5）PROPAGATION_NOT_SUPPORTED – 以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。
            6）PROPAGATION_NEVER – 以非事务方式执行，如果当前存在事务，则抛出异常。
            7）PROPAGATION_NESTED – 如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则进行与PROPAGATION_REQUIRED类似的操作。
            备注：常用的两个事务传播属性是1和4，即PROPAGATION_REQUIRED，PROPAGATION_REQUIRES_NEW
    3、TransactionStatus
        TransactionStatus接口是事务的状态，它描述了某一时间点上事务的状态信息。该接口中包含6个方法，具体如下：
            1）void flush()：刷新事务
            2）boolean hasSavepoint()：获取事务是否完成
            3）boolean isCompleted()：获取事务是否完成
            4）boolean isNewTransaction()：获取是否回滚
            5）boolean isRollbackOnly()：获取是否回滚
            6）void setRollbackOnly()：设置事务回滚
二、事务管理的方式
    1、编程式事务管理
        网上看了例子，传统编程式事务管理，需要在每一个dao类仲增加PlatformTransactionManager对象作为成员，每个更新数据库的方法都需要配合TransactionDefinition和TransactionStatus对象控制提交和回滚，十分复杂，不推荐使用！
        https://www.cnblogs.com/EasonJim/p/6911208.html
    2、声明式事务管理
        通过AOP技术实现的事务管理，其主要思想是将事务管理作为一个“切面”代码单独编写，然后通过AOP技术将事务管理的“切面”代码织入到业务目标类中。
三、基于xml方式的声明式事务
    Spring提供了tx命名空间来配置事务，tx命名空间下提供了<tx:advice>元素来配置事务的通知（增强处理）。当使用<tx:advice>元素配置了事务的增强处理后，就可以通过编写的AOP配置，让Spring自动对目标生成代理。
四、基于Annotation方式的声明式事务
    Spring的声明式事务还可以通过Annotation(注解)的方式来实现，主要做两个事情。
    1、在Spring容器中注册事务注解驱动,其中transactionManager是Spring配置文件中配置好的bean对象
        <tx:annotation-driven transaction-manager="transactionManager"/>
    2、在需要使用事务的Spring Bean类或者Bean类的方法上添加注解@Transactionl
        如果把注解添加在Bean类上，则表示事务的设置对整个Bean类的方法有效
        如果把注解添加在某个方法上，则表示只对对应的方法有效