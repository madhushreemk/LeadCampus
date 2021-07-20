package com.leadcampusapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import androidx.annotation.NonNull;
//import android.support.design.widget.BottomSheetBehavior;
//import android.support.design.widget.BottomSheetDialogFragment;
import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by gurleensethi on 15/01/18.
 */

@SuppressLint("ValidFragment")
public class BottomSheetFragment extends BottomSheetDialogFragment {


    TextView btn_readMore1;
    ImageView shareit1;
    ImageView iv_top;
    Context mContext;
    RelativeLayout share_images;
    String story_blog;
    String Story_Type;
    public File pictureFile;
    File pictureFileDir;
    String Story_Video;
    private BottomSheetBehavior mBottomSheetBehaviour;

    @SuppressLint("ValidFragment")
    public BottomSheetFragment(Context mContext, RelativeLayout share_images, String story_blog, File pictureFile,String Story_Type) {
        this.mContext = mContext;
        this.share_images = share_images;
        this.story_blog = story_blog;
        this.pictureFile = pictureFile;
        this.Story_Type = Story_Type;
    }

    public BottomSheetFragment(Context mContext, RelativeLayout share_images, String story_blog, File pictureFile, String story_type, String story_video) {
        this.mContext = mContext;
        this.share_images = share_images;
        this.story_blog = story_blog;
        this.pictureFile = pictureFile;
        this.Story_Type = story_type;
        this.Story_Video = story_video;
    }


    /*@SuppressLint("ValidFragment")
    public BottomSheetFragment(Context mContext, String story_Blog, File pictureFile, RelativeLayout share_images){
        this.mContext=mContext;
        story_blog=story_blog;
        this.pictureFile=pictureFile;
        this.share_images=share_images;
    }*/

   /* public BottomSheetFragment(String story_blog) {
        this.story_blog=story_blog;
    }*/
  /*  @SuppressLint("ValidFragment")
    public BottomSheetFragment(String story_blog, File pictureFile) {
       // this.Story_Type=Story_Type;
        this.story_blog=story_blog;
        this.pictureFile=pictureFile;
    }*/

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog
            (Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        //Set the custom view
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bottom_sheet, null);
        dialog.setContentView(view);

        Log.i("tag","story_blog=="+story_blog);
        btn_readMore1 = (TextView) view.findViewById(R.id.btn_readMore1);
        shareit1=(ImageView) view.findViewById(R.id.shareit1);
        iv_top=(ImageView) view.findViewById(R.id.iv_top);

      //  btn_readMore1.setVisibility(View.GONE);

            /*btn_readMore1.setTextColor(Color.GRAY);
            btn_readMore1.setClickable(false);
            btn_readMore1.setFocusable(false);*/
       /* }
        else{
            btn_readMore1.setVisibility(View.VISIBLE);

        }*/
        Log.i("tag","Story_Type="+Story_Type);
        if(Story_Type.equals("1")){
            btn_readMore1.setVisibility(View.GONE);
        }else {
        if(story_blog.equals("<empty>") || story_blog.equals("anyType{}") || story_blog.equals("null") || story_blog.equals("")) {
            btn_readMore1.setVisibility(View.GONE);
        }
        else {
            btn_readMore1.setVisibility(View.VISIBLE);
        }
        }

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        final CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    String state = "";

                    switch (newState) {
                        case BottomSheetBehavior.STATE_DRAGGING: {
                            state = "DRAGGING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_SETTLING: {
                            state = "SETTLING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_EXPANDED: {
                            state = "EXPANDED";
                            break;
                        }
                        case BottomSheetBehavior.STATE_COLLAPSED: {
                            state = "COLLAPSED";
                            break;
                        }
                        case BottomSheetBehavior.STATE_HIDDEN: {
                            dismiss();
                            state = "HIDDEN";
                            break;
                        }
                    }

                    Toast.makeText(getContext(), "Bottom Sheet State Changed to: " + state, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });
        }



        shareit1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void onClick(View v) {
                Log.i("TAG", "pictureFile=bottom="+pictureFile);
                File file = saveBitMap(mContext, share_images);
                if (file != null) {
                    Log.i("TAG", "Drawing saved to the gallery!");
                } else {
                    Log.i("TAG", "Oops! Image could not be saved.");
                }
                if(Story_Type.equals("4")){
                    shareVideo();
                }else {
                    shareImage();
                }

               // state.  BottomSheetBehavior.STATE_HIDDEN;
                dismiss();
            }
        });

        btn_readMore1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(story_blog));
                mContext.startActivity(browserIntent);
            }
        });

        iv_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verticalViewPager.setAdapter(new VerticlePagerAdapter(getActivity(), LeadStoryList.listview_arr));

                //verticalViewPager.setPageTransformer(true, new VerticalPageTransformerAnimate());
                //verticalViewPager.setCurrentItem(0);
                LeadStoryFragment.verticalFunction();
               // mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);
                dismiss();
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public File saveBitMap(Context context, View drawView){
        pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),"Logicchip");
        if (!pictureFileDir.exists()) {
            boolean isDirectoryCreated = pictureFileDir.mkdirs();
            if(!isDirectoryCreated)
                Log.i("TAG", "Can't create directory to save the image");
            return null;
        }
        String filename = pictureFileDir.getPath() + File.separator+ System.currentTimeMillis()+".jpg";
        pictureFile = new File(filename);
        Bitmap bitmap =getBitmapFromView(drawView);
        try {
            pictureFile.createNewFile();
            FileOutputStream oStream = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, oStream);
            oStream.flush();
            oStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("TAG", "There was an issue saving the image.");
        }
        scanGallery( context,pictureFile.getAbsolutePath());
        Log.i("tag","pictureFile="+pictureFile);
        return pictureFile;
    }
    private void scanGallery(Context cntx, String path) {
        try {
            MediaScannerConnection.scanFile(cntx, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("TAG", "There was an issue scanning gallery.");
        }
    }
    private Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap;
        if(Build.VERSION.SDK_INT == Build.VERSION_CODES.M){
            returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        }else if(Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP){
            returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        }else if(Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1){
            returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        }else {
            returnedBitmap = Bitmap.createBitmap(1080, 1500, Bitmap.Config.ARGB_8888);
        }
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        }   else{
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    /* private Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        }   else{
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }
*/
    public void shareVideo() {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");

        String imagePath= pictureFile.getAbsolutePath();
        Log.i("tag","pictureFile="+imagePath);
        File imageFileToShare = new File(imagePath);

        //Uri uri = Uri.fromFile(pictureFile);
        Uri uri = FileProvider.getUriForFile(getContext(), "com.mydomain.fileprovider", pictureFile);
        share.putExtra(android.content.Intent.EXTRA_SUBJECT, "Leader Story");
        share.putExtra(Intent.EXTRA_TEXT,"http://www.youtube.com/embed/"+Story_Video);
        share.putExtra(Intent.EXTRA_STREAM, uri);

        startActivity(Intent.createChooser(share, "Share Image!"));
        // File fdelete = new File(imagePath);
        // if (pictureFileDir.exists()) {

        //}
    }
    public void shareImage() {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");

        String imagePath= pictureFile.getAbsolutePath();
        Log.i("tag","pictureFile="+imagePath);
        File imageFileToShare = new File(imagePath);

      //  Uri uri = Uri.fromFile(pictureFile);
        Uri uri = FileProvider.getUriForFile(getContext(), "com.mydomain.fileprovider", pictureFile);
        share.putExtra(android.content.Intent.EXTRA_SUBJECT, "Leader Story");
     //   share.putExtra(Intent.EXTRA_TEXT,"http://www.youtube.com/embed/"+story_blog);
        share.putExtra(Intent.EXTRA_STREAM, uri);

        startActivity(Intent.createChooser(share, "Share Image!"));
        // File fdelete = new File(imagePath);
        // if (pictureFileDir.exists()) {

        //}
    }
   /* public void show(FragmentManager supportFragmentManager, String tag, String story_blog) {
        Log.i("tag","story_blog"+story_blog);
    }*/
}
