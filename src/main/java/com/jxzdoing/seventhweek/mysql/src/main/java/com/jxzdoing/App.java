package com.jxzdoing;

import java.sql.*;

/**
 * Hello world!
 *
 */
public class App {

    private static int count = 1000000;

    public static void main( String[] args ) throws ClassNotFoundException, SQLException {
        long start = System.currentTimeMillis();
        String url = "jdbc:mysql://localhost:3306/em?useSSL=false&useUnicode=true&characterEncoding=UTF8";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url,"root","123456");
        connection.setAutoCommit(false);
        String sql = "insert into em.order(user_id,product_id,amount,total_price,create_time,update_time,status) " +
                "values(?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 1; i <=count ; i++) {
            preparedStatement.setInt(1,i);
            preparedStatement.setInt(2,i);
            preparedStatement.setInt(3,i);
            preparedStatement.setDouble(4,i);
            preparedStatement.setDate(5,new Date(19929333L));
            preparedStatement.setDate(6,new Date(19929333L));
            preparedStatement.setInt(7,1);
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        connection.commit();
        preparedStatement.close();
        connection.close();
        System.out.println("耗时:"+(System.currentTimeMillis() - start)/1000+"seconds");
    }
}
