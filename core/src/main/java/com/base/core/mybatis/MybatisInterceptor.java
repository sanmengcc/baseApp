package com.base.core.mybatis;


import java.sql.SQLException;
import java.text.DateFormat;
import java.util.*;
import java.util.regex.Matcher;

import com.base.core.annotation.MybatisLog;
import com.base.core.entity.dto.SqlLogDTO;
import com.base.core.service.LogService;
import com.base.util.SpringBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.core.annotation.AnnotationUtils;


/**
 * MyBatis拦截器
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class,
                Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class,
                Object.class, RowBounds.class, ResultHandler.class})})
@SuppressWarnings({"unchecked", "rawtypes"})
@Slf4j
public class MybatisInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation)
            throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        String sqlId = mappedStatement.getId();
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        Configuration configuration = mappedStatement.getConfiguration();
        String sql = getSql(configuration, boundSql);

        // INSERT SQL
        if (SqlCommandType.INSERT.equals(mappedStatement.getSqlCommandType())) {;
            Optional.ofNullable(MyBatisUtil.processInsert(mappedStatement.getBoundSql(parameter).getSql()))
                    .ifPresent(newSQL -> resetSql2Invocation(invocation, newSQL));
        }
        // UPDATE SQL
        if (SqlCommandType.UPDATE.equals(mappedStatement.getSqlCommandType())) {;
            Optional.ofNullable(MyBatisUtil.processUpdate(mappedStatement.getBoundSql(parameter).getSql()))
                    .ifPresent(newSQL -> resetSql2Invocation(invocation, newSQL));
        }
        // DELETE SQL
        if (SqlCommandType.DELETE.equals(mappedStatement.getSqlCommandType())) {;
            Optional.ofNullable(MyBatisUtil.processDelete(mappedStatement.getBoundSql(parameter).getSql()))
                    .ifPresent(newSQL -> resetSql2Invocation(invocation, newSQL));
        }
        // SELECT SQL
        if (SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {;
            Optional.ofNullable(MyBatisUtil.processSelect(mappedStatement.getBoundSql(parameter).getSql()))
                    .ifPresent(newSQL -> resetSql2Invocation(invocation, newSQL));
        }

        Long startTime = System.currentTimeMillis();
        Object proceed = invocation.proceed();
        Long useTime = System.currentTimeMillis() - startTime;
        Class<?> classType = Class.forName(mappedStatement.getId().substring(0, mappedStatement.getId().lastIndexOf(".")));
        MybatisLog mybatisLog = AnnotationUtils.findAnnotation(classType, MybatisLog.class);
        if (Objects.nonNull(mybatisLog) && !mybatisLog.log()) {
            return proceed;
        }
        SqlLogDTO logDTO = new SqlLogDTO();
        logDTO.setSql(sql);
        logDTO.setMapper(sqlId);
        logDTO.setUseTime(useTime);
        logDTO.setGmtCreate(new Date());
        LogService logService = SpringBeanUtil.getBean(LogService.class);
        logService.saveSQL(logDTO);
        return proceed;
    }


    /**
     * 包装sql后，重置到invocation中
     * @param invocation
     * @param sql
     * @throws SQLException
     */
    public static void resetSql2Invocation(Invocation invocation, String sql){
        log.error("newSQL:{}", sql);
        final Object[] args = invocation.getArgs();
        MappedStatement statement = (MappedStatement) args[0];
        Object parameterObject = args[1];
        BoundSql boundSql = statement.getBoundSql(parameterObject);
        MappedStatement newStatement = newMappedStatement(statement, new BoundSqlSqlSource(boundSql));
        MetaObject msObject =  MetaObject.forObject(newStatement, new DefaultObjectFactory(), new DefaultObjectWrapperFactory(),new DefaultReflectorFactory());
        msObject.setValue("sqlSource.boundSql.sql", sql);
        args[0] = newStatement;
    }

    private static MappedStatement newMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder =
                new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
            StringBuilder keyProperties = new StringBuilder();
            for (String keyProperty : ms.getKeyProperties()) {
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }


    public static String getSql(Configuration configuration, BoundSql boundSql) {
        String sql = showSql(configuration, boundSql);
        StringBuilder str = new StringBuilder(100);
        str.append(sql);
        return str.toString();
    }

    private static String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT,
                    DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }

        }
        return value;
    }

    public static String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (CollectionUtils.isNotEmpty(parameterMappings) && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?",
                        Matcher.quoteReplacement(getParameterValue(parameterObject)));

            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?",
                                Matcher.quoteReplacement(getParameterValue(obj)));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?",
                                Matcher.quoteReplacement(getParameterValue(obj)));
                    } else {
                        sql = sql.replaceFirst("\\?", "缺失");
                    }
                }
            }
        }
        return sql;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

}

//    定义一个内部辅助类，作用是包装sq
class BoundSqlSqlSource implements SqlSource {
    private BoundSql boundSql;
    public BoundSqlSqlSource(BoundSql boundSql) {
        this.boundSql = boundSql;
    }
    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        return boundSql;
    }
}