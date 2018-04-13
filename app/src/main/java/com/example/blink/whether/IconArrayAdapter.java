package com.example.blink.whether;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by blink on 4/11/2018.
 */

public class IconArrayAdapter extends ArrayAdapter<RowItem> {


    private Context context;

    public IconArrayAdapter(@NonNull Context context, @LayoutRes int resource, List<RowItem> items) {
        super(context, resource, items);
        this.context = context;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView txtDesc;
    }

    @Override
    /**
     * public View Getview
     *  Gets a view
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final RowItem rowItem = getItem(position);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.whether_list_row, null);
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.textView_city);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtDesc.setText(rowItem.getCityName());
        holder.imageView.setImageDrawable(rowItem.getDrawable());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);

                Drawable drawable = rowItem.getDrawable();
                Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                intent.putExtra(RowItem.DRAWABLE, bitmap);
                intent.putExtra(RowItem.CITY_NAME, rowItem.getCityName());
                intent.putExtra(RowItem.TEMP, rowItem.getCityTemperature());

                intent.putExtra(RowItem.LOW_TEMP, rowItem.getLowTemperature());
                intent.putExtra(RowItem.HIGH_TEMP, rowItem.getHighTemperature());
                intent.putExtra(RowItem.PRECIPITATION, rowItem.getChanceOfPrecipitation());
                intent.putExtra(RowItem.WIND_SPEED, rowItem.getWindSpeed());
                intent.putExtra(RowItem.HUMIDITY, rowItem.getHumidity());
                context.startActivity(intent);
            }
        });

        return convertView;
    }

}