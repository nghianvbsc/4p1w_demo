package vinaedu.a4pics1word.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.github.siyamed.shapeimageview.RoundedImageView;

import vinaedu.a4pics1word.R;

/**
 * Created by bscenter on 14/08/2016
 */
public class GrViewPictureAdapter extends BaseAdapter {

    private Context mContext;
    private Integer[] mPics;

    public GrViewPictureAdapter(Context context, Integer[] pics) {
        mContext = context;
        mPics = pics;
    }

    @Override
    public int getCount() {
        return mPics == null ? 0 : mPics.length;
    }

    @Override
    public Integer getItem(int i) {
        return mPics[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View newView = view;
        ViewHolder viewHolder;
        if (newView == null) {
            newView = LayoutInflater.from(mContext).inflate(R.layout.item_gridview_picture, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.mRoundedImageView = (RoundedImageView) newView.findViewById(R.id.rdiPicture);
            newView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) newView.getTag();
        }

        viewHolder.mRoundedImageView.setImageDrawable(ContextCompat.getDrawable(mContext, getItem(i)));

        return newView;
    }

    class ViewHolder {
        private RoundedImageView mRoundedImageView;
    }
}
