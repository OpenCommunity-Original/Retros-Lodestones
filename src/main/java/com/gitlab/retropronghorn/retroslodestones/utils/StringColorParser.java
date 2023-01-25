package com.gitlab.retropronghorn.retroslodestones.utils;

public class StringColorParser {

    public static boolean unifiedColor = true;

    public static String uuidToColor(String uuid) {
        StringBuilder build = new StringBuilder();
        for (int i = 0; i < uuid.length(); i++) {
            if (uuid.charAt(i) != '-')
                build.append("§").append(uuid.charAt(i));
            if (unifiedColor)
                build.append("§5");
        }
        return build.toString();
    }
}
