package kz.atu.uit.afo.domain.util;

import kz.atu.uit.afo.domain.User;

public abstract class MessageHelper {
    public static String getAuthorName(User author){
        return author != null ? author.getUsername() : "none";
    }
}
