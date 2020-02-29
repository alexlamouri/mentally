package com.pacman.MentAlly.ui.depression;

public class depressionQuestionModel {

    public String[] mQues = {
            "1. Little interest or pleasure in doing things",
            "2. Feeling down, depressed, or hopeless",
            "3. Trouble falling or staying asleep, or sleeping too much",
            "4. Feeling tired or having little energy",
            "5. Poor appetite or overeating",
            "6. Feeling bad about yourself – or that you are a failure or have let yourself or your family down",
            "7. Trouble concentrating on things, such as reading the newspaper or watching television",
            "8. Moving or speaking so slowly that other people could have noticed? Or the opposite – being so fidgety or restless that you have been moving around a lot more than usual",
            "9. Thoughts that you would be better off dead or of hurting yourself in some way"
    };

    private String[][] mChoices = {
            {"Not at all sure", "Several days", "Over half the days", "Nearly every day"},
            {"Not at all sure", "Several days", "Over half the days", "Nearly every day"},
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
