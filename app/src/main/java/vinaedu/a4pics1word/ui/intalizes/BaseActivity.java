package vinaedu.a4pics1word.ui.intalizes;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by bscenter on 13/08/2016
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int getLayout();

    protected abstract void initViews();

    protected abstract void main();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        initViews();
        main();
    }
}
