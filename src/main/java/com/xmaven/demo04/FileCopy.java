package com.xmaven.demo04;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: Ambition
 * @Description TODO FileCopy 使用nio完成文件的复制
 * @Date: 2022/1/14 4:34 下午
 * @Version 1.0
 */
public class FileCopy {
    
    public static void main(String[] args) throws IOException {
        // 1.创建输入流和输出流 (依赖于IO流获取channel)
        FileInputStream fileInputStream = new FileInputStream(
                "xmaven.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("nio复制.txt");
        // 2.通过IO流获取channel通道
        FileChannel channel1 = fileInputStream.getChannel();
        FileChannel channel2 = fileOutputStream.getChannel();
        
        // 创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 循环读写
        while (channel1.read(buffer) != -1) {
            // 此时buffer中 position 指向的是 1023(文件足够大的时候) limit 也是1023（位置从0开始）
            // 我们写入文件读取的位置就是position。需要把position置为0开始 从position到limit开始写入
            // 此时需要使用到flip 把position置为0，把limit置为写入的大小（可能读取最后不够1023）
            // 切换
            buffer.flip();
            // write 后 position和limit都指向的是同一个位置
            channel2.write(buffer);
            // 还原所有指针位置 也就是相当于初始化到最初的状态 ByteBuffer.allocate(1024);
            // 可以理解为清空缓冲区 但是之前存入缓冲区的数据还在缓冲区只是这个数据处于一个遗忘状态
            // 主要是为了让后续的数据可以写入到缓冲区中
            buffer.clear();
        }
        // 关流
        fileInputStream.close();
        fileOutputStream.close();
    }
    
}
