package net.darkhax.xchange.util;

import java.util.*;

import net.darkhax.xchange.*;
import sx.blah.discord.api.internal.json.objects.*;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.*;

public class MessageUtils {

	/**
     * Creates a ping message for a user based upon their user ID.
     *
     * @param userID The user ID of the user to generate a ping message for.
     *
     * @return String A short string which will ping the specified user when sent into the
     *         chat.
     */
    public static String getPingMessage (String userID) {

        return "<@" + userID + ">";
    }

    /**
     * Makes a String message italicized. This only applies to chat.
     *
     * @param message The message to format.
     *
     * @return String The message with the formatting codes applied.
     */
    public static String makeItalic (String message) {

        return "*" + message + "*";
    }

    /**
     * Makes a String message bold. This only applies to chat.
     *
     * @param message The message to format.
     *
     * @return String The message with the bold formatting codes applied.
     */
    public static String makeBold (String message) {

        return "**" + message + "**";
    }

    /**
     * Makes a String message scratched out. This only applies to chat.
     *
     * @param message The message to format.
     *
     * @return String The message with the scratched out formatting codes applied.
     */
    public static String makeScratched (String message) {

        return "~~" + message + "~~";
    }

    /**
     * Makes a String message underlined. This only applies to chat.
     *
     * @param message The message to format.
     *
     * @return String The message with the underlined formatting codes applied.
     */
    public static String makeUnderlined (String message) {

        return "__" + message + "__";
    }

    /**
     * Makes a String message appear in a code block. This only applies to chat.
     *
     * @param message The message to format.
     *
     * @return String The message with the code block format codes applied.
     */
    public static String makeCodeBlock (String message) {

        return "`" + message + "`";
    }

    /**
     * Makes a string which represents multiple lines of text.
     *
     * @param lines The lines of text to display. Each entry is a new line.
     *
     * @return A string which has been split up.
     */
    public static String makeMultilineMessage (String... lines) {

        String text = "";

        for (final String line : lines)
            text += line + Main.SEPERATOR;

        return text;
    }

    /**
     * Makes a String message appear in a multi-lined code block. This only applies to chat.
     *
     * @param message The message to format.
     *
     * @return String The message with the multi-lined code block format codes applied.
     */
    public static String makeMultiCodeBlock (String message) {

        return "```" + message + "```";
    }

    public static String makeHyperlink (String text, String url) {

        return String.format("[%s](%s)", text, url);
    }

    public static String sanatizeMarkdown (String text) {

        return text.replace("_", "\\_").replace("*", "\\*");
    }

    /**
     * Attempts to send a private message to a user. If a private message channel does not
     * already exist, it will be created.
     *
     * @param user The user to send the private message to.
     * @param message The message to send to the user.
     */
    public static void sendPrivateMessage (IUser user, String message) {

        try {

            sendMessage(Main.instance.getOrCreatePMChannel(user), message);
        }

        catch (final Exception e) {

            e.printStackTrace();
        }
    }

    public static void sendMessage (IChannel channel, EmbedBuilder embed, int color) {

        embed.ignoreNullEmptyFields();
        embed.withColor(color);
        sendMessage(channel, embed.build());
    }

    public static void sendMessage (IChannel channel, EmbedObject object) {

        RequestBuffer.request( () -> {
            try {
                channel.sendMessage(object);
            }
            catch (DiscordException | MissingPermissionsException e) {

                e.printStackTrace();
            }
        });
    }

    public static void sendMessage (IChannel channel, String message, EmbedObject object) {

        if (message.contains("@") || object.description.contains("@")) {

            sendMessage(channel, "I tried to send a message, but it contained an @. I can not ping people!");
            System.out.println(message);
            System.out.println(object.description);
            return;
        }

        if (message.length() > 2000 || object.description.length() > 2000) {

            sendMessage(channel, "I tried to send a message, but it was too long. " + message.length() + "/2000 chars! Embedded: " + object.description.length() + "/2000!");
            System.out.println(message);
            System.out.println(object.description);
            return;
        }

        RequestBuffer.request( () -> {
            try {
                channel.sendMessage(message, object, false);
            }
            catch (DiscordException | MissingPermissionsException e) {

                e.printStackTrace();
            }
        });
    }

    public static void sendMessage (IChannel channel, String message, Object... args) {

        sendMessage(channel, String.format(message, args));
    }

    /**
     * Sends a message into the chat. This version of the method will handle exceptions for
     * you.
     *
     * @param channel The channel to send to message to.
     * @param message The message to send to the channel.
     */
    public static IMessage sendMessage (IChannel channel, String message) {

        if (message.contains("@") && !message.startsWith("I tried to send a message,")) {

            sendMessage(channel, "I tried to send a message, but it contained an @. I can not ping people!");
            System.out.println(message);
            return null;
        }

        if (message.length() > 2000) {

            sendMessage(channel, "I tried to send a message, but it was too long. " + message.length() + "/2000 chars!");
            System.out.println(message);
            return null;
        }

        RequestBuffer.request( () -> {
            try {
                return channel.sendMessage(message);
            }

            catch (MissingPermissionsException | DiscordException e) {

                e.printStackTrace();
            }

            return null;
        });

        return null;
    }
    
    public static boolean isPrivateMessage (IMessage message) {

        return message.getGuild() == null;
    }

    public static String toString (List<IRole> roles, String delimiter) {

        if (roles.isEmpty() || roles.size() == 1 && roles.get(0).isEveryoneRole())
            return "None";

        String ret = "";

        for (final IRole role : roles)
            if (!role.isEveryoneRole())
                ret += role.getName() + delimiter;
        return ret.substring(0, ret.length() - delimiter.length());
    }

    public static String userString (IUser user) {

        return user.getName() + "#" + user.getDiscriminator() + " - " + user.getID();
    }
}
