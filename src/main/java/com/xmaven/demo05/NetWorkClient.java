package com.xmaven.demo05;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Ambition
 * @Description TODO NIO网络编程 客户端
 * @Date: 2022/1/14 5:12 下午
 * @Version 1.0
 */
public class NetWorkClient {
    
    
    /**
     * BIO: 同步阻塞  NIO：同步非阻塞(并发支持高)
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        // 1.创建对象
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999));
        // 2.创建缓冲区数组
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 3.设置数据，往缓冲区中写入数据
        byteBuffer.put("哈哈哈哈".getBytes(StandardCharsets.UTF_8));
        // 4.切换成读模式 这个很重要，put之后position为当前插入元素的len，我们需要切换成读模式就是说position置为0
        byteBuffer.flip();
        // 5.输出数据
        socketChannel.write(byteBuffer);
        // 6.关流
        socketChannel.close();
    }
    
    /**
     * IO网络编程 进行对比使用
     *
     * @throws IOException
     */
    public static void IONetWorkClient() throws IOException {
        Socket socket = new Socket("127.0.0.1", 9999);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("你好呀".getBytes());
        outputStream.close();
        socket.close();
    }
    
}
