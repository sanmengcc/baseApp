//package com.base.core.mybatis;
//
//import lombok.extern.slf4j.Slf4j;
//import net.sf.jsqlparser.JSQLParserException;
//import net.sf.jsqlparser.expression.Expression;
//import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
//import net.sf.jsqlparser.expression.operators.relational.ItemsList;
//import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
//import net.sf.jsqlparser.parser.CCJSqlParserUtil;
//import net.sf.jsqlparser.schema.Column;
//import net.sf.jsqlparser.schema.Table;
//import net.sf.jsqlparser.statement.Statement;
//import net.sf.jsqlparser.statement.insert.Insert;
//import net.sf.jsqlparser.statement.select.*;
//import org.apache.ibatis.executor.statement.StatementHandler;
//import org.apache.ibatis.mapping.BoundSql;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.mapping.SqlCommandType;
//import org.apache.ibatis.plugin.*;
//import org.apache.ibatis.reflection.MetaObject;
//import org.apache.ibatis.reflection.SystemMetaObject;
//
//import java.sql.Connection;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Properties;
//
///**
// * 多租户拦截器
// */
//@Intercepts({@Signature(
//        type = StatementHandler.class,
//        method = "prepare",
//        args = {
//                Connection.class,
//                Integer.class
//        }
//)})
//@Slf4j
//public class TenantInterceptor implements Interceptor {
//
//
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
//        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
//
//        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
//
//        final Object[] args = invocation.getArgs();
//        Object parameter = args[1];
//        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
//
//        String processSql = boundSql.getSql();
//
//        BoundSql boundSq2l = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
//
//        // 查询语句
//        if (SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
//        }
//        // 删除语句
//        if (SqlCommandType.DELETE.equals(mappedStatement.getSqlCommandType())) {
//
//        }
//        // 更新语句
//        if (SqlCommandType.UPDATE.equals(mappedStatement.getSqlCommandType())) {
//
//        }
//        // 插入语句
//        if (SqlCommandType.INSERT.equals(mappedStatement.getSqlCommandType())) {
//            processInsert(boundSq2l.getSql());
//
//        }
//
////        String sql = boundSql.getSql();
////        log.info("之前sql语句:{}", sql);
////        // 判断是否符合需要增加区分代理商查询条件
////        sql = this.ifAgentQuery(sql);
////        log.info("代理商查询sql语句:{}", sql);
////        // 最终将修改好的sql语句设置回去执行
////        metaStatementHandler.setValue("delegate.boundSql.sql", sql);
//        return invocation.proceed();
//    }
//
//    public void processInsert(String processSql) throws JSQLParserException {
//        Statement statement = CCJSqlParserUtil.parse(processSql);
//        Insert insertStatement = (Insert) statement;
//        String tableName = insertStatement.getTable().getName();
//
//        // 去除反引号
//        tableName = tableName.replace("`", "");
//
//
//        List<Column> columns = insertStatement.getColumns();
//        columns.add(new Column("oem_code"));
//        insertStatement.setColumns(columns);
//
//        //单个插入 或者 批量插入
//        ItemsList itemsList = insertStatement.getItemsList();
//        if (itemsList instanceof ExpressionList){
//            ExpressionList expressionList = (ExpressionList) itemsList;
//            List<Expression> list = new ArrayList<>();
//            list.addAll(expressionList.getExpressions());
//            list.add(CCJSqlParserUtil.parseExpression("'001'"));
//            expressionList.setExpressions(list);
//            insertStatement.setItemsList(expressionList);
//        } else if (itemsList instanceof MultiExpressionList){
//            MultiExpressionList multiExpressionList = (MultiExpressionList) itemsList;
//            List<ExpressionList> expressionListList = multiExpressionList.getExpressionLists();
//            for (ExpressionList expressionList : expressionListList) {
//                List<Expression> list = new ArrayList<>();
//                list.addAll(expressionList.getExpressions());
//                list.add(CCJSqlParserUtil.parseExpression("'001'"));
//                expressionList.setExpressions(list);
//            }
//            insertStatement.setItemsList(multiExpressionList);
//        }
//
//        System.out.println(insertStatement.toString());
//    }
//
//    @Override
//    public Object plugin(Object target) {
//        // 返回拦截器本身, 还是返回目标本身
//        return target instanceof StatementHandler ? Plugin.wrap(target, this) : target;
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//
//    }
//
//    /**
//     * 判断是否需要进行拼接代理商查询条件
//     * 有以下几种情况: (0 运营商, 不进行拼接查询全部) , (1 代理商并且是总仓管理员, 仅拼接代理商字段查询)
//     * (2 代理商并且是分仓管理员, 需拼接代理商字段与仓库字段查询) , (3 代理商并且是总仓管理员, 需拼接代理商字段查询)
//     *
//     * @param sql
//     * @return java.lang.String
//     * @author: ZhiHao
//     * @date: 2020/12/17
//     */
//    private String ifAgentQuery(String sql) throws JSQLParserException {
//        // sql解析器解析查询语句(也只有查询能进来)
//        Select selectStatement = (Select) CCJSqlParserUtil.parse(sql);
//        // 不是单表与多表直接查询
//        if (!(selectStatement.getSelectBody() instanceof PlainSelect)) {
//            return sql;
//        }
//        PlainSelect selectBody = (PlainSelect) selectStatement.getSelectBody();
//        // 仅对枚举符合表, 并没有代理商字段的进行拼接sql
//        Table table = this.ifConform(selectBody);
//
//        sql = selectStatement.toString();
//        return sql;
//    }
//
//    /**
//     * 仅对dms_battery表查询并没有代理商字段的进行拼接sql,
//     * 防止拦截器开启时, 兼容以后自定义查询语句包含查询字段的进行了拼接出错
//     *
//     * @param selectBody
//     * @return boolean
//     * @author: ZhiHao
//     * @date: 2020/12/18
//     */
//    private Table ifConform(PlainSelect selectBody) {
//        // 判断from后面是否符合需要拼接的表名
//        FromItem fromItem = selectBody.getFromItem();
//        if (fromItem instanceof Table) {
//            Table table = (Table) fromItem;
////            if (TenantTable.tableMap.get(table.getName()) != null) {
////                return table;
////            }
//        }
//        // 上面from后面不满足则判断多表情况是否包含
//        List<Join> joins = selectBody.getJoins();
//        Table table = null;
//        if (joins != null && joins.size() > 0) {
//            joins.forEach(join -> {
//                FromItem item = join.getRightItem();
//                FromItem rightItem = join.getRightItem();
//                System.out.println("-");
//            });
//        }
//        return table != null ? table : null;
//    }
//
//
//}
