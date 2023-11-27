package com.company.lyskraft.client;

import com.company.lyskraft.constant.ChatMessageType;
import com.company.lyskraft.entity.*;
import com.company.lyskraft.constant.ChatMessageStatus;
import com.company.lyskraft.constant.CompanyStatus;
import com.company.lyskraft.constant.EnquiryType;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {
    private Logger logger = LogManager.getLogger(MyStompSessionHandler.class);
    private static String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIrNjI4Nzg1ODg4MTI1NiIsImlhdCI6MTY4ODI2ODY0OSwiZXhwIjoxNzE5ODA0NjQ5fQ.GPOSpi1x7Fo5s4MSqbo5J6PuTGF18zKcGaS3nkOqVvc";

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        StompHeaders subscribeHeaders = new StompHeaders();
        subscribeHeaders.add("Authorization", "Bearer "+token);
        subscribeHeaders.add("destination", "/company/1/queue/messages");
        session.subscribe(subscribeHeaders, this);
        logger.info("Subscribed to /company/1/queue/messages");

        StompHeaders sendHeaders = new StompHeaders();
        sendHeaders.add("Authorization", "Bearer "+token);
        sendHeaders.add("destination", "/mtp/chat");
        //session.send(sendHeaders, getSampleSimpleMessage());
        session.send(sendHeaders, getSampleEnquiryMessage());
        //session.send(sendHeaders, getSampleQuoteMessage());
        logger.info("Message sent to websocket server");

        StompHeaders markReadHeaders = new StompHeaders();
        markReadHeaders.add("Authorization", "Bearer "+token);
        markReadHeaders.add("destination", "/mtp/chat/read");
        session.send(markReadHeaders, getMessageIds());
        logger.info("Messages mark read");
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        logger.error("Got an exception", exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return ChatMessage.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        ChatMessage msg = (ChatMessage) payload;
        logger.info("Received : " + msg.getBody().getText() + " from : " + msg.getSenderCompanyId());
        logger.info("Last Modified time :" + msg.getLastModifiedDate());
    }

    /**
     * A sample message instance.
     * @return instance of <code>Message</code>
     */
    private ChatMessage getSampleSimpleMessage() {
        ChatMessage msg = new ChatMessage();
        msg.setSenderCompanyId(1);
        msg.setRecipientCompanyId(2);
        ChatBody chatBody = new ChatBody();
        chatBody.setChatMessageType(ChatMessageType.Text);
        SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        chatBody.setText("Hello, this is the most simple message send at: " + dtf.format(new Date()));
        logger.info(chatBody.getText());
        msg.setBody(chatBody);
        return msg;
    }

    private List<Long> getMessageIds() {
        List<Long> messageIds = new ArrayList<>();
        messageIds.add(85L);
        messageIds.add(84L);
        return messageIds;
    }

    private ChatMessage getSampleEnquiryMessage() {
        ChatMessage msg = new ChatMessage();
        msg.setSenderCompanyId(2);
        msg.setRecipientCompanyId(1);
        ChatBody chatBody = new ChatBody();
        chatBody.setChatMessageType(ChatMessageType.Enquiry);
        Enquiry enquiry = new Enquiry();
        enquiry.setId(4);
        msg.setEnquiryId(4);
        chatBody.setEnquiry(enquiry);
        msg.setBody(chatBody);
        logger.info(new Gson().toJson(msg));
        return msg;
    }

    private ChatMessage getSampleQuoteMessage() {
        ChatMessage msg = new ChatMessage();
        Company sender = new Company();
        sender.setId(2);
        sender.setName("Tata Steels");
        sender.setStatus(CompanyStatus.Verified);
        msg.setSenderCompanyId(1);
        Company receiver = new Company();
        receiver.setId(1);
        receiver.setName("Tata Steels");
        receiver.setStatus(CompanyStatus.Verified);
        msg.setRecipientCompanyId(2);
        ChatBody chatBody = new ChatBody();
        chatBody.setChatMessageType(ChatMessageType.Quote);
        chatBody.setText("Hello, this is the quote type chat. This is very large message to be truncated.");
        Enquiry enquiry = new Enquiry();
        enquiry.setId(1);
        enquiry.setEnquiryCompany(sender);
        enquiry.setEnquiryType(EnquiryType.Buy);
        Quote quote = new Quote();
        quote.setId(1);
        quote.setEnquiry(enquiry);
        quote.setQuoteCompany(receiver);
        msg.setQuoteId(1);
        msg.setEnquiryId(1);
        chatBody.setQuote(quote);
        msg.setBody(chatBody);
        msg.setStatus(ChatMessageStatus.Unseen);
        return msg;
    }
}