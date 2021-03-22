package deadlock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

/**
 * @Author:shifengqiang
 * @Date:2021/3/15 4:42 下午
 */
public class Test {
    static Random random = new Random();

    public static void main(String[] args) {
//        try {
//            Connection connection = createConnection();
//            for (int i = 101; i < 1000000; i++) {
//                insert(connection, i + "");
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
        update();
    }

    private static void update() {
        try {
            Connection connection1 = createConnection();
            Connection connection2 = createConnection();

            Thread thread1 = new Thread(() -> {
                int i = 0;
                while (true) {
                    i++;
//                    update(connection1);

                    if (i >= 10000) {
                        System.out.println("#####" + i);
                        break;
                    }
                }
            });
            Thread thread2 = new Thread(() -> {
                int j = 0;
                while (true) {
//                    update(connection2);
                    j++;
                    if (j >= 10000) {

                        System.out.println("#####" + j);
                        break;
                    }
                }

            });
            thread1.start();
            thread2.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insert(Connection connection, String name) {
        try {
            //获取connection对象

            String sql = "insert into test1(name, address) values ('%s', '%s')";
            sql = String.format(sql, name, name + "address");

            //3.准备SQL语句
            PreparedStatement pStatement = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            pStatement.executeUpdate();

            //6.关闭连接
            pStatement.close();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static Connection createConnection() throws ClassNotFoundException, SQLException {
        //1.加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/shfq";

        String user = "root";

        String password = "123456789";
        //2.建立连接
        Connection connections = DriverManager.getConnection(url, user, password);
        //返回连接对象
        return connections;

    }

    //调用它的方法如下：
    public static void update(Connection connection) {
        try {
            //获取connection对象

            String sql = "update test1 set address = '%s' where name='694264' and phone='694264';";
            sql = String.format(sql, System.nanoTime() + " " + random.nextInt());

            //3.准备SQL语句
            PreparedStatement pStatement = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            pStatement.executeUpdate();

            //6.关闭连接
            pStatement.close();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
