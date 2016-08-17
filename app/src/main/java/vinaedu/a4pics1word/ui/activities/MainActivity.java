package vinaedu.a4pics1word.ui.activities;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vinaedu.a4pics1word.R;
import vinaedu.a4pics1word.common.SharedPreference;
import vinaedu.a4pics1word.common.interfaces.DialogMainGameListening;
import vinaedu.a4pics1word.common.interfaces.DialogNextListening;
import vinaedu.a4pics1word.common.interfaces.DialogReviewListening;
import vinaedu.a4pics1word.common.module.QuestionLib;
import vinaedu.a4pics1word.model.objects.Question;
import vinaedu.a4pics1word.ui.dialogs.MainGameDialog;
import vinaedu.a4pics1word.ui.dialogs.NextQuestionDialog;
import vinaedu.a4pics1word.ui.dialogs.ReviewQuestionDialog;
import vinaedu.a4pics1word.ui.intalizes.BaseActivity;

public class MainActivity extends BaseActivity
        implements DialogReviewListening, DialogNextListening, DialogMainGameListening {

    private SharedPreference mPref;

    private ProgressBar mPbLoading;
    private TextView mTvLoading;
    private int currentQuestion;
    private List<Question> questions;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {

        mPbLoading = (ProgressBar) findViewById(R.id.pbLoading);
        mTvLoading = (TextView) findViewById(R.id.tvLoading);

    }

    @Override
    protected void main() {
        mPref = SharedPreference.getInstance(this);
        if (mPref.isFirstOpenApp()) {
            mPref.addGold(100);
            mPref.setFirstOpenApp();
        }
        currentQuestion = mPref.getCurrentQuestion();
        questions = QuestionLib.getList();
        startProgressBar();

    }

    private void startProgressBar() {
        final int[] prog = {0};
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (prog[0] < 100) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    prog[0] += 3;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mPbLoading.setProgress(prog[0]);
                        }
                    });
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        openReviewQuestion();
                        hideViewLoading();
                    }
                });
            }
        }).start();

    }

    private void openReviewQuestion() {
        ReviewQuestionDialog.showDialog(getSupportFragmentManager(), questions.get(currentQuestion - 1));
    }

    private void hideViewLoading() {
        mTvLoading.setVisibility(View.GONE);
        mPbLoading.setVisibility(View.GONE);
    }

    @Override
    public void onPlay() {
        ReviewQuestionDialog.close();
        MainGameDialog.showDialog(getSupportFragmentManager(), questions.get(currentQuestion - 1));
    }

    @Override
    public void onNextQuestion() {
        NextQuestionDialog.close();
        if (currentQuestion == questions.size()) {
            currentQuestion = 1;
            Toast.makeText(MainActivity.this, "Bạn đã hoàn thành trò chơi! Chơi lại từ đầu nào....", Toast.LENGTH_SHORT).show();
            mPref.saveCurrentQuestion(currentQuestion);
            mPref.saveGold(100);
            ReviewQuestionDialog.showDialog(getSupportFragmentManager(), questions.get(currentQuestion));
        } else {
            currentQuestion++;
            mPref.saveCurrentQuestion(currentQuestion);
            MainGameDialog.showDialog(getSupportFragmentManager(), questions.get(currentQuestion - 1));
        }

    }

    @Override
    public void onAnswer(boolean resultAnswer) {
        if (resultAnswer) {
            int newGold = mPref.getGold() + 10;
            mPref.saveGold(newGold);

            MainGameDialog.close();
            NextQuestionDialog.showDialog(getSupportFragmentManager(), questions.get(currentQuestion - 1));
        } else {
            Toast.makeText(MainActivity.this, "Rất tiếc Bạn đã trả lời sai!", Toast.LENGTH_SHORT).show();
        }
    }
}
