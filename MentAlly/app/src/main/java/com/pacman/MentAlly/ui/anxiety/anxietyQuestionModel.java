package com.pacman.MentAlly.ui.anxiety;

public class anxietyQuestionModel {

    public String[] mQues = {
            "1. Feeling nervous, anxious, or on edge",
            "2. Not being able to stop or control worrying",
            "3. Worrying too much about different things",
            "4. Trouble relaxing",
            "5. Being so restless that it's hard to sit still",
            "6. Becoming easily annoyed or Irritable",
            "7. Feeling afraid as if something awful might happen"
    };

    private String[][] mChoices = {
            {"Not at all sure", "Several days", "Over half the days", "Nearly every day"},
            {"Not at all sure", "Several days", "Over half the days", "Nearly every day"},
            {"Not at all sure", "Several days", "Over half the days", "Nearly every day"},
            {"Not at all sure", "Several days", "Over half the days", "Nearly every day"},
            {"Not at all sure", "Several days", "Over half the days", "Nearly every day"},
            {"Not at all sure", "Several days", "Over half the days", "Nearly every day"},
            {"Not at all sure", "Several days", "Over half the days", "Nearly every day"}
    };

     public String getQuestion(int i){
         String question = mQues[i];
         return question;
     }

     public String getChoice1(int i){
         String choice1 = mChoices[i][0];
         return choice1;
     }

    public String getChoice2(int i){
        String choice2 = mChoices[i][1];
        return choice2;
    }

    public String getChoice3(int i){
        String choice3 = mChoices[i][2];
        return choice3;
    }

    public String getChoice4(int i){
        String choice4 = mChoices[i][3];
        return choice4;
    }

    public int getLength(){
         int len = mQues.length;
         return len;
    }

}
