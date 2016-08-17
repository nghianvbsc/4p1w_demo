package vinaedu.a4pics1word.ui.dialogs;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import vinaedu.a4pics1word.R;
import vinaedu.a4pics1word.adapters.GrViewOutputAdapter;
import vinaedu.a4pics1word.common.interfaces.DialogNextListening;
import vinaedu.a4pics1word.common.utils.QuestionUtil;
import vinaedu.a4pics1word.model.objects.Question;
import vinaedu.a4pics1word.ui.intalizes.BaseDialog;

/**
 * Created by bscenter on 14/08/2016
 */
public class NextQuestionDialog extends BaseDialog {

    public static NextQuestionDialog nextQuestionDialog;
    private GrViewOutputAdapter mGrViewOutputAdapter;
    private GridView mGrvResult;
    private Button mBtnContinue;
    private Question mQuestion;


    @Override
    protected int getLayout() {
        return R.layout.dialog_next_question;
    }

    @Override
    protected void initViews() {
        mGrvResult = (GridView) view.findViewById(R.id.grvResult);
        mBtnContinue = (Button) view.findViewById(R.id.btnContinue);
        mBtnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof DialogNextListening) {
                    ((DialogNextListening) getActivity()).onNextQuestion();
                }
            }
        });
    }

    @Override
    protected void main() {
        if (getArguments() != null && getArguments().getSerializable("QUESTION") != null) {
            mQuestion = (Question) getArguments().getSerializable("QUESTION");
            mGrViewOutputAdapter = new GrViewOutputAdapter(getContext(),
                    QuestionUtil.createListObjectAnswer(QuestionUtil.convertStringToArraylist(mQuestion.getTrueAnswer())),
                    QuestionUtil.createListObjectAnswer(QuestionUtil.convertStringToArraylist(mQuestion.getTrueAnswer())));
            mGrvResult.setAdapter(mGrViewOutputAdapter);
            initViews();
        }

    }

    public static NextQuestionDialog getInstance() {
        if (nextQuestionDialog == null) {
            nextQuestionDialog = new NextQuestionDialog();
        }
        return nextQuestionDialog;
    }

    public static void close() {
        if (nextQuestionDialog != null) {
            nextQuestionDialog.dismiss();
            nextQuestionDialog = null;
        }
    }

    public static void showDialog(FragmentManager fragmentManager, Question question) {
        NextQuestionDialog nextQuestionDialog = getInstance();
        nextQuestionDialog.setCancelable(false);
        Bundle bundle = new Bundle();
        bundle.putSerializable("QUESTION", question);
        nextQuestionDialog.setArguments(bundle);
        nextQuestionDialog.show(fragmentManager, nextQuestionDialog.toString());
    }
}
