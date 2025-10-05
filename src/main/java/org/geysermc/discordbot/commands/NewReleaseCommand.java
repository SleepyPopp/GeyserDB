/*
 * Copyright (c) 2020-2024 GeyserMC. http://geysermc.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * @author GeyserMC
 * @link https://github.com/GeyserMC/GeyserDiscordBot
 */

package org.geysermc.discordbot.commands;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.geysermc.discordbot.commands.filter.FilteredSlashCommand;
import org.geysermc.discordbot.util.BotColors;
import org.geysermc.discordbot.util.MessageHelper;

import java.util.List;

public class NewReleaseCommand extends FilteredSlashCommand {

    private static final String VERSION_REGEX = "^[0-9]+\\.[0-9]+(\\.[0-9]+)?$";

    public NewReleaseCommand() {
        this.name = "newrelease";
        this.arguments = "<version> <preview> <viaversion>";
        this.help = "Check Geyser support status for the latest Java release";
        this.guildOnly = false;

        this.options = List.of(
                new OptionData(OptionType.STRING, "version", "The latest Java release (e.g. 1.21.9)", true),
                new OptionData(OptionType.BOOLEAN, "preview", "Has the Geyser Preview released for this version?", true),
                new OptionData(OptionType.BOOLEAN, "viaversion", "Have ViaVersion + ViaBackwards updated for this version?", true)
        );
    }

    @Override
    protected void executeFiltered(SlashCommandEvent event) {
        String version = event.getOption("version").getAsString();
        boolean preview = event.getOption("preview").getAsBoolean();
        boolean viaversion = event.getOption("viaversion").getAsBoolean();

        if (!isValidVersion(version)) {
            event.replyEmbeds(MessageHelper.errorResponse(null, "Invalid version format",
                    "The version must be in the format `X.X` or `X.X.X` (e.g. 1.21 or 1.21.9)."))
                    .setEphemeral(true)
                    .queue();
            return;
        }

        event.replyEmbeds(handle(version, preview, viaversion)).queue();
    }

    @Override
    protected void execute(CommandEvent event) {
        String[] args = event.getArgs().split(" ");
        if (args.length < 3) {
            MessageHelper.errorResponse(event, "Invalid usage",
                    "Usage: `" + event.getPrefix() + name + " <version> <preview:true/false> <viaversion:true/false>`");
            return;
        }

        String version = args[0];
        if (!isValidVersion(version)) {
            MessageHelper.errorResponse(event, "Invalid version format",
                    "The version must be in the format `X.X` or `X.X.X` (e.g. 1.21 or 1.21.9).");
            return;
        }

        boolean preview = Boolean.parseBoolean(args[1]);
        boolean viaversion = Boolean.parseBoolean(args[2]);

        event.getMessage().replyEmbeds(handle(version, preview, viaversion)).queue();
    }

    private boolean isValidVersion(String version) {
        return version.matches(VERSION_REGEX);
    }

    private MessageEmbed handle(String version, boolean preview, boolean viaversion) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Geyser Support for " + version);

        String message;

        if (!preview && !viaversion) {
            message = "Geyser does not currently support " + version + ", please wait patiently!";
        } else if (preview && !viaversion) {
            message = "You can use the Geyser Preview at <#1230530815918866453> to support " + version + ".\n" +
                    "On aternos, to get the preview, install \"GeyserMC Preview\" from the plugins or mods tab.";
        } else if (preview && viaversion) {
            message = "You can use the Geyser Preview at <#1230530815918866453> to support " + version + ".\n" +
                    "On aternos, to get the preview, install \"GeyserMC Preview\" from the plugins or mods tab.\n\n" +
                    "Alternatively, you can use ViaVersion + ViaBackwards on release Geyser builds, but keep in mind, " +
                    "due to how ViaBackwards works, " + version + " features will show with hacky workarounds on Bedrock. " +
                    "Such as copper golems being a frog named \"Copper Golem\" while 1.21.9 was still in development.";
        } else { // only viaversion true
            message = "You can use ViaVersion + ViaBackwards on release Geyser builds, but keep in mind, " +
                    "due to how ViaBackwards works, " + version + " features will show with hacky workarounds on Bedrock. " +
                    "Such as copper golems being a frog named \"Copper Golem\" while 1.21.9 was still in development.";
        }

        embed.setDescription(message);
        embed.setColor(BotColors.SUCCESS.getColor());
        return embed.build();
    }
}
