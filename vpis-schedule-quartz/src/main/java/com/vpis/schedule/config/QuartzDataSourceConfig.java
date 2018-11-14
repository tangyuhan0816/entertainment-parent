package com.vpis.schedule.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author Forest Wang
 * @package cn.magicwindow.meta.config
 * @class DataSourceConfig
 * @email forest@magicwindow.cn
 * @date 24/01/2017 15:11
 * @description
 */
@MapperScan(basePackages = QuartzDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "quartzSqlSessionFactory")
@Configuration
public class QuartzDataSourceConfig {

    private static final Logger logger = LoggerFactory.getLogger(QuartzDataSourceConfig.class);

    static final String PACKAGE = "com.vpis.schedule.dao.quartz";

    static final String MAPPER_LOCATION = "classpath:mapper/quartz/*.xml";

    @Value("${spring.quartz-datasource.url}")
    private String url;

    @Value("${spring.quartz-datasource.username}")
    private String username;

    @Value("${spring.quartz-datasource.password}")
    private String password;

    @Value("com.mysql.jdbc.Driver")
    private String driverClassName;

    @Value("5")
    private int initialSize;

    @Value("5")
    private int minIdle;

    @Value("20")
    private int maxActive;

    @Value("60000")
    private int maxWait;

    /**
     * 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
     */
    @Value("60000")
    private int timeBetweenEvictionRunsMillis;
    /**
     * 配置一个连接在池中最小生存的时间，单位是毫秒
     */
    @Value("300000")
    private int minEvictableIdleTimeMillis;

    @Value("SELECT 1 FROM DUAL")
    private String validationQuery;

    @Value("true")
    private boolean testWhileIdle;

    @Value("false")
    private boolean testOnBorrow;

    @Value("false")
    private boolean testOnReturn;

    /**
     * 打开PSCache，并且指定每个连接上PSCache的大小
     */
    @Value("true")
    private boolean poolPreparedStatements;

    @Value("20")
    private int maxPoolPreparedStatementPerConnectionSize;
    /**
     * 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
     */
    @Value("stat,wall,log4j")
    private String filters;
    /**
     * 通过connectProperties属性来打开mergeSql功能；慢SQL记录
     */
    @Value("druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500")
    private String connectionProperties;

    @Bean(name = "quartzDataSource")
    @Qualifier("quartzDataSource")
    public DataSource quartzDataSource() {
        return getDataSourceConfig(username, password, url);
    }

    private DataSource getDataSourceConfig(String username, String password, String url) {
        logger.info("quartz数据源启动中：{}",url);
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(url);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        logger.info(username);
        //configuration
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            logger.error("error ", e);
        }
        datasource.setConnectionProperties(connectionProperties);

        return datasource;
    }

    @Bean(name = "quartzTransactionManager")
    public DataSourceTransactionManager masterTransactionManager() {
        return new DataSourceTransactionManager(quartzDataSource());
    }

    @Bean(name = "quartzSqlSessionFactory")
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("quartzDataSource") DataSource masterDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(masterDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(QuartzDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

}
