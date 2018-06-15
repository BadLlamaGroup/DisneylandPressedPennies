package net.cox.mario_000.disneylandpressedpennies;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;


/**
 * Created by mario_000 on 2/1/2017.
 */

public class EmailPage extends DialogFragment implements RadioGroup.OnCheckedChangeListener {
    EditText e;
    Spinner spin;
    RadioButton feature;
    RadioButton idea;
    RadioButton complaint;
    RadioButton other;
    RadioButton mac;
    int selectedId;
    RadioGroup radioGroup;
    String reason;
    private Tracker mTracker;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(getActivity(), "Thank you for your input.", Toast.LENGTH_SHORT).show();
        this.dismiss();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.email_page, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        MainActivity application = (MainActivity) getActivity();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("Page - Email");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());


        radioGroup = (RadioGroup) view.findViewById(R.id.rdoReason);
        feature = (RadioButton) view.findViewById(R.id.rdoFeature);
        idea = (RadioButton) view.findViewById(R.id.rdoIdea);
        other = (RadioButton) view.findViewById(R.id.rdoOther);
        complaint = (RadioButton) view.findViewById(R.id.rdoComplaint);
        mac = (RadioButton) view.findViewById(R.id.rdoMac);

        Button b = (Button) view.findViewById(R.id.btn_send_email);

        radioGroup.setOnCheckedChangeListener(this);
        reason = "Feature Request";

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:bad.llama.group@gmail.com")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_SUBJECT, reason + " - " + "Pressed Coins at Disneyland");
                //intent.putExtra(Intent.EXTRA_TEXT, e.getText());
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(intent, 100);
                }

            }
        });

        return view;
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        selectedId = radioGroup.getCheckedRadioButtonId();

        switch (selectedId){
            case R.id.rdoFeature:
                reason = "Feature Request";
                break;
            case R.id.rdoIdea:
                reason = "Idea";
                break;
            case R.id.rdoMac:
                reason = "Machine Change";
                break;
            case R.id.rdoComplaint:
                reason = "Complaint";
                break;
            case R.id.rdoOther:
                reason = "Other";
                break;
        }

    }
}
