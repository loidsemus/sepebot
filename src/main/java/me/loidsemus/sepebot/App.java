package me.loidsemus.sepebot;

import me.loidsemus.sepebot.api.W2GService;
import me.loidsemus.sepebot.listeners.CommandListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.security.auth.login.LoginException;
import java.util.Collections;

public class App {

    public static void main(String[] args) throws LoginException {
        new App().start();
    }

    private void start() throws LoginException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://w2g.tv")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        W2GService service = retrofit.create(W2GService.class);

        JDA jda = JDABuilder.createLight(System.getenv("DISCORD_TOKEN"),
                Collections.emptyList())
                .setActivity(Activity.playing("/w2g"))
                .addEventListeners(new CommandListener(service))
                .build();

        jda.upsertCommand(new CommandData("w2g", "Skapa ett W2G-rum")
                .addOption(OptionType.STRING, "video", "Video som ska visas", false)).queue();
    }

}
