package code.warehouse.configuration.mybatis;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * mybatis自定义类型转换器
 * package code.warehouse.weichat.mybatis
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-04-14 10:41
 **/
public class BooleanTypeHandler implements TypeHandler {

    /**
     * 用于定义在Mybatis设置参数时该如何把Java类型的参数转换为对应的数据库类型
     *
     * @param preparedStatement
     *         当前的PreparedStatement对象
     * @param i
     *         当前参数的位置
     * @param o
     *         当前参数的Java对象
     * @param jdbcType
     *         当前参数的数据库类型
     *
     * @throws SQLException
     */
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, Object o, JdbcType jdbcType) throws SQLException {
        if (o.equals(Boolean.TRUE)) {
            preparedStatement.setInt(i, 1);
        } else {
            preparedStatement.setInt(i, 2);
        }
    }

    /**
     * 用于在Mybatis获取数据结果集时如何把数据库类型转换为对应的Java类型
     *
     * @param resultSet
     *         当前的结果集
     * @param s
     *         当前的字段名称
     *
     * @return 转换后的Java对象
     *
     * @throws SQLException
     */
    @Override
    public Object getResult(ResultSet resultSet, String s) throws SQLException {
        return tranferType(resultSet.getInt(s));
    }


    /**
     * 用于在Mybatis通过字段位置获取字段数据时把数据库类型转换为对应的Java类型
     *
     * @param resultSet
     *         当前的结果集
     * @param i
     *         当前字段的位置
     *
     * @return 转换后的Java对象
     *
     * @throws SQLException
     */
    @Override
    public Object getResult(ResultSet resultSet, int i) throws SQLException {
        return tranferType(resultSet.getInt(i));
    }


    /**
     * 用于Mybatis在调用存储过程后把数据库类型的数据转换为对应的Java类型
     *
     * @param callableStatement
     *         当前的CallableStatement执行后的CallableStatement
     * @param i
     *         当前输出参数的位置
     *
     * @return
     *
     * @throws SQLException
     */
    @Override
    public Object getResult(CallableStatement callableStatement, int i) throws SQLException {
        return tranferType(callableStatement.getInt(i));
    }


    private Boolean tranferType(int s) {
        if (s == 1) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
