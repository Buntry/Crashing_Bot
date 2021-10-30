package com.crashing_waves.discord_bot;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.util.concurrent.CompletableFuture;

@SpringBootApplication
public class DiscordBotApplication {

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(DiscordBotApplication.class, args);
    }


    @Bean
    @ConfigurationProperties(value = "discord-api")
    public DiscordApi discordApi() {
        String token = env.getProperty("TOKEN");
        DiscordApi api = new DiscordApiBuilder().setToken(token).setAllNonPrivilegedIntents().login().join();
        LinkParser parser = new LinkParser();
        api.addMessageCreateListener(event -> {
            if (parser.isWebURL(event.getMessageContent())){
                Message test = new MessageBuilder()
                        .setContent("Working...")
                        .send(event.getChannel())
                        .join();
                parser.parseLink(event.getMessageContent()).send(event.getChannel());
                event.deleteMessage();
                test.delete();
            }
        });

        return api;
    }

}
