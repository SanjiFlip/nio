package com.xmaven.demo06;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author: Ambition
 * @Description TODO
 * @Date: 2022/1/14 5:40 下午
 * @Version 1.0
 */
public class AcceptProblem2 {
    
    public static void main(String[] args) throws IOException {
        // 1.创建服务器端对象，监听对应的端口
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 2.监听对应的端口
        serverSocketChannel.bind(new InetSocketAddress(9999));
        // 设置成非阻塞连接
        serverSocketChannel.configureBlocking(false);
        System.out.println("等待客户端发送过来>>>>>>>");
        
        System.out.println("客户端数据接受中---------");
        while (true) {
            // 3.连接客户端 目前是阻塞的
            SocketChannel channel = serverSocketChannel.accept();
            if (channel != null) {
                // 4.读取数据
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                // 读取到的字节长度
                int len = channel.read(buffer);
                System.out.println(new String(buffer.array(), 0, len));
                break;
            } else {
                // 没有连接到服务器的客户端
                System.out.println("做一些别的事情...");
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        
        
    }
    
}
