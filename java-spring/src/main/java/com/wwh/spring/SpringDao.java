package com.wwh.spring;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import java.beans.PropertyVetoException;
import java.io.IOException;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@Configuration
public class SpringDao implements EnvironmentAware {

    @Value("${jdbc.driver}")
    private String driver;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.user}")
    public String user;

    @Value("${jdbc.password}")
    public String password;

    @Autowired
    private Environment env;

    @Bean
    public ComboPooledDataSource comboPooledDataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(env.getProperty("jdbc.driver"));
        dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        dataSource.setUser(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.password"));
        return dataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() throws PropertyVetoException, IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setConfigLocation(resolver.getResource("mybatis-config.xml"));
        sqlSessionFactoryBean.setDataSource(comboPooledDataSource());
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.nf147.entity");
        return sqlSessionFactoryBean;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.wwh.dao");
        return mapperScannerConfigurer;
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager() throws PropertyVetoException {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(comboPooledDataSource());
        return dataSourceTransactionManager;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }
}
