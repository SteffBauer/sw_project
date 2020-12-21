package de.othr.sw.bank.utils;

import de.othr.sw.bank.entity.WebsiteMessage;
import de.othr.sw.bank.entity.WebsiteMessageType;
import org.springframework.ui.Model;

public class WebsiteMessageUtils {

    public static String showWebsiteMessage(Model model, WebsiteMessageType type, String title, String message) {
        WebsiteMessage msg = new WebsiteMessage(type, title, message);
        model.addAttribute("message", msg);
        return "messages";
    }
}

