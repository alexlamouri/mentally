package com.pacman.MentAlly.ui.bipolar;

public class bipolarQuestionModel {

    public String[] mQues = {
            "1. At times I am much more talkative or speak much faster than usual.",
            "2. There have been times when I was much more active or did many more things than usual.",
            "3. I get into moods where I feel very speeded up or irritable.",
            "4. There have been times when I have felt both high (elated) and low (depressed) at the same time.",
            "5. At times I have been much more interested in sex than usual.",
            "6. My self-confidence ranges from great self- doubt to equally great overconfidence.",
            "7. There have been GREAT variations in the quantity or quality of my work.",
            "8. For no apparent reason I sometimes have been VERY angry or hostile.",
            "9. I have periods of mental dullness and other periods of very creative thinking.",
            "10. At times I am greatly interested in being with people and at other times I just want to be left alone with my thoughts.",
            "11. I have had periods of great optimism and other periods of equally great pessimism.",
            "12. I have had periods of tearfulness and crying and other times when I laugh and joke excessively."
    };

    private String[][] mChoices = {
            {"Not at all", "Just a little", "Somewhat", "Moderately", "Quite a lot", "Very much"},
            {"Not at all", "Just a little", "Somewhat", "Moderately", "Quite a lot", "Very much"},
            {"Not at all", "Just a little", "Somewhat", "Moderately", "Quite a lot", "Very much"},
            {"Not at all", "Just a little", "Somewhat", "Moderately", "Quite a lot", "Very much"},
            {"Not at all", "Just a little", "Somewhat", "Moderately", "Quite a lot", "Very much"},
            {"Not at all", "Just a little", "Somewhat", "Moderately", "Quite a lot", "Very much"},
            {"Not at all", "Just a little", "Somewhat", "Moderately", "Quite a lot", "Very much"},
            {"Not at all", "Just a little", "Somewhat", "Moderately", "Quite a lot", "Very much"},
            {"Not at all", "Just a little", "Somewhat", "Moderately", "Quite a lot", "Very much"},
            {"Not at all", "Just a little", "Somewhat", "Moderately", "Quite a lot", "Very much"},
            {"Not at all", "Just a little", "Somewhat", "Moderately", "Quite a lot", "Very much"},
            {"Not at all", "Just a little", "Somewhat", "Moderately", "Quite a lot", "Very much"}
    };

    public String getQuestion(int i){
        String question = mQues[i];
        return question;
    }

    public String getChoiceA(int i){
        String choiceA = mChoices[i][0];
        return choiceA;
    }

    public String getChoiceB(int i){
        String choiceB = mChoices[i][1];
        return choiceB;
    }

    public String getChoiceC(int i){
        String choiceC = mChoices[i][2];
        return choiceC;
    }

    public String getChoiceD(int i){
        String choiceD = mChoices[i][3];
        return choiceD;
    }

    public String getChoiceE(int i){
        String choiceE = mChoices[i][4];
        return choiceE;
    }

    public String getChoiceF(int i){
        String choiceF = mChoices[i][5];
        return choiceF;
    }

    public int getLength(){
        int len = mQues.length;
        return len;
    }
}
