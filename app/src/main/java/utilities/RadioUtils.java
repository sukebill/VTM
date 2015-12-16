package utilities;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;


import data.Radio;

import java.util.ArrayList;
import java.util.List;

public class RadioUtils {

    public void createRadio(final List<Radio> radios, final Context context, final String stat){
        for(int i = 0; i < radios.size(); i++){
            final int finalI = i;
            radios.get(i).getRadioButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(finalI < radios.size()-1){
                        if(radios.get(finalI +1).isEnabled()){
                            for(int j = finalI+1; j < radios.size(); j++){
                                radios.get(j).getRadioButton().setChecked(false);
                                radios.get(j).setIsEnabled(false);
                            }
                        }
                        else if(radios.get(finalI).isEnabled()){
                            for(int j = finalI; j < radios.size(); j++){
                                radios.get(j).getRadioButton().setChecked(false);
                                radios.get(j).setIsEnabled(false);
                            }
                        }
                        else{
                            radios.get(finalI).getRadioButton().setChecked(true);
                            radios.get(finalI).setIsEnabled(true);
                            for(int j = 0; j < finalI; j++){
                                radios.get(j).getRadioButton().setChecked(true);
                                radios.get(j).setIsEnabled(true);
                            }
                        }
                    }
                    else{
                        radios.get(finalI).getRadioButton().setChecked(true);
                        radios.get(finalI).setIsEnabled(true);
                        for(int j = 0; j < finalI; j++){
                            radios.get(j).getRadioButton().setChecked(true);
                            radios.get(j).setIsEnabled(true);
                        }
                    }
                    SharedPreferences sharedPref = context
                            .getSharedPreferences("com.threemenstudio.vtm.PREFERENCE_FILE_KEY",
                                    Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(stat, String.valueOf(finalI+1));
                    editor.apply();
                }
            });
        }
    }

    public List<Radio> createList(String name, View view, Context context){
        List<Radio> list = new ArrayList<>();
        for(int i = 0; i < 12; i++){
            int item = i+1;
            String radioButton = name + item;
            Radio radio = new Radio();
            radio.setIsEnabled(false);
            radio.setRadioButton((RadioButton) view.findViewById(context.getResources().getIdentifier(radioButton
                    , "id", context.getPackageName())));
            list.add(radio);
        }
        return list;
    }

    public void setRadioButton(List<Radio> radios, Context context, String stat){
        SharedPreferences sharedPref = context
                .getSharedPreferences("com.threemenstudio.vtm.PREFERENCE_FILE_KEY",
                        Context.MODE_PRIVATE);
        int level = Integer.parseInt(sharedPref.getString(stat,"0"));
        Log.w(stat,level+"");
        if(level > 0){
            for(int i = 0; i < level; i++){
                radios.get(i).getRadioButton().setChecked(true);
                radios.get(i).setIsEnabled(true);
            }
        }
    }
}
