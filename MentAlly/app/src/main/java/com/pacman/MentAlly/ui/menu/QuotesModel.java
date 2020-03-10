package com.pacman.MentAlly.ui.menu;

public class QuotesModel {
    public String[] mQuotes = {
            "One thing at a time ☺",
            "You are amazing! \uD83D\uDE0D",
            "You are not alone ♥",
            "Fall seven times and stand up eight \uD83D\uDCAA",
            "Believe you can and you're halfway there \uD83D\uDE0A",
            "Love yourself ♥",
            "Stay strong, stay positive and never give up \uD83D\uDCAA",
            "You miss 100% of the shots you don't take.",
            "We all have to start somewhere."

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
