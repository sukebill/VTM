package com.threemenstudio.data;


import android.widget.RadioButton;

public class Radio {

    private RadioButton radioButton;
    private boolean isEnabled;

    public Radio() {
    }

    public Radio(RadioButton radioButton, boolean isEnabled) {
        this.radioButton = radioButton;
        this.isEnabled = isEnabled;
    }

    public RadioButton getRadioButton() {
        return radioButton;
    }

    public void setRadioButton(RadioButton radioButton) {
        this.radioButton = radioButton;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
}
