package vinaedu.a4pics1word.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.RoundedImageView;

import java.util.List;

import vinaedu.a4pics1word.R;
import vinaedu.a4pics1word.model.objects.ItemOutput;

/**
 * Created by bscenter on 14/08/2016
 */
public class GrViewOutputAdapter extends BaseAdapter {

    private Context mContext;

    private List<ItemOutput> mListInput;
    private List<ItemOutput> mListTrueAnswer;

    public GrViewOutputAdapter(Context context, List<ItemOutput> listInput, List<ItemOutput> listTrueAnswer) {
        mContext = context;
        mListInput = listInput;
        mListTrueAnswer = listTrueAnswer;
    }

    @Override
    public int getCount() {
        return mListTrueAnswer == null ? 0 : mListTrueAnswer.size();
    }

    @Override
    public ItemOutput getItem(int i) {
        return mListInput.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("NewApi")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View newView = view;
        ViewHolder viewHolder;
        if (newView == null) {
            newView = LayoutInflater.from(mContext).inflate(R.layout.item_gridview_output, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.mTextView = (TextView) newView.findViewById(R.id.tvText);
            newView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) newView.getTag();
        }

        if (i > mListInput.size() - 1) {
            viewHolder.mTextView.setText("");
        } else {
            viewHolder.mTextView.setText(getItem(i).getCharacter());
        }

        return newView;
    }

    class ViewHolder {
        private TextView mTextView;
    }
}