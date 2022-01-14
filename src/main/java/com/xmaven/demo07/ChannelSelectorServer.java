package com.xmaven.demo07;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * @Author: Ambition
 * @Description TODO
 * @Date: 2022/1/14 7:37 下午
 * @Version 1.0
 */
public class ChannelSelectorServer {
    
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
        System.out.println("调用select前------------");
        int select = selector.select();
        System.out.println(select);
        
    }
    
}
