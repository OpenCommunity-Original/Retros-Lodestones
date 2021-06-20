package com.gitlab.retropronghorn.retroslodestones.utils;

import com.gitlab.retropronghorn.retroslodestones.RetrosLodestones;
import org.bukkit.inventory.ItemStack;

public class StringColorParser {

    public static boolean unifiedColor = true;

    public static String uuidToColor(String uuid){
        StringBuilder build = new StringBuilder();
        for(int i = 0; i < uuid.length(); i++){
            if(uuid.charAt(i)!='-')
                build.append("§").append(uuid.charAt(i));
            if(unifiedColor)
                build.append("§5");
        }
        return build.toString();
    }

    public static String getUUID(RetrosLodestones inst, ItemStack stack){
        String lang = inst.getLanguageConfig().getString("compass-bound");
        String uuidString = null;
        if(stack.getItemMeta().getLore()==null)
            return null;
        for(String s : stack.getItemMeta().getLore()) {
            if (s.contains(lang))
                uuidString = s;
        }
        if(uuidString!=null){
            String[] sub = uuidString.split(lang);
            String uuid = sub[0].replace("§", "");
            if(unifiedColor)
                uuid = uuid.substring(0, uuid.length()-1);
            uuidString = new StringBuilder(uuid)
                    .insert(8, "-").insert(13, "-").insert(18, "-").insert(23, "-").toString();
        }
        return uuidString;
    }

    public static String getLocationString(RetrosLodestones inst, ItemStack stack){
        String lang = inst.getLanguageConfig().getString("compass-bound");
        String locString = null;
        if(stack.getItemMeta().getLore()==null)
            return null;
        for(String s : stack.getItemMeta().getLore()) {
            if (s.contains(lang))
                locString = s;
        }
        if(locString!=null){
            String[] sub = locString.split(lang);
            locString = sub[1];
        }
        return locString;
    }
}
