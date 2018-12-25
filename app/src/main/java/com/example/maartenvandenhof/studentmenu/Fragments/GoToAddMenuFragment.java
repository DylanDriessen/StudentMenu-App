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
    private LinearLayout mLayout;
    private EditText name;
    private EditText price;
    private Button mButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_menu, container, false);

        mLayout = (LinearLayout) v.findViewById(R.id.addIngredientToMenu);
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
                for( int i = 0; i < mLayout.getChildCount(); i++) {
                    if (mLayout.getChildAt(i) instanceof TextView) {
                        if (((TextView) mLayout.getChildAt(i)).getText().equals(name.getText().toString().trim())) {
                            ingredientExists = true;
                        }
                    }
                }
                if (!ingredientExists){
                    mLayout.addView(createNewTextView(name.getText().toString()));
                    mLayout.addView(createNewTextView(price.getText().toString()));
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
        return textView;
    }
}
