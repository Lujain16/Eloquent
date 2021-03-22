package Model;

public class Listitem {
    public String Question;
    public String Answer;
    public String RecordPostion;
    //, String recordPostion
    public Listitem(String question, String answer, String recordPostion) {
        Question = question;
        Answer = answer;
        RecordPostion = recordPostion;
        //--
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getRecordPostion() {
        return RecordPostion;
    }

    public void setRecordPostion(String recordPostion) {
        RecordPostion = recordPostion;
    }
}
