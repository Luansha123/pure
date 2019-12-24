package com.sc;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws IOException {
        System.out.println( "Hello World!" );


        ServerSocket serverSocket = new ServerSocket(9090);

        ServerSocket serverSocket1 = new ServerSocket();
        serverSocket1.bind(new InetSocketAddress(9090));
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(9999));
        serverSocketChannel.configureBlocking(false);

        SocketChannel socketChannel = serverSocketChannel.accept();

    }
}
