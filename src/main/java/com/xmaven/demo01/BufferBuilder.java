package com.xmaven.demo01;

import java.nio.ByteBuffer;

/**
 * @Author: Ambition
 * @Description TODO ByteBuffer创建的三种方式
 * @Date: 2022/1/12 3:11 下午
 * @Version 1.0
 */
public class BufferBuilder {
    
    public static void main(String[] args) {
        // 第一种创建方式 在堆中创建缓冲区：allocate(int capacity)*  这种是最常用的方式
        ByteBuffer b1 = ByteBuffer.allocate(10);
        // 第二种创建方式 在系统内存创建缓冲区：allocateDirect(int capacity)
        ByteBuffer b2 = ByteBuffer.allocateDirect(10);
        // 第三种创建方式 通过普通数组创建缓冲区：wrap(byte[] arr)
        byte[] b3 = {22, 33, 44, 55};
        ByteBuffer wrap = ByteBuffer.wrap(b3);
        
    }
    
}
