package com.testdemo.holyg.mywallet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.we.swipe.helper.WeSwipeHelper;



public class RecAdapter extends RecyclerView.Adapter<RecAdapter.RecViewholder> {
    public final static int INCOME = 1;
    public final static int EXPEND = 0;

    public final static int TRANSPORT = 5;
    public final static int MEAL = 6;
    public final static int HEALTH = 7;
    public final static int CLOTHES = 8;

    private Context context;
    private List<Sheet> data = new ArrayList<>();
    private LayoutInflater layoutInflater;


    DeletedItemListener deletedItemListener;

    public void setDelectedItemListener(DeletedItemListener deletedItemListener) {
        this.deletedItemListener = deletedItemListener;
    }

    public RecAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void itemMove(int fromPosition, int toPosition){
        notifyItemInserted(toPosition);
        //notifyItemMoved(fromPosition,toPosition);//执行动画
        notifyItemRangeChanged(Math.min(fromPosition, toPosition), Math.abs(fromPosition - toPosition) +1);//受影响的itemd都刷新下
    }

    public void setList(List<Sheet> list) {
        data.clear();
        data.addAll(list);
        if(!list.isEmpty()) {
            //itemMove(0,data.size()-1);

            notifyItemInserted(data.size()-1);
            //notifyItemMoved(data.size() - 1, 0);
            //notifyDataSetChanged();

        }
        else{
            notifyItemMoved(0,0);
        }
    }

    public List<Sheet> getData() {
        return data;
    }

    public void removeDataByPosition(int position) {
        if (position >= 0 && position < data.size()) {
            data.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public RecViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.layout_item, parent, false);
        return new RecViewholder(view);
    }

    @Override
    public void onBindViewHolder(final RecViewholder holder, int position) {

        Sheet tempSheet = data.get(holder.getAdapterPosition());

        holder.textViewComment.setText(tempSheet.getComment());
        holder.textViewDate.setText(tempSheet.getDate());
        holder.textViewAmount.setText(tempSheet.getValue());
        holder.textViewTime.setText(tempSheet.getTime());

        if(tempSheet.getType() == INCOME){
            holder.textViewTypeIncome.setVisibility(View.VISIBLE);
            holder.textViewTypeExpend.setVisibility(View.INVISIBLE);
            holder.imageViewTransport.setVisibility(View.INVISIBLE);
            holder.imageViewHealth.setVisibility(View.INVISIBLE);
            holder.imageViewMeal.setVisibility(View.INVISIBLE);
            holder.imageViewClothes.setVisibility(View.INVISIBLE);
        }
        else if(tempSheet.getType() == EXPEND){
            holder.textViewTypeIncome.setVisibility(View.INVISIBLE);
            holder.textViewTypeExpend.setVisibility(View.VISIBLE);
            switch (tempSheet.getWay()){
                case TRANSPORT:
                    holder.imageViewTransport.setImageResource(R.drawable.transport_dark);
                    break;
                case CLOTHES:
                    holder.imageViewClothes.setImageResource(R.drawable.clothes_dark);
                    break;
                case MEAL:
                    holder.imageViewMeal.setImageResource(R.drawable.restaurant_dark);
                    break;
                case HEALTH:
                    holder.imageViewMeal.setImageResource(R.drawable.health_dark);
                    break;
            }
        }


        holder.slideItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "s  " + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "EDIT" + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != deletedItemListener) {
                    deletedItemListener.deleted(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    /**
     * view.getWidth()获取的是屏幕中可以看到的大小.
     */
    public class RecViewholder extends RecyclerView.ViewHolder implements WeSwipeHelper.SwipeLayoutTypeCallBack {
        public TextView textViewComment;
        public TextView textViewDate;
        public TextView textViewTime;
        public TextView textViewTypeIncome;
        public TextView textViewTypeExpend;
        public TextView textViewAmount;
        public LinearLayout slide;
        public TextView edit, delete;
        public RelativeLayout slideItem;

        public ImageView imageViewTransport;
        public ImageView imageViewClothes;
        public ImageView imageViewMeal;
        public ImageView imageViewHealth;

        public RecViewholder(View itemView) {
            super(itemView);
            textViewComment = itemView.findViewById(R.id.item_text);
            textViewDate = itemView.findViewById(R.id.dateBar);
            textViewTime = itemView.findViewById(R.id.timeBar);
            textViewAmount = itemView.findViewById(R.id.textViewAmount);
            textViewTypeIncome = itemView.findViewById(R.id.textViewTypeIncome);
            textViewTypeExpend = itemView.findViewById(R.id.textViewTypeExpend);

            imageViewClothes = itemView.findViewById(R.id.imageViewClothes);
            imageViewMeal = itemView.findViewById(R.id.imageViewMeal);
            imageViewHealth = itemView.findViewById(R.id.imageViewHealth);
            imageViewTransport = itemView.findViewById(R.id.imageViewTransport);

            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            slide = itemView.findViewById(R.id.slide);
            slideItem = itemView.findViewById(R.id.slide_itemView);
        }

        @Override
        public float getSwipeWidth() {
            //布局隐藏超过父布局的范围的时候这里得不到宽度
            return dip2px(context, 160);
        }

        @Override
        public View needSwipeLayout() {
            return slideItem;
        }

        @Override
        public View onScreenView() {
            return textViewComment;
        }
    }

    /**
     * 根据手机分辨率从DP转成PX
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public interface DeletedItemListener {
        void deleted(int position);
    }

}
