package com.hoymm.root.morsecodeconverter;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hoymm.root.morsecodeconverter.InputOutputFields.AnimationForEditTextBoxes;

/**
 * Created by root on 06.05.17.
 */

public class MorseToTextSwappingPanel {

    private Context myContext;
    private ImageButton arrowButton;
    private TextView leftTextView, rightTextView;
    public static boolean convertingFromTextToMorseDirection = false;
    private ValueAnimator arrowRotateAnimation;


    public MorseToTextSwappingPanel(Context context){
        myContext = context;
        linkObjectsWithXML();
        restoreLastTextViewsStatus();
    }

    private void linkObjectsWithXML() {
        leftTextView = (TextView) getActivity().findViewById(R.id.left_text_swap_id);
        rightTextView = (TextView) getActivity().findViewById(R.id.right_text_swap_id);
        arrowButton = (ImageButton) getActivity().findViewById(R.id.swap_button_id);
    }

    private Context getContext(){
        return myContext;
    }

    private void restoreLastTextViewsStatus() {
        restoreTags();
        restoreTexts();
    }

    private void restoreTags() {
        String textTag = this.getContext().getString(R.string.text_tag);
        String morseTag = this.getContext().getString(R.string.morse_tag);
        if(isLastTranslationFromMorseToText()) {
            leftTextView.setTag(morseTag);
            rightTextView.setTag(textTag);
        }
        else{
            leftTextView.setTag(textTag);
            rightTextView.setTag(morseTag);
        }
    }

    private void restoreTexts() {
        String textText = this.getContext().getString(R.string.text);
        String morseText = this.getContext().getString(R.string.morse);
        if(isLastTranslationFromMorseToText()) {
            leftTextView.setText(morseText);
            rightTextView.setText(textText);
        }
        else{
            leftTextView.setText(textText);
            rightTextView.setText(morseText);
        }
    }

    private boolean isLastTranslationFromMorseToText(){
        SharedPreferences sharedPref = ((Activity)myContext).getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getBoolean(getIsTranslationFromMorseToTextKey(), true);
    }

    public void swapTags(){
        String leftTag = leftTextView.getTag().toString();
        String rightTag = rightTextView.getTag().toString();
        leftTextView.setTag(rightTag);
        rightTextView.setTag(leftTag);
    }

    public void swapTexts(){
        String leftText = leftTextView.getText().toString();
        String rightText = rightTextView.getText().toString();
        leftTextView.setText(rightText);
        rightTextView.setText(leftText);
    }

    public void saveToSharedPreferencesReversedTranslationDirection() {
        SharedPreferences sharedPref = ((Activity)this.getContext()).getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        boolean putTo = !isTranslationFromTextToMorse();
        editor.putBoolean(getIsTranslationFromMorseToTextKey(), putTo);
        editor.apply();
    }

    private String getIsTranslationFromMorseToTextKey(){
        return this.getContext().getString(R.string.isTranslationFromTextToMorse);
    }

    private boolean isTranslationFromTextToMorse() {
        return leftTextView.getTag().equals(this.getContext().getString(R.string.text_tag));
    }

    public boolean rotateArrowAnimation(){
        if(arrowRotateAnimation != null && arrowRotateAnimation.isRunning())
            return false;

        arrowRotateAnimation = ValueAnimator.ofFloat(arrowButton.getRotation(), arrowButton.getRotation() + 180F);
        arrowRotateAnimation.setDuration(AnimationForEditTextBoxes.animationTime);
        arrowRotateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        arrowRotateAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                arrowButton.setRotation(animatedValue);
            }
        });
        arrowRotateAnimation.start();
        return true;
    }

    private Activity getActivity(){
        return ((Activity)myContext);
    }
}