package com.divine.controller;

import com.divine.model.Message;
import com.divine.config.MyStompSessionHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.web.WebView;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class SampleController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<?> groupList;

    @FXML
    private WebView webBrowser;

    @FXML
    private ListView<String> messageList;

    @FXML
    private TextArea messageFiled;

    @FXML
    void initialize() {

        try {
            StompSession session = connectToWebsocketServer("http://localhost:8080/convers","JavaFx-client");
            session.send("/app/chat.sendMessage", new Message("JavaFx-client","Hello we are connected"));
            sendDefaultMessage(session);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    *Connect to WebSocket server using STOMP & SockJS
    * Only work Without security server config
     */
    private StompSession connectToWebsocketServer(String url, String sender) throws ExecutionException, InterruptedException, IOException {
        WebSocketClient simpleWebSocketClient = new StandardWebSocketClient();
        List<Transport> transports = new ArrayList(1);
        transports.add(new WebSocketTransport(simpleWebSocketClient));

        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSessionHandler sessionHandler = new MyStompSessionHandler(sender);
        StompSession session = stompClient.connect(url, sessionHandler).get();
        return session;
    }

    private void sendDefaultMessage(StompSession session) {
        messageFiled.setOnKeyPressed(event ->{
            if(event.getCode() == KeyCode.ENTER){
                Message message = new Message();
                message.setContent(messageFiled.getText().trim());
                message.setSender("JavaFX client");
                session.send("/app/chat.sendMessage",message);
                clearMessageField();
            }
        });
    }

    private void clearMessageField() {
        messageFiled.setText(null);
    }
}