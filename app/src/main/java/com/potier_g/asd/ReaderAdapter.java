package com.potier_g.asd;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Debug;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by guill on 01/05/2017.
 */

public class ReaderAdapter extends PagerAdapter {
    private final Activity activity;
    private List<String> imagesUrl;
    Map<Integer, View> imageViews = new HashMap<Integer, View>();

    private float width;
    private float height;

    private HashMap<Integer, Bitmap> listImg = new HashMap<Integer, Bitmap>();
    private HashMap<Integer, View>listView = new HashMap<Integer, View>();
    private HashMap<Integer, ViewGroup>listGrpView = new HashMap<Integer, ViewGroup>();
    private HashMap<Integer, TouchImageView> listTIM = new HashMap<Integer, TouchImageView>();


    public ReaderAdapter(Activity activity, List<String> imagesUrl, float width, float height) {
        this.activity = activity;
        this.imagesUrl = imagesUrl;
        this.width = width;
        this.height = height;
    }

    public Map<Integer, View> getImageViews() {
        return imageViews;
    }

    @Override
    public int getCount() {
        return imagesUrl.size();
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = activity.getLayoutInflater();

        View view = inflater.inflate(R.layout.activity_screen_slide, null);
        com.potier_g.asd.TouchImageView imageView = (TouchImageView) view.findViewById(R.id.readerPage);
        this.listTIM.put(position, imageView);
        try {
            File file = new File(imagesUrl.get(position));
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            int scale = 1;
            while (o.outWidth / scale / 2 >= width && o.outHeight / scale / 2 >= height) {
                scale *= 2;
            }

            long mem = get_memory();

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;

            FileInputStream f = new FileInputStream(file);

            Bitmap bitmap = BitmapFactory.decodeStream(f, null, o2);
            listImg.put(position, bitmap);

        // ERREUR DE FILS DE PUTE DE SA GRAND MERE LA CHIENNE
            imageView.setImageBitmap(bitmap);
            imageView.setImageBitmap(null);

        } catch (FileNotFoundException g) {
            g.printStackTrace();
        }

        container.addView(view);
        listGrpView.put(position, container);
        listView.put(position, view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((FrameLayout) object);
        listGrpView.get(position).destroyDrawingCache();
        listView.get(position).destroyDrawingCache();
        //((BitmapDrawable)this.listTIM.get(position).getDrawable()).getBitmap().recycle();
        this.listTIM.get(position).setImageBitmap(null);
        this.listTIM.get(position).setImageDrawable(null);
        this.listTIM.get(position).setImageResource(0);
        listImg.get(position).recycle();
        java.lang.System.gc();
    }

    public long get_memory()
    {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) activity.getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);

        return mi.availMem;
    }

}