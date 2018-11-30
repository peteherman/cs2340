package com.example.gourn.buzztracker.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.gourn.buzztracker.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

class CustomWindow implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
//    private Context mContext;

    public CustomWindow(Context context) {
        Context mContext;
        mWindow = LayoutInflater.from(context).inflate(R.layout.locations_info_window, null);
    }

    private void rendowWindowText(Marker marker, View view){

        String title = marker.getTitle();
        TextView tvTitle = view.findViewById(R.id.title);

        if(!"".equals(title)){
            tvTitle.setText(title);
        }

        String snippet = marker.getSnippet();
        TextView tvSnippet = view.findViewById(R.id.snippet);

        if(!"".equals(snippet)){
            tvSnippet.setText(snippet);
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }
}