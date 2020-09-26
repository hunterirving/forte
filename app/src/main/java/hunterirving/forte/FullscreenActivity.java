package hunterirving.forte;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FullscreenActivity extends AppCompatActivity {


    final String[][] appPairs =
            {
                    {"ATLAS","com.google.android.apps.maps"},
                    {"CAMERA","com.google.android.GoogleCamera"},
                    {"TELEPHONE","com.google.android.dialer"},
                    {"TELEGRAPH","com.google.android.apps.messaging"}
            };

    int chunkSize = 100; //
    float maxPos = chunkSize * appPairs.length; // 320
    float pos = 0; //0->320, position in UI land
    int index; // 0->(appPairs.length)

    float lastKnownY = 0; //0 until first ACTION_DOWN event.
    float yDelta = 0; // (most recent ACTION_MOVE position - lastKnownY) = Y delta.. (used to update pos)

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

        final TextView UI_container = findViewById(R.id.UI_container); //maybe should have a view to hold this and all else?
        final TextView selected_item = findViewById(R.id.selected_item);
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

                    //invert direction?


                    //determine index of item that needs to be selected
                    index = (int) (pos / chunkSize); //0, 1, 2, or 3

                    System.out.println("pos: " + pos);
                    System.out.println("maxPos: " + maxPos);
                    System.out.println("index: " + index);


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
                    System.out.println("UI_backgroundString: " + UI_backgroundString);

                    //update TextViews
                    UI_container.setText(UI_backgroundString);
                    selected_item.setText(appPairs[index][0]);

                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //launch the appropriate app

                    //using POS and APPPAIRS.LENGTH
                    //determine the currently selected element in APPPAIRS, then launch it

                }

                return false;

            }
        });





        //TODO:
        //invert controls
        //add sounds and vibration to inform navigation
        //reintroduce launcher capabilities
        //handle exiting/pausing the app

        //DONE:
        //consider re-implementing volume controls (considered, but not implemented)
        //improved dragging based on touch deltas..?
        //use TextView instead of ImageView (faster)
        //changed green title bar to black
        //add onTouchListener to view to listen for MotionEvents
        //update menu when dragged away from starting point passed a threshold
        //make text smaller (inkscape)
        //make it actually launch the appropriate apps... touchdown, drag, release
        //export an APK !!!
        //make it "a launcher"
        //always start on ATLAS (call finish() in onPause())
        //make image go behind the nav bar
        //hide status bar (and nav bar? if possible)
        //add listeners for up and down volume buttons
        //ignore the default behavior of those buttons
        //make it change the bg image when those buttons are hit
    }


}
