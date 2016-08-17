package vinaedu.a4pics1word.ui.dialogs;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import vinaedu.a4pics1word.R;
import vinaedu.a4pics1word.adapters.GrViewInputAdapter;
import vinaedu.a4pics1word.adapters.GrViewOutputAdapter;
import vinaedu.a4pics1word.adapters.GrViewPictureAdapter;
import vinaedu.a4pics1word.common.SharedPreference;
import vinaedu.a4pics1word.common.interfaces.DialogMainGameListening;
import vinaedu.a4pics1word.common.utils.QuestionUtil;
import vinaedu.a4pics1word.model.objects.ItemOutput;
import vinaedu.a4pics1word.model.objects.Question;
import vinaedu.a4pics1word.ui.intalizes.BaseDialog;

/**
 * Created by bscenter on 14/08/2016
 */
public class MainGameDialog extends BaseDialog {

    public static MainGameDialog mainGameDialog;

    private GrViewPictureAdapter mGrViewPictureAdapter;
    private GrViewOutputAdapter mGrViewOutputAdapter;
    private GrViewInputAdapter mGrViewInputAdapter;

    private GridView mGrvPictures;
    private GridView mGrvOutput;
    private GridView mGrvInput;
    private TextView mTvGold;

    private Question mQuestion;

    private int lengthTrueAnswer;
    public ArrayList<ItemOutput> answer;
    public static HashMap<String, String> relationInoutAndOutClick = new HashMap<>();

    @Override
    protected int getLayout() {
        return R.layout.dialog_main_game;
    }

    @Override
    protected void initViews() {
        mGrvPictures = (GridView) view.findViewById(R.id.grvPictures);
        mGrvOutput = (GridView) view.findViewById(R.id.grvOutput);
        mGrvInput = (GridView) view.findViewById(R.id.grvInput);
        mTvGold = (TextView) view.findViewById(R.id.tvGold);
    }

    @Override
    protected void main() {
        mTvGold.setText(SharedPreference.getInstance(getContext()).getGold() + "");
        if (getArguments() != null && getArguments().getSerializable("QUESTION") != null) {
            mQuestion = (Question) getArguments().getSerializable("QUESTION");
            initAllData();

            mGrViewPictureAdapter = new GrViewPictureAdapter(getActivity(), mQuestion.getListPicture());
            mGrvPictures.setAdapter(mGrViewPictureAdapter);

            mGrViewOutputAdapter = new GrViewOutputAdapter(getActivity(),
                    answer,
                    QuestionUtil.createListObjectAnswer(QuestionUtil.convertStringToArraylist(mQuestion.getTrueAnswer())));

            mGrvOutput.setAdapter(mGrViewOutputAdapter);

            mGrViewInputAdapter = new GrViewInputAdapter(getActivity(),
                    QuestionUtil.convertStringToArraylist(QuestionUtil.randomListTextForChoise(mQuestion.getTrueAnswer())));
            mGrvInput.setAdapter(mGrViewInputAdapter);

            setListenning();

        }

    }

    private void initAllData() {
        lengthTrueAnswer = mQuestion.getTrueAnswer().length();
        answer = QuestionUtil.createListAnswer(lengthTrueAnswer);
    }

    private void setListenning() {
        mGrvInput.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int countCharacterChoise = 0;
                for (ItemOutput chracterChoiseOld : answer) {
                    if (!chracterChoiseOld.getCharacter().trim().equals("")) {
                        countCharacterChoise++;
                    }
                }

                if (countCharacterChoise == lengthTrueAnswer) {
                    return;
                }

                mGrvInput.getChildAt(position).setVisibility(View.INVISIBLE);

                //TODO: pass data from input to outputvTextt
                String characterSelect = ((TextView) mGrvInput.getChildAt(position).findViewById(R.id.tvText)).getText().toString().trim();
                Log.d("CHARACTER CHOISE", " character choise is " + characterSelect);

                int index = 0;
                boolean isAddFirst = false;
                for (index = 0; index < answer.size(); index++) {
                    ItemOutput chracterChoiseOld = answer.get(index);
                    if (chracterChoiseOld.getCharacter().trim().equals("")) {
                        isAddFirst = true;
                        answer.remove(index);

                        ItemOutput itemOutPut = new ItemOutput();
                        itemOutPut.setCharacter(characterSelect);

                        answer.add(index, itemOutPut);
                        relationInoutAndOutClick.put(String.valueOf(index), String.valueOf(position));
                        mGrViewOutputAdapter.notifyDataSetChanged();
                        break;
                    }
                }

                if (!isAddFirst) {
                    relationInoutAndOutClick.put(String.valueOf(answer.size()), String.valueOf(position));

                    ItemOutput itemOutPut = new ItemOutput();
                    itemOutPut.setCharacter(characterSelect);

                    answer.add(itemOutPut);
                    mGrViewOutputAdapter.notifyDataSetChanged();
                }


                //TODO: check answer is true
                String answerOnOutput = "";
                for (ItemOutput itemOutPut : answer) {
                    answerOnOutput += itemOutPut.getCharacter();
                }

                if (mQuestion.getTrueAnswer().equals(answerOnOutput.trim())) {
                    if (getActivity() instanceof DialogMainGameListening) {
                        ((DialogMainGameListening) getActivity()).onAnswer(true);
                    }
                } else {
                    // TODO: check enought character notify answer fasle;
                    countCharacterChoise = 0;
                    for (ItemOutput itemOutPut : answer) {
                        if (!itemOutPut.getCharacter().trim().equals("")) {
                            countCharacterChoise++;
                        }
                    }
                    if (countCharacterChoise == lengthTrueAnswer) {
                        if (getActivity() instanceof DialogMainGameListening) {
                            ((DialogMainGameListening) getActivity()).onAnswer(false);
                        }
                    }
                }


            }
        });


        mGrvOutput.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (answer.size() > position && !"".equals(answer.get(position).getCharacter().trim())) {

                    answer.remove(position);
                    ItemOutput itemOutPut = new ItemOutput();
                    itemOutPut.setCharacter("");
                    answer.add(position, itemOutPut);

                    mGrViewOutputAdapter.notifyDataSetChanged();
                    int positionRelationInCharacterListInout = Integer.parseInt(relationInoutAndOutClick.get(String.valueOf(position)));
                    mGrvInput.getChildAt(positionRelationInCharacterListInout).setVisibility(View.VISIBLE);
                    relationInoutAndOutClick.remove(String.valueOf(position));
                }
            }
        });
    }

    public static MainGameDialog getInstance() {
        if (mainGameDialog == null) {
            mainGameDialog = new MainGameDialog();
        }
        return mainGameDialog;
    }

    public static void close() {
        if (mainGameDialog != null) {
            mainGameDialog.dismiss();
            mainGameDialog = null;
        }
    }

    public static void showDialog(FragmentManager fragmentManager, Question question) {
        MainGameDialog mainGameDialog = getInstance();
        mainGameDialog.setCancelable(false);
        Bundle bundle = new Bundle();
        bundle.putSerializable("QUESTION", question);
        mainGameDialog.setArguments(bundle);
        mainGameDialog.show(fragmentManager, mainGameDialog.toString());
    }
}
