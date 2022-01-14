package com.xmaven.demo07;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: Ambition
 * @Description TODO
 * @Date: 2022/1/14 7:37 下午
 * @Version 1.0
 */
public class ChannelSelectorServer2 {
    
    public static void main(String[] args) throws IOException {
        // 通道注册到选择器上
        
        // 1.获取selector选择器
        Selector selector = Selector.open();
        
        // 2.获取通道对象
        ServerSocketChannel serverSocketChannel1 = ServerSocketChannel.open();
        ServerSocketChannel serverSocketChannel2 = ServerSocketChannel.open();
        ServerSocketChannel serverSocketChannel3 = ServerSocketChannel.open();
        
        // 3.指定端口
        serverSocketChannel1.bind(new InetSocketAddress(9999));
        serverSocketChannel2.bind(new InetSocketAddress(8888));
        serverSocketChannel3.bind(new InetSocketAddress(7777));
        
        // 4.设置当前模式为非阻塞 这个是最重要的，因为与selector使用必须采用非阻塞
        serverSocketChannel1.configureBlocking(false);
        serverSocketChannel2.configureBlocking(false);
        serverSocketChannel3.configureBlocking(false);
        
        // 5.将通道注册到选择器上面,指定监听事件为"接收"事件
        serverSocketChannel1.register(selector, SelectionKey.OP_ACCEPT);
        serverSocketChannel2.register(selector, SelectionKey.OP_ACCEPT);
        serverSocketChannel3.register(selector, SelectionKey.OP_ACCEPT);
        
        // select() 查询已经就绪的通道操作，返回值：表示有多少通道已经就绪
        // 阻塞：阻塞到至少有一个通道上的事件就绪了
        
        // 6.采用轮训的方式、查询准备就绪的事件
        while (selector.select() > 0) {
            // 7.集合中就是所有已经准备就绪的操作
            Set<SelectionKey> set = selector.selectedKeys();
            // 遍历集合
            Iterator<SelectionKey> selectionKeys = set.iterator();
            while (selectionKeys.hasNext()) {
                // 8.已经准备就绪的事件
                SelectionKey selectionKey = selectionKeys.next();
                
                // 9.判断事件的类型 ---
                if (selectionKey.isAcceptable()) {
                    System.out.println("做些事件--------");
                    ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                    // 10.接收客户端发送过来的数据
                    SocketChannel socketChannel = channel.accept();
                    // 11.读取数据
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int len = socketChannel.read(buffer);
                    // 12.打印
                    System.out.println(new String(buffer.array(), 0, len));
                    // 13.资源关闭
                    socketChannel.close();
                }
                selectionKeys.remove();
            }
        }
        
        serverSocketChannel1.close();
        serverSocketChannel2.close();
        serverSocketChannel3.close();
    }
    
}
