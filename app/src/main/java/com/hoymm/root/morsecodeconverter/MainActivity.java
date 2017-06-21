package com.hoymm.root.morsecodeconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hoymm.root.morsecodeconverter.InputOutputFields.ConvertingTextFieldsPanel;

public class MainActivity extends AppCompatActivity {
    private TopBarSpeedSpinner topBarSpeedSpinner;
    private MorseToTextSwappingPanel morseToTextSwappingPanel;
    private ConvertingTextFieldsPanel convertingTextFieldsPanel;
    private PlayPauseStopButtons playPauseStopButtons;
    private MorseKeyboardPanel morseKeyboardPanel;
    private FooterPanel footerPanel;

    private ImageButton swapButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeProgramComponents();
        initializateSwapButtonAction();
        convertingTextFieldsPanel.resizeBoxesAnimation();
    }

    private void initializeProgramComponents() {
        topBarSpeedSpinner = new TopBarSpeedSpinner(this);
        morseToTextSwappingPanel = new MorseToTextSwappingPanel(this);
        convertingTextFieldsPanel = new ConvertingTextFieldsPanel(this);
        playPauseStopButtons = new PlayPauseStopButtons(this);
        morseKeyboardPanel = new MorseKeyboardPanel(this);
        footerPanel = new FooterPanel(this);
    }



    private void initializateSwapButtonAction() {
        swapButton = (ImageButton) findViewById(R.id.swap_button_id);
        swapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (convertingTextFieldsPanel.ifNoAnimationCurrentlyRunning()) {
                    MorseToTextSwappingPanel.convertTextToMorse = !MorseToTextSwappingPanel.convertTextToMorse;
                    convertingTextFieldsPanel.resizeBoxesAnimation();
                    convertingTextFieldsPanel.swapTextInsideBoxes();
                    morseToTextSwappingPanel.swapTextHeaders();
                    morseToTextSwappingPanel.saveToSharedPreferencesReversedTranslationDirection();
                    morseKeyboardPanel.hideOrShowMorsePanel();
                    Toast.makeText(getApplicationContext(),
                            "TEXT TO MORSE: " + MorseToTextSwappingPanel.convertTextToMorse, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
