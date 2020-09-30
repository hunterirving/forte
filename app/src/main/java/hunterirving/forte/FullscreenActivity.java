package hunterirving.forte;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class FullscreenActivity extends AppCompatActivity {
    final String[][] appPairs =
            {
                    {"ATLAS","com.google.android.apps.maps"},
                    {"CAMERA","com.google.android.GoogleCamera"},
                    {"TELEPHONE","com.google.android.dialer"},
                    {"TELEGRAPH","com.google.android.apps.messaging"}
            };

    int chunkSize = 85; //
    float maxPos = chunkSize * appPairs.length; // 320
    float pos = 0; //0-(maxPos-1) (position within UI bounds)
    int index = 0; //0-(appPairs.length-1) (index of selected element in appPairs)

    float lastKnownY = 0;
    float yDelta = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        final TextView UI_container = findViewById(R.id.UI_container);
        final TextView selected_item = findViewById(R.id.selected_item);
        //Initialize UI
        String UI_backgroundString = "";
        for (int i = 0; i < (appPairs.length) - index; i++) {
            UI_backgroundString += "\n";
        }
        for (int i = 0; i < appPairs.length; i++) {
            UI_backgroundString += appPairs[i][0] + "\n";
        }
        for (int i = 0; i < index; i++) {
            UI_backgroundString += "\n";
        }

        UI_container.setText(UI_backgroundString);
        selected_item.setText(appPairs[index][0]);

        final FrameLayout Frame = findViewById(R.id.Frame);

        Frame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    lastKnownY = event.getY();
                    return true;

                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    float newY = event.getY(); //get most recent Y position
                    yDelta = lastKnownY - newY; //determine delta from last known position
                    lastKnownY = newY; //store most recent Y position

                    //Add newest movement delta to position
                    float unclampedPos = pos + yDelta;

                    //sanity check (clamp pos within UI bounds)
                    if (unclampedPos < 0) {
                        pos = 0;
                    } else if (unclampedPos >= maxPos) {
                        pos = maxPos - 1;
                    } else {
                        pos = unclampedPos;
                    }

                    //determine index of item that needs to be selected
                    int prevIndex = index;
                    index = (int) (pos / chunkSize);

                    //determine if UI needs to be updated
                    if (index != prevIndex) {
                        String UI_backgroundString = "";

                        //rebuild TextView strings
                        //beginning
                        for (int i = 0; i < (appPairs.length) - index; i++) {
                            UI_backgroundString += "\n";
                        }
                        //middle
                        for (int i = 0; i < appPairs.length; i++) {
                            UI_backgroundString += appPairs[i][0] + "\n";
                        }
                        //end
                        for (int i = 0; i < index; i++) {
                            UI_backgroundString += "\n";
                        }
                        //update TextViews
                        UI_container.setText(UI_backgroundString);
                        selected_item.setText(appPairs[index][0]);

                        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibrator.vibrate(VibrationEffect.createOneShot(25, 55));
                        } else {
                            vibrator.vibrate(55);
                        }
                    }

                    return true;

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage(appPairs[index][1]);
                    startActivity(launchIntent);
                }

                return false;
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();

        pos = 0; //0-(maxPos-1) (position within UI bounds)
        index = 0; //0-(appPairs.length-1) (index of selected element in appPairs)

        lastKnownY = 0;
        yDelta = 0;

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        //Re-Initialize UI
        final TextView UI_container = findViewById(R.id.UI_container);
        final TextView selected_item = findViewById(R.id.selected_item);

        String UI_backgroundString = "";
        for (int i = 0; i < (appPairs.length) - index; i++) {
            UI_backgroundString += "\n";
        }

        for (int i = 0; i < appPairs.length; i++) {
            UI_backgroundString += appPairs[i][0] + "\n";
        }
        for (int i = 0; i < index; i++) {
            UI_backgroundString += "\n";
        }
        UI_container.setText(UI_backgroundString);
        selected_item.setText(appPairs[index][0]);

    }
}

//TODO:
//add keyboard support