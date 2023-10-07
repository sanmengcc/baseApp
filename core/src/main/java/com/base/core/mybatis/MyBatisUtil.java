package com.base.core.mybatis;

import com.base.core.context.CloudManager;
import com.base.util.ValidateHelper;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MyBatisUtil {

    private static List<String> tableNames = new ArrayList<>();
    static {
        tableNames.add("t_host_config");
        tableNames.add("t_role_module");
        tableNames.add("t_oem_info");
    }

    /**
     * 增加oemCode where条件
     * @param alias
     * @param oemCode
     * @return
     */
    public static String addOemWhere(String tableName,String alias, String oemCode) {
        if (tableNames.contains(tableName)) {
            return null;
        }
        StringBuffer whereSql = new StringBuffer();
        //增加sql语句的逻辑部分处理
        if (ValidateHelper.isNotEmptyString(alias)) {
            whereSql.append(alias).append(".oem_code = ").append("'" + oemCode + "'");
        } else {
            whereSql.append("oem_code = ").append("'" + oemCode + "'");
        }
        return whereSql.toString();
    }

    /**
     * 处理SELECT查询语句 添加租户字段 处理多表联查
     * @param processSql
     * @return
     * @throws JSQLParserException
     */
    public static String processSelect(String processSql) throws JSQLParserException {
        Statement statement = CCJSqlParserUtil.parse(processSql);
        Select selectStatement = (Select) statement;
        PlainSelect plain = (PlainSelect) selectStatement.getSelectBody();
        FromItem fromItem = plain.getFromItem();

        String tableName = fromItem.toString();

        // 去除反引号
        tableName = tableName.replace("`", "");
        if (tableNames.contains(tableName)) {
            return null;
        }

        String oemCode = CloudManager.getInstance().getOemCode();
        List<Join> startJoins = plain.getJoins();
        List<String> wheres = new ArrayList<>();
        String whereSQL = addOemWhere(fromItem.toString(), Optional.ofNullable(fromItem.getAlias()).map(Alias::getName).orElse(null), oemCode);
        if (ValidateHelper.isNotEmptyString(whereSQL)) {
            wheres.add(whereSQL);
        }

        if (ValidateHelper.isNotEmptyCollection(startJoins)) {
            for (Join join : startJoins) {
                String joinWhereSQL = addOemWhere(join.getRightItem().toString(), Optional.ofNullable(join.getRightItem().getAlias()).map(Alias::getName).orElse(null), oemCode);
                if (ValidateHelper.isNotEmptyString(joinWhereSQL)) {
                    wheres.add(joinWhereSQL);
                }
            }
        }

        Expression where = plain.getWhere();
        if (ValidateHelper.isNotEmptyCollection(wheres)) {
            if (where == null) {
                Expression expression = CCJSqlParserUtil
                        .parseCondExpression(" 1 = 1 ");
                plain.setWhere(expression);
            }

            StringBuffer newWhere = new StringBuffer(plain.getWhere().toString());

            wheres.forEach(w->{
                newWhere.append(" and " + w.toString());
            });

            Expression expression = CCJSqlParserUtil
                    .parseCondExpression(newWhere.toString());
            plain.setWhere(expression);
        }

        return selectStatement.toString();
    }

    /**
     * 处理DELETE删除语句 添加租户字段
     * @param processSql
     * @return
     * @throws JSQLParserException
     */
    public static String processDelete(String processSql) throws JSQLParserException {
        Statement statement = CCJSqlParserUtil.parse(processSql);
        Delete deleteStatement = (Delete) statement;
        String oemCode = CloudManager.getInstance().getOemCode();

        Table table = deleteStatement.getTable();

        String tableName = table.getName();

        // 去除反引号
        tableName = tableName.replace("`", "");
        if (tableNames.contains(tableName)) {
            return null;
        }
        List<String> wheres = new ArrayList<>();
        String whereSQL = addOemWhere(table.getName(), Optional.ofNullable(table.getAlias()).map(Alias::getName).orElse(null), oemCode);
        if (ValidateHelper.isNotEmptyString(whereSQL)) {
            wheres.add(whereSQL);
        }


        Expression where = deleteStatement.getWhere();
        if (ValidateHelper.isNotEmptyCollection(wheres)) {
            if (where == null) {
                Expression expression = CCJSqlParserUtil
                        .parseCondExpression(" 1 = 1 ");
                deleteStatement.setWhere(expression);
            }

            StringBuffer newWhere = new StringBuffer(deleteStatement.getWhere().toString());

            wheres.forEach(w->{
                newWhere.append(" and " + w.toString());
            });

            Expression expression = CCJSqlParserUtil
                    .parseCondExpression(newWhere.toString());
            deleteStatement.setWhere(expression);
        }

        return deleteStatement.toString();
    }


    /**
     * 处理update更新语句 添加租户字段 处理联表更新
     * @param processSql
     * @return
     * @throws JSQLParserException
     */
    public static String processUpdate(String processSql) throws JSQLParserException {
        Statement statement = CCJSqlParserUtil.parse(processSql);
        Update updateStatement = (Update) statement;
        String oemCode = CloudManager.getInstance().getOemCode();
        Table table = updateStatement.getTable();
        String tableName = table.getName();

        // 去除反引号
        tableName = tableName.replace("`", "");
        if (tableNames.contains(tableName)) {
            return null;
        }
        List<Join> startJoins = updateStatement.getStartJoins();
        List<String> wheres = new ArrayList<>();
        String whereSQL = addOemWhere(table.getName(), Optional.ofNullable(table.getAlias()).map(Alias::getName).orElse(null), oemCode);
        if (ValidateHelper.isNotEmptyString(whereSQL)) {
            wheres.add(whereSQL);
        }

        if (ValidateHelper.isNotEmptyCollection(startJoins)) {
            for (Join join : startJoins) {
                String joinWhereSQL = addOemWhere(join.getRightItem().toString(), Optional.ofNullable(join.getRightItem().getAlias()).map(Alias::getName).orElse(null), oemCode);
                if (ValidateHelper.isNotEmptyString(joinWhereSQL)) {
                    wheres.add(joinWhereSQL);
                }
            }
        }

        Expression where = updateStatement.getWhere();
        if (ValidateHelper.isNotEmptyCollection(wheres)) {
            if (where == null) {
                Expression expression = CCJSqlParserUtil
                        .parseCondExpression(" 1 = 1 ");
                updateStatement.setWhere(expression);
            }

            StringBuffer newWhere = new StringBuffer(updateStatement.getWhere().toString());

            wheres.forEach(w->{
                newWhere.append(" and " + w.toString());
            });

            Expression expression = CCJSqlParserUtil
                    .parseCondExpression(newWhere.toString());
            updateStatement.setWhere(expression);
        }

        return updateStatement.toString();
    }

    /**
     * 处理插入SQL 添加租户字段 处理批量插入
     * @param processSql
     * @return
     * @throws JSQLParserException
     */
    public static String processInsert(String processSql) throws JSQLParserException {
        Statement statement = CCJSqlParserUtil.parse(processSql);
        Insert insertStatement = (Insert) statement;
        String tableName = insertStatement.getTable().getName();

        // 去除反引号
        tableName = tableName.replace("`", "");
        if (tableNames.contains(tableName)) {
            return null;
        }

        List<Column> columns = insertStatement.getColumns();
        columns.add(new Column("oem_code"));
        insertStatement.setColumns(columns);
        String oemCode = CloudManager.getInstance().getOemCode();
        //单个插入 或者 批量插入
        ItemsList itemsList = insertStatement.getItemsList();
        if (itemsList instanceof ExpressionList){
            ExpressionList expressionList = (ExpressionList) itemsList;
            List<Expression> list = new ArrayList<>();
            list.addAll(expressionList.getExpressions());
            list.add(CCJSqlParserUtil.parseExpression("'" + oemCode + "'"));
            expressionList.setExpressions(list);
            insertStatement.setItemsList(expressionList);
        } else if (itemsList instanceof MultiExpressionList){
            MultiExpressionList multiExpressionList = (MultiExpressionList) itemsList;
            List<ExpressionList> expressionListList = multiExpressionList.getExpressionLists();
            for (ExpressionList expressionList : expressionListList) {
                List<Expression> list = new ArrayList<>();
                list.addAll(expressionList.getExpressions());
                list.add(CCJSqlParserUtil.parseExpression("'" + oemCode + "'"));
                expressionList.setExpressions(list);
            }
            insertStatement.setItemsList(multiExpressionList);
        }
        return insertStatement.toString();
    }
}
