package com.example.deadsec.isliroutine.utils;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.deadsec.isliroutine.R;
import com.example.deadsec.isliroutine.model.ClassModel;
import com.framgia.library.calendardayview.DayView;
import com.framgia.library.calendardayview.EventView;
import com.framgia.library.calendardayview.data.IEvent;
import com.framgia.library.calendardayview.decoration.CdvDecorationDefault;

/**
 * Created by deadsec on 10/30/17.
 */

public class CustomDecoration extends CdvDecorationDefault {
    Context context;

    public CustomDecoration(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public EventView getEventView(IEvent event, Rect eventBound, int hourHeight, int seperateHeight) {

        final EventView eventView = super.getEventView(event, eventBound, hourHeight, seperateHeight);

        // hide event name
        eventView.findViewById(com.framgia.library.calendardayview.R.id.item_event_name).setVisibility(View.GONE);

        // hide event header
        //eventView.findViewById(com.framgia.library.calendardayview.R.id.item_event_header).setVisibility(View.GONE);

        LinearLayout linearLayout = (LinearLayout) eventView.findViewById(com.framgia.library.calendardayview.R.id.item_event_content);

        View view = LayoutInflater.from(context).inflate(R.layout.item_routine_single_class, null);
        TextView className = (TextView) view.findViewById(R.id.tv_class_name);
        TextView teacherName = (TextView) view.findViewById(R.id.tv_class_teacher_name);
        TextView location = (TextView) view.findViewById(R.id.tv_class_location);
        TextView time = (TextView) view.findViewById(R.id.tv_class_time);
        ImageView lessionType=(ImageView)view.findViewById(R.id.iv_lession_type);

        ClassModel classModel = (ClassModel) event;
        className.setText(classModel.getCourseName());
        teacherName.setText(classModel.getTeacherName());
        location.setText(classModel.getLocation());
        time.setText(String.format("%1$02d:%2$02d-%3$02d:%4$02d",
                classModel.getStartTime().getTime().getHours(),
                classModel.getStartTime().getTime().getMinutes(),
                classModel.getEndTime().getTime().getHours(),
                classModel.getEndTime().getTime().getMinutes()));

        switch(classModel.getType()) {
            case "Lecture":
                lessionType.setImageResource(R.drawable.ic_lecture2);
                break;
            case "Lab":
                lessionType.setImageResource(R.drawable.ic_tutorial2);
                break;
            case "Tutorial":
                lessionType.setImageResource(R.drawable.ic_lab);
                break;
        }

        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        linearLayout.addView(view);

        return eventView;
    }

    @Override
    public DayView getDayView(int hour) {
        return super.getDayView(hour);
    }
}
