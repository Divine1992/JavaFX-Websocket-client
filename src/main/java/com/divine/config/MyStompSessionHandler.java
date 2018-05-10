package com.divine.config;

import com.divine.model.Message;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {
    private String sender;

    public MyStompSessionHandler(String userId) {
        this.sender = userId;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        subscribeTopic("/topic/public", session);
    }

    private void subscribeTopic(String topic,StompSession session) {

        session.subscribe(topic, new StompFrameHandler() {

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Message.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                // Another way to use incoming/outcoming messages
                /*try {
                    FileOutputStream fos = new FileOutputStream(new File("messages"));
                    String message = payload.toString();
                    byte[] bytes = message.getBytes();
                    fos.write(bytes);
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                System.err.println(payload.toString());
            }

        });

    }

}
