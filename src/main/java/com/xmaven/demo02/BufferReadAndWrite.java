package com.xmaven.demo02;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * @Author: Ambition
 * @Description TODO ByteBuffer的读取和写入
 * @Date: 2022/1/12 3:16 下午
 * @Version 1.0
 */
public class BufferReadAndWrite {
    
    public static void main(String[] args) {
        // 1.创建buffer对象
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        
        // put(byte b) 给数组添加元素
        byteBuffer.put((byte) 10);
        byteBuffer.put((byte) 20);
        byteBuffer.put((byte) 30);
        
        // 把缓冲区数组转换成普通数组
        byte[] array = byteBuffer.array();
        
        // 打印
        System.out.println(Arrays.toString(array));
        
        // get()  获取一个元素
        byte b = byteBuffer.get(0);
        System.out.println(b);
        
    }
}
