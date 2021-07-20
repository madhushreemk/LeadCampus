package com.leadcampusapp;

/**
 * Created by User on 9/27/2018.
 */

        import android.app.Activity;
        import android.content.Context;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.leadcampusapp.module.NotificationActivityModel;

        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;

public class NotificationCustomAdapter extends BaseAdapter{
    String [] result;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;
    public ArrayList<NotificationActivityModel> projList;
    Activity activity;

    public NotificationCustomAdapter(NotificationHistoryActivity mainActivity, String[] prgmNameList, int[] prgmImages) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=mainActivity;
        imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    private ArrayList<NotificationActivityModel> mDisplayedValues = null;

    public NotificationCustomAdapter(Activity activity, ArrayList<NotificationActivityModel> projList) {
        super();
        this.activity = activity;
        this.projList = projList;
        context=activity;
        this.mDisplayedValues = projList;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mDisplayedValues.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv1;
        TextView tv2;
        TextView tv3;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.notification_list, null);
       LayoutInflater inflater = activity.getLayoutInflater();
       // LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //rowView = inflater.inflate(R.layout.notification_list, parent, false);

        NotificationActivityModel item = projList.get(position);

        holder.tv1=(TextView) rowView.findViewById(R.id.label);
        holder.tv2=(TextView) rowView.findViewById(R.id.n_message);
        holder.tv3=(TextView) rowView.findViewById(R.id.n_date);
        holder.img=(ImageView) rowView.findViewById(R.id.img);


        SimpleDateFormat inFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
        Date date = null;
        String day = null;
        try {
            date = inFormat.parse(item.getNotification_Date());
            SimpleDateFormat outFormat = new SimpleDateFormat("dd MMM hh:mm aa");
            day = outFormat.format(date);
            Log.e("tag","day="+day+" date="+date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tv1.setText(item.getNotification_Type());
        holder.tv2.setText(item.getNotification_Message());
        holder.tv3.setText(day);
       // holder.img.setImageResource(imageId[position]);
       /* rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
            }
        });*/



        return rowView;
    }

}