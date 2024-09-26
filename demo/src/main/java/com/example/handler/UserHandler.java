package com.example.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;

import com.example.service.User;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class UserHandler implements HttpHandler{

    private final User user = new User();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // TODO Auto-generated method stub
        this.getInfo(exchange);
            
        /*
        InputStream inputStream = context.getRequestBody();
        byte[] bytes = inputStream.readAllBytes();
        String body = new String(bytes);
        System.out.println(body);
        */

        String content = user.getAllUsers().toJSONString();//"reseponse data";
        this.send(exchange, content);
    }
    
    private void getInfo(HttpExchange context) {
        String method = context.getRequestMethod();
        System.out.println("method => "+method);
        URI uri = context.getRequestURI();
        String path = uri.getPath();
        System.out.println("path =>"+path);
        
        Headers headers = context.getRequestHeaders();
        for (String key : headers.keySet()) {
            List<String> values = headers.get(key);
            System.out.println(key + ": " + values);
        }
    }

    private void send(HttpExchange context, String sendData) {
        byte[] bytes = sendData.getBytes();
        try {
            context.getResponseHeaders().add("Content-Type", "application/json");
            context.sendResponseHeaders(200, bytes.length);
            OutputStream outputStream = context.getResponseBody();
            outputStream.write(bytes);
            outputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
            try {
                context.sendResponseHeaders(400, 0L);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
