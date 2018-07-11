package com.example.swaroopsrinivasan.safety_app.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.swaroopsrinivasan.safety_app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by swaroop.srinivasan on 7/1/18.
 */

public class CustomEditTextDialog extends Dialog {
    @BindView(R.id.et_input_user_dialog) EditText et_user_input_dialog;
    @BindView(R.id.dialog_positive_button) Button btn_positive_dialog;
    @BindView(R.id.dialog_negative_button) Button btn_negative_dialog;


    public CustomEditTextDialog(Context context, View.OnClickListener positiveBtnClickListener, View.OnClickListener negativeBtnClickListener) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.input_dialog_view);
        ButterKnife.bind(this);
        btn_positive_dialog.setOnClickListener(positiveBtnClickListener);
        btn_negative_dialog.setOnClickListener(negativeBtnClickListener);
    }

    public String getUserText() {
        return et_user_input_dialog.getText().toString();
    }
}
