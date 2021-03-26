package io.iskaldvind.notes.utils;

import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import io.iskaldvind.notes.R;

public class ImageLoader {
    
    private static ImageLoader instance = null;
    private static com.nostra13.universalimageloader.core.ImageLoader nostraImageLoader = null;
    private static DisplayImageOptions options = null;
    
    public static ImageLoader getInstance(Context context) {
        if (instance == null) {
            
            if (nostraImageLoader == null) {
                nostraImageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
                ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(context).build();
                nostraImageLoader.init(imageLoaderConfiguration);
                options = new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.drawable.image_stub)
                        .cacheOnDisk(true)
                        .cacheInMemory(true)
                        .build();
            }
            instance = new ImageLoader();
        }
        return instance;
    }
    
    public void displayImage(String url, ImageView view) {
        nostraImageLoader.displayImage(url, view, options);
    }
}
