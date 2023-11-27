package com.company.lyskraft.communication;

import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.Collection;
@RequiredArgsConstructor
@Service
@EnableAsync
public class FirebaseMessagingService {
    private final FirebaseMessaging firebaseMessaging;

    @Async
    public void sendNotification(String title, String body, Collection<String> tokens) throws Exception {
        Notification notification = Notification
                .builder()
                .setTitle(title)
                .setBody(body)
                .build();

        MulticastMessage message = MulticastMessage
                .builder()
                .addAllTokens(tokens)
                .setNotification(notification)
                .build();
        firebaseMessaging.sendMulticast(message);
    }
}
