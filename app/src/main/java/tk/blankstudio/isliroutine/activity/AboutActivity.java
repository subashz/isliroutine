package tk.blankstudio.isliroutine.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

import tk.blankstudio.isliroutine.R;

/**
 * Created by deadsec on 11/26/17.
 */

public class AboutActivity extends AppCompatActivity{

    TextView aboutTextView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        aboutTextView=findViewById(R.id.aboutTextView);
        aboutTextView.setText(Html.fromHtml(getString(R.string.about_text)));
    }
}
