import java.sql.*;
import java.util.ArrayList;

public class DbUtil {
    private static Connection con;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;
    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/mygame?userSSL=true&useUnicode=true&characterEncoding=UTF8&serverTimezone=GMT", "root", "123456");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //url   user
        //通过驱动管理获取  连接对象
        return con;
    }
    public static PreparedStatement getPreparedStatement(String sql){
        try {
            preparedStatement=getConnection().prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return preparedStatement;
    }
    public static int getexecuteUpdate(String sql, ArrayList<Object> params){
        int count=0;
        preparedStatement=getPreparedStatement(sql);
        try {
            //绑定  ????   type

            for (int i = 0; i < params.size(); i++) {
                //  preparedStatement.setInt(1,1);
                //int index     1
                //List  index   0
                preparedStatement.setObject(i + 1, params.get(i));
            }
            count = preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;
    }
    public static ResultSet getexecuteQuery(String sql, ArrayList<Object> pramars) {
        getPreparedStatement(sql);
        try {
            //绑定  ????   type
            for (int i = 0; i < pramars.size(); i++) {
                //  preparedStatement.setInt(1,1);
                //int index     1
                //List  index   0
                preparedStatement.setObject(i + 1, pramars.get(i));
            }
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }
    public static void closeAll() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}