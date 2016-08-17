package vinaedu.a4pics1word.model.objects;

import java.io.Serializable;

/**
 * Created by bscenter on 14/08/2016
 */
public class Question implements Serializable {
    private Integer[] listPicture;
    private String trueAnswer;

    public Integer[] getListPicture() {
        return listPicture;
    }

    public void setListPicture(Integer[] listPicture) {
        this.listPicture = listPicture;
    }

    public String getTrueAnswer() {
        return trueAnswer;
    }

    public void setTrueAnswer(String trueAnswer) {
        this.trueAnswer = trueAnswer;
    }
}
