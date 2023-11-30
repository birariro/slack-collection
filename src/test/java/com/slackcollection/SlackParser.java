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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SlackParser {
    @Value("${slack.token}")
    private String token ;

    /**
     * Find conversation ID using the conversations.list method
     */
    @Test
    public void findChatRomNames() {
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
//
//
//                System.out.println(" >>> getName = " + channel.getName());
//                System.out.println(" >>> isMember = " + channel.isMember());

            }

        } catch (IOException | SlackApiException e) {

            System.out.println("e = " + e);
        }
    }

    @Test
    public void fetchHistory() {
        String id = "";
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
