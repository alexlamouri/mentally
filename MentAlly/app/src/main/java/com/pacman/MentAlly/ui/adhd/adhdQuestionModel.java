package com.pacman.MentAlly.ui.adhd;

public class adhdQuestionModel {

    public String[] mQues = {
            "1. How often do you have trouble wrapping up the final details of a project, once the challenging parts have been done?",
            "2. How often do you have difficulty getting things in order when you have to do a task that requires organization?",
            "3. How often do you have problems remembering appointments or obligations?",
            "4. When you have a task that requires a lot of thought, how often do you avoid or delay getting started?",
            "5. How often do you fidget or squirm with your hands or feet when you have to sit down for a long time?",
            "6. How often do you feel overly active and compelled to do things, like you were driven by a motor?"
    };

    private String[][] mChoices = {
            {"Never", "Rarely", "Sometimes", "Often", "Very Often"},
            {"Never", "Rarely", "Sometimes", "Often", "Very Often"},
            {"Never", "Rarely", "Sometimes", "Often", "Very Often"},
            {"Never", "Rarely", "Sometimes", "Often", "Very Often"},
            {"Never", "Rarely", "Sometimes", "Often", "Very Often"},
            {"Never", "Rarely", "Sometimes", "Often", "Very Often"},
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

    public int getLength(){
        int len = mQues.length;
        return len;
    }
}
