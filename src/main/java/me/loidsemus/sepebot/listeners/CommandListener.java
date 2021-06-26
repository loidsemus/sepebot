package me.loidsemus.sepebot.listeners;

import me.loidsemus.sepebot.api.CreateRoomResponse;
import me.loidsemus.sepebot.api.W2GService;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Response;

public class CommandListener extends ListenerAdapter {

    private final W2GService service;

    public CommandListener(W2GService service) {
        this.service = service;
    }

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        if (!event.getName().equals("w2g")) return;

        Call<CreateRoomResponse> call;
        OptionMapping videoOption = event.getOption("video");
        if (videoOption == null) {
            call = service.createRoomWithoutVideo(System.getenv("W2G_TOKEN"));
        }
        else {
            call = service.createRoomWithVideo(new W2GService.RequestBody(
                    System.getenv("W2G_TOKEN"),
                    videoOption.getAsString()));
        }

        try {
            Response<CreateRoomResponse> response = call.execute();
            if (response.body() == null) {
                event.reply("Något gick snett bre").setEphemeral(true).queue();
                return;
            }
            event.reply("https://w2g.tv/rooms/" + response.body().getStreamkey()).setEphemeral(false).queue();
        } catch (Exception e) {
            event.reply("Något gick snett bre").setEphemeral(true).queue();
            e.printStackTrace();
        }
    }
}
