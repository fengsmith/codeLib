package test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * @Author:shifengqiang
 * @Date:2021/3/12 10:54 上午
 */
public class HttpUrlConnectionTest {
    public static void main(String[] args) {
//        for (int i = 0; i < 25; i++) {
//            new Thread(() -> createConnection()).start();
//        }
        System.out.println(7 % 8);
    }

    private static void createConnection() {
        try {
            URL url = null;
            url = new URL("http://220.181.38.148");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            connection.getInputStream().close();
//            TimeUnit.MINUTES.sleep(1);
//            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
