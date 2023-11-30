package com.slackcollection;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.Conversation;
import com.slack.api.model.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SlackParser {

    private final static String token = "";
    /**
     * Find conversation ID using the conversations.list method
     */
    @Test
    public List<String> findChatRomNames() {
        // you can get this instance via ctx.client() in a Bolt app
        var client = Slack.getInstance().methods();
        try {
            // Call the conversations.list method using the built-in WebClient
            var result = client.conversationsList(r -> r
                // The token you used to initialize your app
                .token(token)
            );
            List<Conversation> channels = result.getChannels();
            for (Conversation channel : channels) {

                System.out.println("channel = " + channel);

            }
            return channels.stream().map(Conversation::getName).toList();

        } catch (IOException | SlackApiException e) {

            System.out.println("e = " + e);
        }
        return new ArrayList<>();
    }

    @Test
    public void fetchHistory(String id) {
        // you can get this instance via ctx.client() in a Bolt app
        var client = Slack.getInstance().methods();
        try {
            // Call the conversations.history method using the built-in WebClient
            var result = client.conversationsHistory(r -> r
                // The token you used to initialize your app
                .token(token)
                .channel(id)
            );

            Optional<List<Message>> messages = Optional.ofNullable(result.getMessages());

           // System.out.println("result = " + result);


            messages.ifPresentOrElse(msg -> {
                for (Message message : msg) {
                    System.out.println("message = " + message);
                }
            },
            () -> System.out.println("not message"));

        } catch (IOException | SlackApiException e) {
            System.out.println("e.getMessage() = " + e.getMessage());
        }
    }
}
