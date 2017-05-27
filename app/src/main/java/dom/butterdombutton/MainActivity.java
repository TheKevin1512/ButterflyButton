package dom.butterdombutton;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import dom.butterdombutton.adapter.IButterdomModel;
import dom.butterdombutton.view.ButterdomButton;

public class MainActivity extends AppCompatActivity {
    private ButterdomButton button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.button = (ButterdomButton) findViewById(R.id.btnButterdom);
        this.button.addOption(R.drawable.ic_sentiment_neutral_white_24dp, ButterdomButton.NORTH);
        this.button.addOption(R.drawable.ic_sentiment_very_dissatisfied_white_24dp, ButterdomButton.WEST);
        this.button.addOption(R.drawable.ic_sentiment_very_satisfied_white_24dp, ButterdomButton.SOUTH);
        this.button.setOnOptionSelectedListener(new ButterdomButton.OnOptionSelectedListener() {
            public static final String TAG = "SELECT";

            @Override
            public void onOptionSelected(int position, Bitmap icon) {
                Log.d(TAG, "onOptionSelected: " + position);
            }
        });
        this.button.performClick();
        this.button.setCloseOnSelect(true);
    }
}
