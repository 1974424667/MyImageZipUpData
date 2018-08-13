package com.yl.myimageupdata.widget;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yl.myimageupdata.R;

public class BlankFragment extends Fragment {
private  int imgRes;

    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_blank, container, false);
        ImageView image = (ImageView) inflate.findViewById(R.id.image);
        image.setImageResource(imgRes);
        return inflate;
    }
public void setImage(int imgRes){
this.imgRes=imgRes;
}
}
