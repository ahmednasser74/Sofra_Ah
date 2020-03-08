package com.example.sofra.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sofra.R;
import com.example.sofra.data.model.listOfTown.GeneralResponse;
import com.example.sofra.data.model.listOfTown.GeneralResponseData;

import java.util.ArrayList;
import java.util.List;

public class SpinnersAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflter;
    public List<GeneralResponseData> generalResponseData = new ArrayList<>();
    public int selectedId = 0;

    public SpinnersAdapter(Context applicationContext) {
        this.context = applicationContext;
        inflter = (LayoutInflater.from(applicationContext));
    }

    public void setData(List<GeneralResponseData> generalResponseData, String hint) {
        this.generalResponseData = new ArrayList<>();
        this.generalResponseData.add(new GeneralResponseData(0, hint));
        this.generalResponseData.addAll(generalResponseData);
    }

    @Override
    public int getCount() {
        return generalResponseData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.item_spinner, null);//da el item ely byzhr gowa el spinner nfso

        TextView names = (TextView) view.findViewById(R.id.item_spinner_tv_text);

//        names.setText(generalResponseData.get(0).getName());
//        selectedId = generalResponseData.get(0).getId();

        names.setText(generalResponseData.get(i).getName());
        selectedId = generalResponseData.get(i).getId();//da el id ely ana wa2f 3aleh now

        return view;
    }
}