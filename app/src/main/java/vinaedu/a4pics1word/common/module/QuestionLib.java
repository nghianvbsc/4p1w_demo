package vinaedu.a4pics1word.common.module;

import java.util.ArrayList;
import java.util.List;

import vinaedu.a4pics1word.R;
import vinaedu.a4pics1word.model.objects.Question;

/**
 * Created by bscenter on 14/08/2016
 */
public class QuestionLib {

    public static List<Question> getList() {
        List<Question> questions = new ArrayList<>();

        Question question1 = new Question();
        question1.setListPicture(new Integer[]{R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four});
        question1.setTrueAnswer("BONMUA"); //bốn mùa
        questions.add(question1);

        Question question2 = new Question();
        question2.setListPicture(new Integer[]{R.drawable.thanhcong1, R.drawable.thanhcong2, R.drawable.thanhcong3, R.drawable.thanhcong4});
        question2.setTrueAnswer("THANHCONG"); //thành công
        questions.add(question2);

        return questions;
    }
}
