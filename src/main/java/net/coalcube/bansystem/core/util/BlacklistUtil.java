package net.coalcube.bansystem.core.util;

import dev.dejvokep.boostedyaml.YamlDocument;

import java.util.List;

public class BlacklistUtil {

    private YamlDocument blacklist;
    public BlacklistUtil(YamlDocument blacklist) {
        this.blacklist = blacklist;
    }
    public boolean hasBlockedWordsContains(String message) {
        List<String> whitelist = blacklist.getStringList("Whitelist");

        message = message.trim();
        message = message.replaceAll("AE", "Ä");
        message = message.replaceAll("OE", "Ö");
        message = message.replaceAll("UE", "Ü");
        message = message.replaceAll("Ä", "AE");
        message = message.replaceAll("Ö", "OE");
        message = message.replaceAll("Ü", "UE");
        message = message.replaceAll("Punkt", ".");
        message = message.replaceAll("Point", ".");
        message = message.replaceAll("0", "O");
        message = message.replaceAll("1", "I");
        message = message.replaceAll("3", "E");
        message = message.replaceAll("4", "A");
        message = message.replaceAll("5", "S");
        message = message.replaceAll("8", "B");
        String[] trimmed = message.split(" ");
        String lowerMessage = message.toLowerCase();

        for (String word : blacklist.getStringList("Words")) {
            String lowerWord = word.toLowerCase();
            boolean wordFound = lowerMessage.contains(lowerWord);

            if (!wordFound) {
                for (String piece : trimmed) {
                    if (piece.equalsIgnoreCase(word)) {
                        wordFound = true;
                        break;
                    }
                }
            }

            if (wordFound) {
                boolean whitelisted = false;
                for (String whitelistRow : whitelist) {
                    if (lowerMessage.contains(whitelistRow.toLowerCase())) {
                        whitelisted = true;
                        break;
                    }
                }
                if (!whitelisted) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasAdContains(String message) {
        String rawMessage = message;
        List<String> whitelist = blacklist.getStringList("Whitelist");

        message = message.trim();
        message = message.replaceAll("0", "O");
        message = message.replaceAll("1", "I");
        message = message.replaceAll("3", "E");
        message = message.replaceAll("4", "A");
        message = message.replaceAll("5", "S");
        message = message.replaceAll("8", "B");
        message = message.replaceAll("Ä", "AE");
        message = message.replaceAll("Ö", "OE");
        message = message.replaceAll("Ü", "UE");
        message = message.replaceAll("ä", "ae");
        message = message.replaceAll("ö", "oe");
        message = message.replaceAll("ü", "ue");
        message = message.replaceAll("Punkt", ".");
        message = message.replaceAll("Point", ".");

        String[] trimmed = message.split(" ");
        String lowerMessage = message.toLowerCase();
        String lowerRawMessage = rawMessage.toLowerCase();

        for (String ad : blacklist.getStringList("Ads")) {
            String lowerAd = ad.toLowerCase();
            boolean adFound = lowerMessage.contains(lowerAd) || lowerRawMessage.contains(lowerAd);

            if (!adFound) {
                for (String word : trimmed) {
                    if (word.equalsIgnoreCase(ad)) {
                        adFound = true;
                        break;
                    }
                }
            }

            if (adFound) {
                boolean whitelisted = false;
                for (String whitelistRow : whitelist) {
                    if (lowerMessage.contains(whitelistRow.toLowerCase())) {
                        whitelisted = true;
                        break;
                    }
                }
                if (!whitelisted) {
                    return true;
                }
            }
        }
        return false;
    }
}
