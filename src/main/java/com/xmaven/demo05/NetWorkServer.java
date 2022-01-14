package com.xmaven.demo05;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author: Ambition
 * @Description TODO NIO网络编程 服务端
 * @Date: 2022/1/14 5:12 下午
 * @Version 1.0
 */
public class NetWorkServer {
    
    public static void main(String[] args) throws IOException {
        // 1.创建服务器端对象，监听对应的端口
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 2.监听对应的端口
        serverSocketChannel.bind(new InetSocketAddress(9999));
        // 3.连接客户端 目前是阻塞的
        SocketChannel channel = serverSocketChannel.accept();
        // 4.读取数据
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 读取到的字节长度
        int len = channel.read(buffer);
        System.out.println(new String(buffer.array(), 0, len));
    }
    
    public static void IONetWorkServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        Socket socket = serverSocket.accept();
        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len = inputStream.read(bytes);
        System.out.println(new String(bytes, 0, len));
        socket.close();
        
    }
}
