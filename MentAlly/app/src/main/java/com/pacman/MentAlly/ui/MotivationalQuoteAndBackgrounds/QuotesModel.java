package com.pacman.MentAlly.ui.MotivationalQuoteAndBackgrounds;

public class QuotesModel {
    public String[] mQuotes = {
            "One thing at a time☺",
            "You are amazing!\uD83D\uDE0D",
            "You are not alone♥",
            "Fall seven times and stand up eight\uD83D\uDCAA",
            "Believe you can and you're halfway there\uD83D\uDE0A",
            "Love yourself♥"

    };

    public String getQuote(int i) {
        String quote = mQuotes[i];
        return quote;
    }

    public int getLength(){
        int len = mQuotes.length;
        return len;
    }
}
