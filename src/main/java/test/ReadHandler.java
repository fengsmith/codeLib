package test;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * @author motianlong
 * @date 2018年8月25日 下午10:20:50
 * @jdk 1.8
 */
public class ReadHandler {
    public void readHandler(SelectionKey selectionKey) {
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        ByteBuffer buffer=ByteBuffer.allocate(64);
        StringBuilder stringBuilder=new StringBuilder();
        try {
            //循环读取客户端传过来的数据
            while(channel.read(buffer)!=-1) {
                buffer.flip();
                stringBuilder.append(new String(buffer.array(), 0, buffer.limit(), "UTF-8"));
                buffer.clear();
            }
            System.out.println(stringBuilder.toString());
            //组织服务发送给客户端的数据
            String re=new String("我是服务器，我已经收到你的消息了！");
            buffer.put(re.getBytes("UTF-8"));
            //将buffer设置为写模式
            buffer.flip();
            //写入SocketChannel通道
            while(buffer.hasRemaining()) {
                channel.write(buffer);
            }
            //关闭服务端的写出通道，此时服务端会发送FIN到客户端，客户端收到后，会返回ACK确认字符
            channel.socket().shutdownOutput();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                channel.close();
                channel.socket().close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}