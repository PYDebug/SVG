package edu.tongji.webgis.config;

import edu.tongji.webgis.dao.annotation.Mapper;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@PropertySource(value = { "/WEB-INF/jdbc.properties" })
@ComponentScan("edu.tongji.webgis")
public class AppConfig implements TransactionManagementConfigurer {

//    private static final Logger logger = Logger.getLogger(AppConfig.class);

    @Autowired
    Environment env;

	@Bean
	public DataSource dataSource() {
//		DriverManagerDataSource ds = new DriverManagerDataSource(
//                env.getProperty("jdbc.url"), env.getProperty("jdbc.user"),
//                env.getProperty("jdbc.pass"));
//		ds.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        DriverManagerDataSource ds = new DriverManagerDataSource(
                "jdbc:mysql://localhost:3306/svg?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf-8", "root",
                "1234");
        ds.setDriverClassName("com.mysql.jdbc.Driver");
		return ds;
	}

	@Bean(name = "sessionFactory")
    public SqlSessionFactoryBean sessionFactory() {
//        logger.info("sessionFactory");
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource());
        return bean;
	}

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
//        logger.info("mapperScanner");
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage("edu.tongji.webgis.dao.mapper");
        configurer.setSqlSessionFactoryBeanName("sessionFactory");
        return configurer;
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(){
//        logger.info("DataSourceTransactionManager");
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource());
        return dataSourceTransactionManager;
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return dataSourceTransactionManager();
    }
//
//	public PlatformTransactionManager annotationDrivenTransactionManager() {
//		return txManager();
//	}
}
