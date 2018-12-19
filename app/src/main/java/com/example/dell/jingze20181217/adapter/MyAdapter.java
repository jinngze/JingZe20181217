package com.example.dell.jingze20181217.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.jingze20181217.R;
import com.example.dell.jingze20181217.bean.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

public class MyAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  Context context;
  List<Bean.DataBean> dataBeans;

    public MyAdapter(Context context, List<Bean.DataBean> list) {
        this.context = context;
       dataBeans = new ArrayList<>();
    }


    public void setdata(List<Bean.DataBean> data) {
      dataBeans.clear();
      dataBeans.addAll(data);
      notifyDataSetChanged();

    }

    public void adddata(List<Bean.DataBean> data) {
        dataBeans.addAll(data);
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

         ViewHolder1 holder1 = null;
         View v = View.inflate(context,R.layout.one_image,null);
         holder1 = new MyAdapter.ViewHolder1(v);

        return holder1;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

        ViewHolder1 holder1 = (ViewHolder1) viewHolder;
        holder1.textView.setText(dataBeans.get(i).getTitle());
        holder1.price.setText(dataBeans.get(i).getBargainPrice()+"");
        Glide.with(context).load(dataBeans.get(i).getImages()).into(holder1.imageView);


        //截取图片集
        String images = dataBeans.get(i).getImages();

        Pattern pen = compile("\\|");
        String [] img = pen.split(images);

        Glide.with(context).load(img[0]).into(holder1.imageView);

        //点击事件
        holder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mClickListener != null){
                    mClickListener.onItemClick(i,dataBeans.get(i).getTitle());
                }
            }
        });

        //长按
        holder1.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(mLongItemClickListener != null){
                     mLongItemClickListener.onItemLongClick(i);
                }

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

    //删除

    public void deleteItem(int position){
        dataBeans.remove(position);
        notifyItemRemoved(position);
    }




    class ViewHolder1 extends RecyclerView.ViewHolder{

      private  ImageView imageView;
      private TextView textView,price;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.text1);
            price = itemView.findViewById(R.id.price);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mClickListener != null){
                        mClickListener.onImageClick(view);
                    }
                }
            });

        }




    }


    public interface  OnItemClickListener {

        void onItemClick(int i,String title);
        void onImageClick(View view);
    }

    public interface   OnLongItemClickListener {

        void onItemLongClick(int i);
    }

    private OnItemClickListener mClickListener;
    private OnLongItemClickListener mLongItemClickListener;


    public void setClickListeners(OnItemClickListener clickListener) {
        this.mClickListener =clickListener;
    }

    public void setLongItemClickListeners(OnLongItemClickListener longItemClickListener) {
        this.mLongItemClickListener = longItemClickListener;
    }
}
