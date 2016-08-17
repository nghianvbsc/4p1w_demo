package vinaedu.a4pics1word.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import vinaedu.a4pics1word.R;

/**
 * Created by bscenter on 14/08/2016
 */
public class GrViewInputAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mListCharacter;

    public GrViewInputAdapter(Context context, List<String> listCharacter) {
        mContext = context;
        mListCharacter = listCharacter;
    }

    @Override
    public int getCount() {
        return mListCharacter == null ? 0 : mListCharacter.size();
    }

    @Override
    public String getItem(int i) {
        return mListCharacter.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View newView = view;
        ViewHolder viewHolder;

        if (newView == null) {
            newView = LayoutInflater.from(mContext).inflate(R.layout.item_gridview_input, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.mTextView = (TextView) newView.findViewById(R.id.tvText);
            newView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) newView.getTag();
        }

        viewHolder.mTextView.setText(getItem(i));

        return newView;
    }

    class ViewHolder {
        private TextView mTextView;
    }
}