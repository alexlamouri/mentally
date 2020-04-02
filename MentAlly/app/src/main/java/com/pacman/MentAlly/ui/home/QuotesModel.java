package com.pacman.MentAlly.ui.home;

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
            "We all have to start somewhere.",
            "Victory is always possible for the person who refuses to stop fighting.",
            "Breathe. Take care. Stand still for a minute. What you are looking for might just be looking for you too.",
            "Wherever you are, be all there.",
            "Never dull your shine for somebody else.",
            "In any given moment we have two options: to step forward into growth or back into safety.",
            "Today, you have 100% of your life left.",
            "Forget all the reasons why it won't work and believe the one reason why it will.",
            "The master has failed more times than the beginner has even tried.",
            "Going back isn't an option. Standing still is not enough.",
            "Look for something positive in each day, even if some days you have to look a little harder.",
            "Be strong because thing will get better. It may be stormy now, but it never rains forever.",
            "When you can't find sunshine, be the sunshine.",
            "Every day is a new beginning. Take a deep breath, smile and start again",
            "If you never try, you will never know.",
            "Don't Quit.",
            "Don't give up. Great things take time.",
            "You are wonderful as you are.",
            "If you stumble, you are still moving forward."

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
