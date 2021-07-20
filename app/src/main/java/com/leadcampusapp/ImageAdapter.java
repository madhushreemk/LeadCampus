package com.leadcampusapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * @author Paresh Mayani (@pareshmayani)
 */



public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {

    private ArrayList<String> mImagesList;
   // private ArrayList<byte[]> mImagesListByte;
    private Context mContext;
    private SparseBooleanArray mSparseBooleanArray;

    Class_imagedelete obj_classimagedelete;
    ArrayList<String> arrPackage;

    public ImageAdapter(Context context, ArrayList<String> imageList)

    {
        mContext = context;
        mSparseBooleanArray = new SparseBooleanArray();
        mImagesList = new ArrayList<String>();
      //  mImagesListByte = new ArrayList<>();
        this.mImagesList = imageList;


        arrPackage = new ArrayList<>();
    }

    public ArrayList<String> getCheckedItems()
    {
        ArrayList<String> mTempArry = new ArrayList<String>();

        for(int i=0;i<mImagesList.size();i++) {
            if(mSparseBooleanArray.get(i)) {
                mTempArry.add(mImagesList.get(i));
            }
        }

        return mTempArry;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    CompoundButton.OnCheckedChangeListener mCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mSparseBooleanArray.put((Integer) buttonView.getTag(), isChecked);
        }
    };

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_multiphoto_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {

        String imageUrl = mImagesList.get(position);

        Log.d("imageURLissss",imageUrl);

       /* Glide.with(mContext)
                .load("file://"+imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher)
                .error(R.drawable.ic_launcher)
                .into(holder.imageView);*/

        Glide.with(mContext)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher)
                .error(R.drawable.ic_launcher)
                .into(holder.imageView);





       /* holder.checkBox.setTag(position);
        holder.checkBox.setChecked(mSparseBooleanArray.get(position));
        holder.checkBox.setOnCheckedChangeListener(mCheckedChangeListener);*/
    /*    holder.img_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"Clicked on remove",Toast.LENGTH_LONG).show();

            }
        });*/
    }

    @Override
    public int getItemCount()
    {
        return mImagesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView img_remove;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);

            img_remove = (ImageView) view.findViewById(R.id.img_delete);
            imageView = (ImageView) view.findViewById(R.id.imageView1);

            img_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    //getPosition();
                   // Log.e("position",getPosition());

                    int position = getAdapterPosition();
                    Log.e("position img", String.valueOf(getAdapterPosition()));

                    //removeAt(position);

                  //  Toast.makeText(mContext,"before : " + mImagesList.size(),Toast.LENGTH_SHORT).show();


                    mImagesList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position,mImagesList.size());

                    String str_position= String.valueOf(position);
                    insert_into_positiontable(str_position);

                    //Toast.makeText(mContext,"After : " +  mImagesList.size(),Toast.LENGTH_SHORT).show();



                    //removeAt(getAdapterPosition());
                }
            });
        }


    }

    public void removeAt(int position)
    {
        Log.e("position remove", String.valueOf(position));

        this.mImagesList.remove(position);
        notifyItemRemoved(position);

        notifyItemRangeChanged(position, this.mImagesList.size());
    }




    public ArrayList<String> getImageList()
    {
       // Toast.makeText(mContext,"return : " +  mImagesList.size(),Toast.LENGTH_SHORT).show();
        return mImagesList;

    }
   /* public ArrayList<byte[]> getImageListByte(){
        return mImagesListByte;
    }*/

    public void clear()
    {
        final int size = mImagesList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                mImagesList.remove(0);
            }
            notifyItemRangeRemoved(0, size);
        }
    }



    public void insert_into_positiontable(String str_position)
    {
        SQLiteDatabase db1 = mContext.openOrCreateDatabase("Leaddb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS ImageDeletedlist(PID VARCHAR);");


        String SQLiteQuery = "INSERT INTO ImageDeletedlist (PID)" +
                " VALUES ('"+str_position+"');";
        db1.execSQL(SQLiteQuery);


    }



}// end of class
