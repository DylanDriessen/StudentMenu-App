package com.example.maartenvandenhof.studentmenu.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maartenvandenhof.studentmenu.Activities.MainActivity;
import com.example.maartenvandenhof.studentmenu.R;

public class GoToAddMenuFragment extends Fragment {
    private LinearLayout ingredientC;
    private LinearLayout priceC;
    private EditText name;
    private EditText price;
    private Button mButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_menu, container, false);

        ingredientC = (LinearLayout) v.findViewById(R.id.addMenuIngredientColunm);
        priceC = (LinearLayout) v.findViewById(R.id.addMenuPriceColunm);
        name = (EditText) v.findViewById(R.id.menuAddIngredientName);
        price = (EditText) v.findViewById(R.id.menuAddIngredientPrice);
        mButton = (Button) v.findViewById(R.id.addIngredientButton);
        mButton.setOnClickListener(onClick());
        TextView textView = new TextView((MainActivity)getActivity());
        textView.setText("New text");


        return v;
    }

    private View.OnClickListener onClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ingredientExists = false;
                for( int i = 0; i < ingredientC.getChildCount(); i++) {
                    if (ingredientC.getChildAt(i) instanceof TextView) {
                        if (((TextView) ingredientC.getChildAt(i)).getText().equals(name.getText().toString().trim())) {
                            ingredientExists = true;
                        }
                    }
                }
                if (!ingredientExists){
                    ingredientC.addView(createNewTextView(name.getText().toString()));
                    priceC.addView(createNewTextView(price.getText().toString()));
                } else {
                    Toast t = Toast.makeText((MainActivity)getActivity(), "Ingredient already added", Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        };
    }

    private TextView createNewTextView(String text) {
        final TextView textView = new TextView((MainActivity)getActivity());
        textView.setText(text);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setTextSize(20);
        return textView;
    }
}
