package com.example.sofra.view.fragment.userCycle.userMore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sofra.R;
import com.example.sofra.data.model.contactUs.ContactUs;
import com.example.sofra.view.fragment.untitledFolder.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;


public class UserContactUsFragment extends BaseFragment {

    @BindView(R.id.user_contact_us_radio_group)
    RadioGroup userContactUsRadioGroup;
    @BindView(R.id.user_contact_us_et_name)
    EditText userContactUsEtName;
    @BindView(R.id.user_contact_us_et_mail)
    EditText userContactUsEtMail;
    @BindView(R.id.user_contact_us_et_phone)
    EditText userContactUsEtPhone;
    @BindView(R.id.user_contact_us_et_message)
    EditText userContactUsEtMessage;
    @BindView(R.id.user_contact_us_rb_complaint)
    RadioButton userContactUsRbComplaint;
    @BindView(R.id.user_contact_us_rb_suggestion)
    RadioButton userContactUsRbSuggestion;
    @BindView(R.id.user_contact_us_rb_enquiry)
    RadioButton userContactUsRbEnquiry;
    @BindView(R.id.user_contact_us_btn_send)
    Button userContactUsBtnSend;

    public UserContactUsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        setUpActivity();
        View view = inflater.inflate(R.layout.fragment_user_contact_us, container, false);
        ButterKnife.bind(this, view);

//        userContactUsRadioGroup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // get selected radio button from radioGroup
//                int selectedId = userContactUsRadioGroup.getCheckedRadioButtonId();
//                // find the radiobutton by returned id
//                userContactUsRbComplaint = view.findViewById(selectedId);
//                Toast.makeText(getActivity(), userContactUsBtnSend.getText(), Toast.LENGTH_SHORT).show();
//
//            }
//
//        });

        return view;
    }

    private void getContact() {
        String mail = userContactUsEtMail.getText().toString();
        String name = userContactUsEtName.getText().toString();
        String phone = userContactUsEtPhone.getText().toString();
        String content = userContactUsEtMessage.getText().toString();
        String type = userContactUsEtName.getText().toString();

        if (mail.isEmpty()) {
            Toast.makeText(getActivity(), "Please Enter Email", Toast.LENGTH_SHORT).show();
        } else if (name.isEmpty()) {
            Toast.makeText(getActivity(), "Please Enter Password", Toast.LENGTH_SHORT).show();
        } else if (phone.isEmpty()) {
            Toast.makeText(getActivity(), "Please Enter Phone", Toast.LENGTH_SHORT).show();
        } else if (content.isEmpty()) {
            Toast.makeText(getActivity(), "Please Enter Message", Toast.LENGTH_SHORT).show();
        } else if (userContactUsRadioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getActivity(), "Please Select Message Type", Toast.LENGTH_SHORT).show();
        }
//        else {
//            Toast.makeText(baseActivity, "Tank You", Toast.LENGTH_SHORT).show();
//        }
        init(name, phone, content, mail, type);
    }

    private void init(String name, String phone, String email, String type, String content) {
        getClient().getContactUs(name, phone, email, type, content).enqueue(new Callback<ContactUs>() {
            @Override
            public void onResponse(Call<ContactUs> call, Response<ContactUs> response) {
                if (response.body().getStatus() == 1) {
                    Toast.makeText(baseActivity, response.body().getMsg()+"thank you", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ContactUs> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.user_contact_us_btn_send)
    public void onViewClicked() {
        getContact();
    }

    @Override
    public void onBack() {
        super.onBack();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
