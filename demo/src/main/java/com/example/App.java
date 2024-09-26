package com.example;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import com.example.handler.UserHandler;
import com.sun.net.httpserver.HttpHandler;
/**
 * Hello world!
 *
 */
public class App 
{

    private final HttpHandler userHandler = new UserHandler();;

    public static void main(String[] args) {
        System.out.println("hello world");
        App app = new App();
        try {
            app.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() throws Exception{
        InetSocketAddress address = new InetSocketAddress(5678);
        HttpServer httpServer = HttpServer.create(address, 0);
        httpServer.createContext("/", (exchange) -> {
            String sendData = "Hello";
            byte[] bytes = sendData.getBytes();
            exchange.sendResponseHeaders(200, bytes.length);
            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(bytes);
            outputStream.flush();
        });
        httpServer.createContext("/user", userHandler);

        httpServer.createContext("/favicon.ico", (exchange) -> {
            System.out.println("favicon call");
            exchange.sendResponseHeaders(404, -1);
            exchange.close();
        });
        httpServer.start();
    }
}
