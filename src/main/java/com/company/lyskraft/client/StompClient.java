package com.company.lyskraft.client;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.Scanner;

public class StompClient {
    private static String URL = "ws://api.metaltrade.io/ws";
    //private static String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIrNjI4Nzg1ODg4MTI1NiIsImlhdCI6MTY4ODI2ODY0OSwiZXhwIjoxNzE5ODA0NjQ5fQ.GPOSpi1x7Fo5s4MSqbo5J6PuTGF18zKcGaS3nkOqVvc";
    private static String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIrOTE5ODExODcxNDUxIiwiaWF0IjoxNjg2MzExMzA0LCJleHAiOjE3MTc4NDczMDR9.F-YhmCzGl2xxTsbGoCGuZ-qarWig1QBbEOF1REAvR2U";
    public static void main(String[] args) {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);

        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        headers.add("Authorization", "Bearer "+token);
        StompHeaders stompHeaders = new StompHeaders();
        stompHeaders.add("Authorization", "Bearer "+token);
        StompSessionHandler sessionHandler = new MyStompSessionHandler();
        stompClient.connectAsync(URL, headers, stompHeaders, sessionHandler);

        new Scanner(System.in).nextLine(); // Don't close immediately.
    }
}
