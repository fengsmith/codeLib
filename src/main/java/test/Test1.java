package test;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * @Author:shifengqiang
 * @Date:2020/4/27 2:54 下午
 */
public class Test1 {
    public static void main(String[] args) {
//        Optional<String> optional = Optional.of("abc");
//        User user = new User();
//        Consumer<String> consumer = user::setUserName;
//        optional.ifPresent(consumer);
//        System.out.println("");
        System.out.println(20 % 15);
    }

    public static class User {
        private String userName;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }

}
