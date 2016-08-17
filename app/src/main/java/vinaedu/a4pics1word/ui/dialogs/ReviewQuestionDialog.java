package vinaedu.a4pics1word.ui.dialogs;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.RoundedImageView;

import vinaedu.a4pics1word.R;
import vinaedu.a4pics1word.common.SharedPreference;
import vinaedu.a4pics1word.common.interfaces.DialogReviewListening;
import vinaedu.a4pics1word.model.objects.Question;
import vinaedu.a4pics1word.ui.intalizes.BaseDialog;

/**
 * Created by bscenter on 13/08/2016
 */
public class ReviewQuestionDialog extends BaseDialog {

    public static ReviewQuestionDialog reviewQuestionDialog;
    private RoundedImageView mRdImg1;
    private RoundedImageView mRdImg2;
    private RoundedImageView mRdImg3;
    private RoundedImageView mRdImg4;
    private Button mBtnPlay;
    private Question mQuestion;
    private TextView mTvCurrentQuestion;
    private TextView mTvGold;


    @Override
    protected int getLayout() {
        return R.layout.dialog_review_question;
    }

    @Override
    protected void initViews() {
        mBtnPlay = (Button) view.findViewById(R.id.btnPlay);
        mRdImg1 = (RoundedImageView) view.findViewById(R.id.rdImg1);
        mRdImg2 = (RoundedImageView) view.findViewById(R.id.rdImg2);
        mRdImg3 = (RoundedImageView) view.findViewById(R.id.rdImg3);
        mRdImg4 = (RoundedImageView) view.findViewById(R.id.rdImg4);
        mTvCurrentQuestion = (TextView) view.findViewById(R.id.tvCurrentQuestion);
        mTvGold = (TextView) view.findViewById(R.id.tvGold);


        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof DialogReviewListening) {
                    ((DialogReviewListening) getActivity()).onPlay();
                }
            }
        });
    }

    @Override
    protected void main() {
        if (getArguments() != null && getArguments().getSerializable("QUESTION") != null) {
            mQuestion = (Question) getArguments().getSerializable("QUESTION");
            mRdImg1.setImageDrawable(ContextCompat.getDrawable(getContext(), mQuestion.getListPicture()[0]));
            mRdImg2.setImageDrawable(ContextCompat.getDrawable(getContext(), mQuestion.getListPicture()[1]));
            mRdImg3.setImageDrawable(ContextCompat.getDrawable(getContext(), mQuestion.getListPicture()[2]));
            mRdImg4.setImageDrawable(ContextCompat.getDrawable(getContext(), mQuestion.getListPicture()[3]));
            mTvCurrentQuestion.setText(SharedPreference.getInstance(getActivity()).getCurrentQuestion() + "");
            mTvGold.setText(SharedPreference.getInstance(getActivity()).getGold() + "");
        }


    }

    public static ReviewQuestionDialog getInstance() {
        if (reviewQuestionDialog == null) {
            reviewQuestionDialog = new ReviewQuestionDialog();
        }
        return reviewQuestionDialog;
    }

    public static void close() {
        if (reviewQuestionDialog != null) {
            reviewQuestionDialog.dismiss();
            reviewQuestionDialog = null;
        }
    }

    public static void showDialog(FragmentManager fragmentManager, Question question) {
        ReviewQuestionDialog reviewQuestionDialog = getInstance();
        reviewQuestionDialog.setCancelable(false);
        Bundle bundle = new Bundle();
        bundle.putSerializable("QUESTION", question);
        reviewQuestionDialog.setArguments(bundle);
        reviewQuestionDialog.show(fragmentManager, reviewQuestionDialog.toString());
    }
}
