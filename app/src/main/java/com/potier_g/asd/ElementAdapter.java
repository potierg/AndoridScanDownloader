package com.potier_g.asd;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by guill on 30/04/2017.
 */

public class ElementAdapter extends ArrayAdapter<Element> {

    public ElementAdapter(Context context, List<Element> elements) {
        super(context, 0, elements);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_files,parent, false);
        }

        TomeViewHolder viewHolder = (TomeViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new TomeViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.elementName);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.elementIcon);
            convertView.setTag(viewHolder);
        }

        Element tome = getItem(position);

        viewHolder.name.setText(tome.getName());
        if (tome.getIs_file() == true)
        {
            File file = new File(tome.getPath());
            if(file.exists()) {
                try {
                    BitmapFactory.Options o = new BitmapFactory.Options();
                    o.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(new FileInputStream(file), null, o);

                    int rs;
                    if (file.length() >= 1000000)
                        rs = 70;
                    else
                        rs = 10;

                    // Find the correct scale value. It should be the power of 2.
                    int scale = 1;
                    while (o.outWidth / scale / 2 >= rs &&
                            o.outHeight / scale / 2 >= rs) {
                        scale *= 2;
                    }

                    // Decode with inSampleSize
                    BitmapFactory.Options o2 = new BitmapFactory.Options();
                    o2.inSampleSize = scale;
                    viewHolder.icon.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(file), null, o2));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        else
            viewHolder.icon.setImageDrawable(new ColorDrawable(255));

        return convertView;
    }

    private class TomeViewHolder{
        public TextView name;
        public ImageView icon;
    }
}
