package com.base.core.mybatis;

import jakarta.annotation.PostConstruct;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class MybatisConfig {

    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;

    @PostConstruct
    public void addInterceptor() {
        MybatisInterceptor interceptor = new MybatisInterceptor();
//        TenantInterceptor tenantInterceptor = new TenantInterceptor();
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
//            sqlSessionFactory.getConfiguration().addInterceptor(tenantInterceptor);
        }
    }
}
