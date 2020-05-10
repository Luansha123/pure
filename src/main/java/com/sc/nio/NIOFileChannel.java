package com.sc.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel {

    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream(new File("d:\\beauty.jpg"));
        FileChannel fileChannel = fis.getChannel();

        FileOutputStream fos = new FileOutputStream("d:\\a.jpg");
        FileChannel fosChannel = fos.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);


        int len = 0;
        while((len = fileChannel.read(byteBuffer)) != -1){

            byteBuffer.flip();
            fosChannel.write(byteBuffer);
            byteBuffer.clear();
        }

        fis.close();
        fos.close();

    }
}
