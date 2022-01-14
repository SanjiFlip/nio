# NIO

## **1.** **什么是NIO**

### 1.1 概念

- 即 Java New IO 
- 是1个全新的、 JDK 1.4后提供的 IO API 
- Java API中提供了两套NIO，一套是针对 标准输入输出NIO，另一套就是网络编程NIO

### 1.2 作用

- NIO 和 IO 有相同的作用和目的，但实现方式不同 
- 可替代 标准 Java IO 的 IO API 
- IO是以流的方式处理数据，而NIO是以块的方式处理数据。

### 1.3 流与块的比较

- NIO和IO最大的区别是数据打包和传输方式。
-  IO是以流的方式处理数据，而NIO是以块的方式处理数据。

**面向流**的IO一次一个字节的处理数据，一个输入流产生一个字节，一个输出流就消费一个字节。

**面向块**的IO系统以块的形式处理数据。每一个操作都在一步中产生或消费一个数据块。按块要比按流快 的多

（举例：拿水龙头来比喻：流就像水龙头滴水，每次只有一滴；块就像水龙头往水壶放水，放满之后对 一整个水壶的水进行操作）

### 1.4 新特性

对比于**Java IO**，**NIO**具备的新特性如下：

![image-20220108180217574](https://gitee.com/SanjiFlip_admin/pictures/raw/master/imgs/image-20220108180217574.png)

- 可简单认为：**IO是面向流的处理，NIO是面向块(缓冲区)的处理**
  - 面向流的I/O 系统**一次一个字节地处理数据**
  - 一个面向块(缓冲区)的I/O系统**以块的形式处理数据**

### 1.5 核心组件

**Java NIO**的核心组件 包括：

- 通道（ Channel ）

- 缓冲区（ Buffer ）

- 选择器（ Selector ）

  

在NIO中并不是以流的方式来处理数据的，而是以buﬀer缓冲区和Channel管道配合使用来处理数据。 Selector是因为NIO可以使用异步的非阻塞模式才加入的东西

![image-20220108180547816](https://gitee.com/SanjiFlip_admin/pictures/raw/master/imgs/image-20220108180547816.png)

简单理解一下：

- Channel管道比作成铁路，buﬀer缓冲区比作成火车(运载着货物)，而我们的NIO就是**通过Channel管道运输着存储数据的Buﬀer缓冲区的来实现数据的处理！**

- 要时刻记住：Channel不与数据打交道，它只负责运输数据。与数据打交道的是Buﬀer缓冲区

-  **相关作用**  

  - **Channel-->运输**

  - **Buﬀer-->数据**

    

相对于传统IO而言，**流是单向的**。对于NIO而言，有了Channel管道这个概念，我们的**读写都是双向的** (铁路上的火车能从广州去北京、自然就能从北京返还到广州)！



## 2. Buﬀer缓冲区

### 2.1 Buﬀer缓冲区概述

作用：缓冲区，用来存放具体要被传输的数据，比如文件、scoket 等。这里将数据装入 Buﬀer 再通过 通道进行传输。

Buﬀer 就是一个数组，用来保存不同数据类型的数据 在 NIO 中，所有的缓冲区类型都继承于抽象类 Buﬀer，最常用的就是 ByteBuﬀer，对于 Java 中的基本 类型，基本都有一个具体 Buﬀer 类型与之相对应，它们之间的继承关系如下图所示

​	![image-20220108181030807](https://gitee.com/SanjiFlip_admin/pictures/raw/master/imgs/image-20220108181030807.png)

- ByteBuﬀer：存储字节数据到缓冲区 
- ShortBuﬀer：存储字符串数据到缓冲区 
- CharBuﬀer： 存储字符数据到缓冲区 
- IntBuﬀer：存储整数数据到缓冲区 
- LongBuﬀer：存储长整型数据到缓冲区 
- DoubleBuﬀer：存储小数到缓冲区 
- FloatBuﬀer：存储小数到缓冲区

对于 Java 中的基本数据类型，都有一个 Buﬀer 类型与之相对应，最常用的自然是**ByteBuﬀer** 类（二 进制数据）

### 2.2 ByteBuﬀer的创建方式

**代码演示**

- 在堆中创建缓冲区：allocate(int capacity) 
- 在系统内存创建缓冲区：allocateDirect(int capacity) 
- 通过普通数组创建缓冲区：wrap(byte[] arr)

```java
import java.nio.ByteBuffer;

public class Demo01Buffer {

  public static void main(String[] args) { 
    //在堆中创建缓冲区：allocate(int capacity)* 
    ByteBuffer buffer1 = ByteBuffer.allocate(10);

    //在系统内存创建缓冲区：allocateDirect(int capacity) 
    ByteBuffer buffer2 = ByteBuffer.allocateDirect(10);

    //通过普通数组创建缓冲区：wrap(byte[] arr) 
    byte[] arr = {97,98,99}; 
    ByteBuffer buffer3 = ByteBuffer.wrap(arr);

  }

}
```

### 2.3 常用方法

拿到一个缓冲区我们往往会做什么？很简单，就是**读取缓冲区的数据/写数据到缓冲区中**。

 所以，缓冲区的核心方法就是:

- put(byte b) : 给数组添加元素 
- get() :获取一个元素

```java
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
```

Buﬀer类维护了4个核心变量属性来提供**关于其所包含的数组的信息**。它们是：

- **容量Capacity**
  - **缓冲区能够容纳的数据元素的最大数量**。容量在缓冲区创建时被设定，并且永远不能被改 变。(不能被改变的原因也很简单，底层是数组嘛) 

- **界限Limit**
  - **缓冲区中可以操作数据的大小**，代表了当前缓冲区中一共有多少数据（从limit开始后面的位 置不能操作）。 

- **位置Position**

  - **下一个要被读或写的元素的位置**。Position会自动由相应的**get( )**和**put( )**函数更新。

  - ```
    以上三个属性值之间有一些相对大小的关系：0 <= position <= limit <= capacity 例：- 如果我们创建一个新的容量大小为20 的 ByteBuffer 对象，在初始化的时候， position 设置为 0， limit 和 capacity 被设置为 10，在以后使用 ByteBuffer对象过程中，capacity 的 值不会再发生变化，而其它两个个将会随着使用而变化。
    ```

    

- **标记Mark**
  - **一个备忘位置。用于记录上一次读写的位置。**

### 2.4 buﬀer代码演示

首先展示一下是**创建缓冲区后，核心变量的值是怎么变化的**

```java
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
        byteBuffer.put(s.getBytes(StandardCharsets.UTF_8));
        System.out.println("put后----，capacity = " + byteBuffer.capacity());
        System.out.println("put后----，limit = " + byteBuffer.limit());
        System.out.println("put后----，position = " + byteBuffer.position());
        System.out.println("put后----，mark = " + byteBuffer.mark());
      	System.out.println("------------------------");
        byteBuffer.flip();
        System.out.println("flip后----，capacity = " + byteBuffer.capacity());
        System.out.println("flip后----，limit = " + byteBuffer.limit());
        System.out.println("flip后----，position = " + byteBuffer.position());
        System.out.println("flip后----，mark = " + byteBuffer.mark());
    }
    
}
```

运行结果：

![image-20220112165742999](https://gitee.com/SanjiFlip_admin/pictures/raw/master/imgs/image-20220112165742999.png)

现在**我想要从缓存区拿数据**，怎么拿呀？？NIO给了我们一个**flip()**方法。这个方法可以改动**position**和**limit**的位置！

还是上面的代码，我们**flip()**一下后，再看看4个核心属性的值会发生什么变化：

![image-20220112170249271](https://gitee.com/SanjiFlip_admin/pictures/raw/master/imgs/image-20220112170249271.png)

很明显的是：

- **limit变成了position的位置了**
- **而position变成了0**

当调用完 filp() 时：**limit是限制读到哪里**，**而position是从哪里读** 一般我们称 filp() 为“**切换成读模式**”

- 每当要从缓存区的时候读取数据时，就调用 filp() “**切换成读模式**”。

![image-20220112170726626](https://gitee.com/SanjiFlip_admin/pictures/raw/master/imgs/image-20220112170726626.png)

切换成读模式之后，我们就可以读取缓冲区的数据了：

```java
System.out.println("进行读取buffer-----------");
// 1.创建数组 只有limit这么多个数据可读
byte[] bytes = new byte[byteBuffer.limit()];
// 2.将读取出来的数据装入字节数组中
byteBuffer.get(bytes);
// 3.输出数据
System.out.println(new String(bytes, 0, bytes.length));
```

![image-20220114143403993](https://gitee.com/SanjiFlip_admin/pictures/raw/master/imgs/image-20220114143403993.png)

**读完我们还想写数据到缓冲区**，那就使用 **clear()** 函数，这个函数会“清空”缓冲区：

- 数据没有真正被清空，只是被遗忘掉了

```java
char b = (char) byteBuffer.get();
System.out.println(b);
```

![image-20220114145029268](https://gitee.com/SanjiFlip_admin/pictures/raw/master/imgs/image-20220114145029268.png)

**Mark标记**

```java
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
```

![image-20220114145903392](https://gitee.com/SanjiFlip_admin/pictures/raw/master/imgs/image-20220114145903392.png)

## 3.Channel通道

### 3.1 Channel通道概述

通道（Channel）：由 java.nio.channels 包定义 的。Channel 表示 IO 源与目标打开的连接。 Channel 类似于传统的“流”。 

标准的IO基于字节流和字符流进行操作的，而NIO是基于通道（Channel）和缓冲区（Buﬀer）进行操 作，数据总是从通道读取到缓冲区中，或者从缓冲区写入到通道中（白话: 就是数据传输用的通道，作 用是打开到IO设备的连接，文件、套接字都行） 

例：相当于一根管子，buﬀer中的数据可以通过管子写入被操作的资源当中，也可以将资源通过管子写 入到buﬀer中去

### 3.2 Channel API

Java 为 Channel 接口提供的最主要实现类如下：

![image-20220114150047462](https://gitee.com/SanjiFlip_admin/pictures/raw/master/imgs/image-20220114150047462.png)

- FileChannel：用于读取、写入、映射和操作文件的通道。 
- DatagramChannel：通过 UDP 读写网络中的数据通道。 
- SocketChannel：通过 TCP 读写网络中的数据。 
- ServerSocketChannel：可以监听新进来的 TCP 连接，对每一个新进来 的连接都会创建一个 SocketChannel。

### 3.3 FileChannel基本使用

- 使用FileChannel完成文件的复制

```java
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
```

### 3.4 网络编程收发信息

- 客户端

  ```java
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
          // 4.输出数据
          socketChannel.write(byteBuffer);
          // 5.关流
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
  ```

- 服务器端

  



### 3.5 accept阻塞问题

```java
package com.xmaven.demo06;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author: Ambition
 * @Description TODO 若是只启动服务端，不启动客户端，数据一直在等待接收中...阻塞到客户端连接
 * @Date: 2022/1/14 5:40 下午
 * @Version 1.0
 */
public class AcceptProblem {
    
    public static void main(String[] args) throws IOException {
        // 1.创建服务器端对象，监听对应的端口
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 2.监听对应的端口
        serverSocketChannel.bind(new InetSocketAddress(9999));
        System.out.println("等待客户端发送过来>>>>>>>");
        // 3.连接客户端 目前是阻塞的
        SocketChannel channel = serverSocketChannel.accept();
        System.out.println("客户端数据接受中---------");
        // 4.读取数据
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 读取到的字节长度
        int len = channel.read(buffer);
        System.out.println(new String(buffer.array(), 0, len));
    }
    
}
```

![image-20220114174615085](https://gitee.com/SanjiFlip_admin/pictures/raw/master/imgs/image-20220114174615085.png)

- 非阻塞服务器端

```java
// 设置成非阻塞连接
serverSocketChannel.configureBlocking(false);
```

```java
package com.xmaven.demo06;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author: Ambition
 * @Description TODO 若是只启动服务端，不启动客户端，数据一直在等待接收中...
 * @Date: 2022/1/14 5:40 下午
 * @Version 1.0
 */
public class AcceptProblem {
    
    public static void main(String[] args) throws IOException {
        // 1.创建服务器端对象，监听对应的端口
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 2.监听对应的端口
        serverSocketChannel.bind(new InetSocketAddress(9999));
        // 设置成非阻塞连接
        serverSocketChannel.configureBlocking(false);
        System.out.println("等待客户端发送过来>>>>>>>");
        // 3.连接客户端 目前是阻塞的
        SocketChannel channel = serverSocketChannel.accept();
        System.out.println("客户端数据接受中---------");
        // 4.读取数据
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 读取到的字节长度
        int len = channel.read(buffer);
        System.out.println(new String(buffer.array(), 0, len));
    }
    
}
```

![image-20220114174941323](https://gitee.com/SanjiFlip_admin/pictures/raw/master/imgs/image-20220114174941323.png)

**此时因为为非阻塞状态，未判断客户端的数据，导致空指针异常，我们需要判断**

- 完整版非阻塞代码

  ```java
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
  ```

## 4.Selector选择器(又称多路复用器)

### 4.1 多路复用的概念

​	一个选择器可以同时监听多个服务器端口, 帮多个服务器端口同时等待客户端的访问

### 4.2 Selector的和Channel的关系

Channel和Buﬀer比较好理解 ，联系也比较密切，他们的关系简单来说就是：数据总是从通道中读到 buﬀer缓冲区内，或者从buﬀer写入到通道中。

**选择器和他们的关系又是什么？**

```markdown
选择器（Selector） 是 Channel（通道）的多路复用器，Selector 可以同时监控多个 通道的 IO（输入 输出） 状况。
```

**Selector的作用是什么？**

```markdown
选择器提供选择执行已经就绪的任务的能力。从底层来看，Selector提供了询问通道是否已经准备好执 行每个I/O操作的能力。Selector 允许单线程处理多个Channel。仅用单个线程来处理多个Channels的 好处是，只需要更少的线程来处理通道。事实上，可以只用一个线程处理所有的通道，这样会大量的减 少线程之间上下文切换的开销。
```

### 4.3 可选择通道(SelectableChannel)

注意：并不是所有的Channel，都是可以被Selector 复用的。比方说，FileChannel就不能被选择器复 用。为什么呢？

判断一个Channel 能被Selector 复用，有一个前提：判断他是否继承了一个抽象类SelectableChannel。如果继承了SelectableChannel，则可以被复用，否则不能。

SelectableChannel 的结构如下图：

![image-20220114175747356](https://gitee.com/SanjiFlip_admin/pictures/raw/master/imgs/image-20220114175747356.png)

SelectableChannel类提供了实现通道的可选择性所需要的公共方法

**通道和选择器注册之后，他们是绑定的关系吗？**

```
答:不是。不是一对一的关系。一个通道可以被注册到多个选择器上，但对每个选择器而言只能被注册一 次。
```

通道和选择器之间的关系，使用注册的方式完成。SelectableChannel可以被注册到Selector对象上， 在注册的时候，需要指定通道的哪些操作，是Selector感兴趣的。

![image-20220114175838089](https://gitee.com/SanjiFlip_admin/pictures/raw/master/imgs/image-20220114175838089.png)

### 4.4 Channel注册到Selector

使用Channel.register（Selector sel，int ops）方法，将一个通道注册到一个选择器时。 

​										第一个参数：指定通道要注册的选择器是谁 

​										第二个参数：指定选择器需要查询的通道操作



可以供选择器查询的通道操作，从类型来分，包括以下四种： 

（1）可读 : SelectionKey.OP_READ 

（2）可写 : SelectionKey.OP_WRITE 

（3）连接 : SelectionKey.OP_CONNECT 

（4）接收 : SelectionKey.OP_ACCEPT

如果Selector对通道的多操作类型感兴趣，可以用“位或”操作符来实现：int key = SelectionKey.OP_READ | SelectionKey.OP_WRITE ;

### 4.5 选择键(SelectionKey)

Channel和Selector的关系确定好后，并且一旦通道处于某种就绪的状态，就可以被选择器查询到。这 个工作，使用选择器Selector的select（）方法完成。select方法的作用，对感兴趣的通道操作，进行就 绪状态的查询。

Selector可以不断的查询Channel中发生的操作的就绪状态。并且挑选感兴趣的操作就绪状态。一旦通 道有操作的就绪状态达成，并且是Selector感兴趣的操作，就会被Selector选中，放入选择键集合中。

```markdown
select()							:选择器等待客户端连接的方法 
											阻塞问题:
                          1.在开始没有客户访问的时候是阻塞的

                          2.在有客户来访问的时候方法会变成非阻塞的

                          3.如果客户的访问被处理结束之后,又会恢复成阻塞的

selectedKeys() 				:选择器会把被连接的服务端对象放在Set集合中,这个方法就是返回一个Set集合
```

### 4.6 Selector的使用流程

#### 4.6.1 创建Selector

Selector对象是通过调用静态工厂方法open()来实例化的，如下：

```java
// 1、获取Selector选择器 
Selector selector = Selector.open();
```

#### 4.6.2 将Channel注册到Selector

要实现Selector管理Channel，需要将channel注册到相应的Selector上，如下：

```java
// 2、获取通道 
ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

// 3.设置为非阻塞 
serverSocketChannel.configureBlocking(false);

// 4、绑定连接 
serverSocketChannel.bind(new InetSocketAddress(SystemConfig.SOCKET_SERVER_PORT));

// 5、将通道注册到选择器上,并制定监听事件为：“接收”事件 
serverSocketChannel.register(selector，SelectionKey.OP_ACCEPT);
```

上面通过调用通道的register()方法会将它注册到一个选择器上。 

**首先需要注意的是：** 

与Selector一起使用时，Channel必须处于非阻塞模式下，否则将抛出异常 IllegalBlockingModeException

**测试用例**

```java
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
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 3.指定端口
        serverSocketChannel.bind(new InetSocketAddress(9999));
        // 4.设置当前模式为非阻塞 这个是最重要的，因为与selector使用必须采用非阻塞
        serverSocketChannel.configureBlocking(false);
        // 5.将通道注册到选择器上面,指定监听事件为"接收"事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        
    }
    
}
```

#### 4.6.3 select() 

Channel和Selector的关系确定好后，并且一旦通过处于某种就绪的状态，就可以被选择器查询到。这个工作，使用选择器Selector的select()方法完成。select方法的作用，对感兴趣的通道操作，进行就绪状态的查询。

```java
select();   				//查询已经处于就绪状态的通道
```

```java
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
```

任意启动一个客户端进行测试....

```java
package com.xmaven.demo07;

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
```



#### 4.6.4 轮询查询就绪操作

万事俱备，下一步是查询就绪的操作。

 通过Selector的 select() 方法，可以查询出已经就绪的通道操作，这些就绪的状态集合，包存在一个元 素是SelectionKey对象的Set集合中。 

select()方法返回的int值，表示有多少通道已经就绪 

而一旦调用select()方法，并且返回值不为0时，下一步工干啥？ 通

过调用Selector的selectedKeys()方法来访问已选择键集合，然后迭代集合的每一个选择键元素，根 据就绪操作的类型，完成对应的操作：

```java
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
```

**修改上述的客户端代码启动，端口分别为9999,8888,7777**

![image-20220114201431312](https://gitee.com/SanjiFlip_admin/pictures/raw/master/imgs/image-20220114201431312.png)



