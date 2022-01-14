package com.xmaven.demo03;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Ambition
 * @Description TODO ByteBuffer四大核心属性
 * @Date: 2022/1/12 4:42 下午
 * @Version 1.0
 */
public class CoreAttributes {
    
    public static void main(String[] args) {
        
        // 1.创建buffer对象
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        System.out.println("初始化----，capacity = " + byteBuffer.capacity());
        System.out.println("初始化----，limit = " + byteBuffer.limit());
        System.out.println("初始化----，position = " + byteBuffer.position());
        System.out.println("初始化----，mark = " + byteBuffer.mark());
        System.out.println("------------------------");
        // 添加一些数据到缓冲区
        String s = "JavaEE"; // 这个字符串相当于 6个字节，占据6个位置
        // put改变的是position
        byteBuffer.put(s.getBytes(StandardCharsets.UTF_8));
        System.out.println("put后----，capacity = " + byteBuffer.capacity());
        System.out.println("put后----，limit = " + byteBuffer.limit());
        System.out.println("put后----，position = " + byteBuffer.position());
        System.out.println("put后----，mark = " + byteBuffer.mark());
        
        System.out.println("------------------------");
        // flip改变的是position和limit
        byteBuffer.flip();
        System.out.println("flip后----，capacity = " + byteBuffer.capacity());
        System.out.println("flip后----，limit = " + byteBuffer.limit());
        System.out.println("flip后----，position = " + byteBuffer.position());
        System.out.println("flip后----，mark = " + byteBuffer.mark());
        
        System.out.println("进行读取buffer-----------");
        // 1.创建数组 只有limit这么多个数据可读
        byte[] bytes = new byte[byteBuffer.limit()];
        // 2.将读取出来的数据装入字节数组中
        // get 改变了position
        byteBuffer.get(bytes);
        System.out.println("get后----，capacity = " + byteBuffer.capacity());
        System.out.println("get后----，limit = " + byteBuffer.limit());
        System.out.println("get后----，position = " + byteBuffer.position());
        System.out.println("get后----，mark = " + byteBuffer.mark());
        // 3.输出数据
        System.out.println(new String(bytes, 0, bytes.length));
        
        System.out.println("clear()函数执行后--------------");
        // clear 改变了position和limit 重置为初始化状态
        // 之前存入缓冲区的数据还在缓冲区只是这个数据处于一个遗忘状态
        byteBuffer.clear();
        System.out.println("clear后----，capacity = " + byteBuffer.capacity());
        System.out.println("clear后----，limit = " + byteBuffer.limit());
        System.out.println("clear后----，position = " + byteBuffer.position());
        System.out.println("clear后----，mark = " + byteBuffer.mark());

//        char b = (char) byteBuffer.get();
//        System.out.println(b);
        
        System.out.println("------------------------------");
        // byteBuffer.mark() 用于记录上一次读写位置的position
        byteBuffer.put("maven".getBytes(StandardCharsets.UTF_8));
        System.out.println("第一次put后----，position = " + byteBuffer.position());
        // 做一个标记：纪律上一次读写位置 position的值 5
        byteBuffer.mark();
        byteBuffer.put("abcd".getBytes(StandardCharsets.UTF_8));
        // 还原到标记位置
        byteBuffer.reset();
        System.out.println("reset后----，position = " + byteBuffer.position());
    }
    
}
