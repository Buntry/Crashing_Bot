package com.crashing_waves.discord_bot;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import java.io.File;
import java.io.IOException;

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

        api.addMessageCreateListener(event -> {
            if (event.getMessageContent().equals("ping")){
                String url = "video.mp4";
                String command = "python C:\\Users\\colin\\Documents\\Python_Tik_tok\\main.py";
                try{
                    Process p = Runtime.getRuntime().exec(command);
                    p.waitFor();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                new MessageBuilder().addAttachment(new File(url)).send(event.getChannel());
                try{
                    Process p = Runtime.getRuntime().exec("rm video.mp4");
                    p.waitFor();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                //EmbedBuilder embed = new EmbedBuilder().setAuthor(event.getMessageAuthor()).setFooter("feet");
                //event.getChannel().sendMessage(embed);
            }
        });

        return api;
    }

}
