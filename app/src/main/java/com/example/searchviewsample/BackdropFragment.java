package com.example.searchviewsample;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

public class BackdropFragment extends Fragment {

    private CoordinatorLayout coordinatorLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//        coordinatorLayout = (CoordinatorLayout)inflater.inflate(R.layout.fragment_hazards, container, false);
//
//        Context context = getContext();
//
//        if(context != null){
//            setTitle(context.getString(R.string.title_hazards));
//        }
//
//        filterIcon = coordinatorLayout.findViewById(R.id.filterIcon);
//        LinearLayout contentLayout = coordinatorLayout.findViewById(R.id.contentLayout);
//
//        sheetBehavior = BottomSheetBehavior.from(contentLayout);
//        sheetBehavior.setFitToContents(false);
//        sheetBehavior.setHideable(false);//prevents the boottom sheet from completely hiding off the screen
//        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);//initially state to fully expanded
//
//        filterIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toggleFilters();
//            }
//        });
//
//
//        return coordinatorLayout;
//    }
//
//    private void toggleFilters(){
//        if(sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
//            sheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
//        }
//        else {
//            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//        }
//    }
}
