package cisc181.bustinbricks;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        TextView text = (TextView) findViewById(R.id.score);

        intent = getIntent();
        int score = intent.getIntExtra("Score", 0);
        SharedPreferences sharedPref = getSharedPreferences("high_score", Context.MODE_PRIVATE);
        int defaultValue = 0;
        int highscore = sharedPref.getInt("saved_high_score", defaultValue);
        //Log.e("heee", Integer.toString(highscore));
        if (score > highscore) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("saved_high_score", score);
            editor.commit();
        }
        TextView text1 = (TextView) findViewById(R.id.highscore);
        text1.setText("Highscore: " + highscore);
        if(score < AnimatedView.numBricks / 2) {
            text.setText(score + "/" + AnimatedView.numBricks + "\nNot so good...");
        }
        else if(score < AnimatedView.numBricks * (3 / 4)) {
            text.setText(score + "/" + AnimatedView.numBricks + "\nAcceptable... I guess.");
        }
        else if(score < AnimatedView.numBricks) {
            text.setText(score + "/" + AnimatedView.numBricks + "\nYou're pretty good.");
        }
        else if(score == AnimatedView.numBricks) {
            text.setText(score + "/" + AnimatedView.numBricks + "\nA perfect score. Amazing!");
        }

    }

    public void returnToStart(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
}
