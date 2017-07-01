package net.darkhax.xchange;

import java.io.*;
import java.net.*;
import java.text.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.Map.*;

import org.apache.commons.io.*;

import sx.blah.discord.api.internal.json.objects.*;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.*;

public class Utilities {

    /**
     * Static reference to the line seperator on the current operating system.
     */
    public static final String SEPERATOR = System.lineSeparator();

    public static final DateTimeFormatter FORMAT_TIME_STANDARD = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * A wrapper for {@link FileUtils#copyURLToFile(URL, File)}. Allows for quick download of
     * files based on input from users.
     *
     * @param site The site/url to download the file from.
     * @param fileName The location to save the file to.
     *
     * @return The file that was downloaded.
     */
    public static File downloadFile (String site, String fileName) {

        final File file = new File(fileName);

        try {

            FileUtils.copyURLToFile(new URL(site), file);
        }

        catch (final IOException e) {

            e.printStackTrace();
        }

        return file;
    }

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
            text += line + SEPERATOR;

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

            Utilities.sendMessage(channel, "I tried to send a message, but it contained an @. I can not ping people!");
            System.out.println(message);
            System.out.println(object.description);
            return;
        }

        if (message.length() > 2000 || object.description.length() > 2000) {

            Utilities.sendMessage(channel, "I tried to send a message, but it was too long. " + message.length() + "/2000 chars! Embedded: " + object.description.length() + "/2000!");
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

            Utilities.sendMessage(channel, "I tried to send a message, but it contained an @. I can not ping people!");
            System.out.println(message);
            return null;
        }

        if (message.length() > 2000) {

            Utilities.sendMessage(channel, "I tried to send a message, but it was too long. " + message.length() + "/2000 chars!");
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

    public static String formatMessage (IMessage message) {

        String ret = "";
        if (message.getAttachments() == null || message.getAttachments().isEmpty())
            ret = String.format("[%s|%s] %s: %s", message.getTimestamp().toLocalDate(), message.getTimestamp().toLocalTime(), message.getAuthor().getName(), message.getFormattedContent());
        else
            ret = String.format("[%s|%s] %s: %s [%s]", message.getTimestamp().toLocalDate(), message.getTimestamp().toLocalTime(), message.getAuthor().getName(), message.getFormattedContent(), formatAttachments(message.getAttachments()));
        final List<IEmbed> embeds = message.getEmbedded();
        if (embeds != null && !embeds.isEmpty()) {
            ret += " {";
            for (final IEmbed embed : embeds) {
                String emb = "[" + embed.getDescription();
                final List<IEmbed.IEmbedField> embedFields = embed.getEmbedFields();
                if (embedFields != null && !embedFields.isEmpty())
                    for (final IEmbed.IEmbedField field : embedFields)
                        emb += "|" + field.getValue();
                emb += "]";
                ret += emb;
            }
            ret += "}";
        }
        return ret;
    }

    public static String formatAttachments (List<IMessage.Attachment> attachments) {

        String s = "";
        for (int i = 0, attachmentsSize = attachments.size(); i < attachmentsSize; i++) {
            final IMessage.Attachment attachment = attachments.get(i);
            s += attachment.getFilename();
            s += "|" + humanReadableByteCount(attachment.getFilesize(), true);
            s += "(" + attachment.getUrl() + ")";
            if (i != attachmentsSize - 1)
                s += ", ";
        }
        return s;

    }

    public static String humanReadableByteCount (long bytes, boolean si) {

        final int unit = si ? 1000 : 1024;
        if (bytes < unit)
            return bytes + " B";
        final int exp = (int) (Math.log(bytes) / Math.log(unit));
        final String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    public static boolean isPrivateMessage (IMessage message) {

        return message.getGuild() == null;
    }

    public static <K, V> String mapToString (Map<K, V> map) {

        String output = "";

        for (final Entry<K, V> entry : map.entrySet())
            output += entry.getKey().toString() + " - " + entry.getValue().toString() + SEPERATOR;

        return output;
    }

    public static String formatTime (LocalDateTime time) {

        return time.format(FORMAT_TIME_STANDARD).toString();
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

    public static String getPercent (long l1, long l2) {

        final double ratio = l1 / (double) l2;

        if (ratio < 0.001d)
            return "<0.1%";

        final DecimalFormat percentFormat = new DecimalFormat("#.#%");
        return percentFormat.format(ratio);
    }
    
    public static String readJson(String urlString) {
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(urlString).openStream()))){
            
            StringBuffer buffer = new StringBuffer();
            
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read); 

            return buffer.toString();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return "null";
    }
    
    public static String getCurrencyUrl(String id) {
        
        //TODO maybe add other currency types for comparison
        return String.format("https://api.coinmarketcap.com/v1/ticker/%s/?convert=%s", id, "USD");
    }
    
    public static String getCurrencyImage(String id) {
        
        return String.format("https://files.coinmarketcap.com/static/img/coins/32x32/%s.png", id);
    }
}