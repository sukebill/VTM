package com.threemenstudio.utilities;


public class Unused {
    /*private void createDisciplineLayout(String discipline) {
        LinearLayout content = (LinearLayout) findViewById(R.id.content);
        //create this layout
        //LinearLayout
        //--LinearLayout
        //  --TextView
        //--LinearLayout
        //  --LinearLayout
        //    --RadioButton*6
        //  --LinearLayout
        //    --RadioButton*6
        //parent linear layout
        LinearLayout llParent = new LinearLayout(this);
        llParent.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
        llParent.setOrientation(LinearLayout.HORIZONTAL);
        llParent.setPadding(0,0,0,10);
        //1st child linear layout
        LinearLayout llChild1st = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                0.4f);
        lp.gravity = Gravity.CENTER_VERTICAL;
        llChild1st.setLayoutParams(lp);
        llChild1st.setOrientation(LinearLayout.HORIZONTAL);
        //textview child of 1st child linear layout
        TextView textView = new TextView(this);
        textView.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        Gravity.CENTER_VERTICAL));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setTextAppearance(android.R.style.TextAppearance_Large);
        }
        else{
            textView.setTextAppearance(this,android.R.style.TextAppearance_Large);
        }
        textView.setTextColor(ContextCompat.getColor(this, R.color.black));
        textView.setText(discipline);
        //add to its parent
        llChild1st.addView(textView);
        //2nd child linear layout
        LinearLayout llChild2nd = new LinearLayout(this);
        llChild2nd.setLayoutParams(
                new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0.6f));
        llChild2nd.setOrientation(LinearLayout.VERTICAL);
        //1st linear layout child of 2nd child linear layout
        LinearLayout llChild2ndllChild1st = new LinearLayout(this);
        llChild2ndllChild1st.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
        llChild2ndllChild1st.setOrientation(LinearLayout.HORIZONTAL);
        // radioButtons child of the 1stchild of 2nd child linear layout
        for(int i = 0; i < 6; i++){
            RadioButton radioButton = new RadioButton(this);
            radioButton.setId(i);
            //set it in its parent
            llChild2ndllChild1st.addView(radioButton);
        }
        //add 1st child in its parent
        llChild2nd.addView(llChild2ndllChild1st);
        //2nd linear layout child of 2nd child linear layout
        LinearLayout llChild2ndllChild2nd = new LinearLayout(this);
        llChild2ndllChild2nd.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
        llChild2ndllChild2nd.setOrientation(LinearLayout.HORIZONTAL);
        // radioButtons child of the 2ndchild of 2nd child linear layout
        for(int j = 6; j < 12; j++){
            RadioButton radioButton = new RadioButton(this);
            //noinspection ResourceType
            radioButton.setId(j);
            //set it in its parent
            llChild2ndllChild2nd.addView(radioButton);
        }
        llChild2nd.addView(llChild2ndllChild2nd);
        //add to parent
        llParent.addView(llChild1st);
        llParent.addView(llChild2nd);
        //add to content
        content.addView(llParent);
        //create view
        View view = new View(this);
        view.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        1));
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.border));
        content.addView(view);
    }*/
}
