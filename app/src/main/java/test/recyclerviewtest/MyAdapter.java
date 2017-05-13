package test.recyclerviewtest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Administrator on 2017/3/23.
 * 创建MyAdapter，实现三个方法，绑定数据
 */

public class MyAdapter  extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private LayoutInflater layoutInflater;
    private List<String> list;//填充数据
    private List<Integer> heightList;//textview的高度信息
    OnMyItemClickListener myListener;//定义监听器
    public MyAdapter(Context context,List<String> data){//构造方法
        layoutInflater=LayoutInflater.from(context);
        this.list=data;
        heightList=new ArrayList<Integer>();
        for (int i=0;i<data.size();i++){
            heightList.add((int)(Math.random()*300+160));//随机设置textview的高度
        }
    }
    //点击响应的监听接口
    public interface OnMyItemClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }
    public void setOnMyItemClickListener(OnMyItemClickListener onMyItemClickListener){
        this.myListener=onMyItemClickListener;
    }



    @Override
    //创建viewholder
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.recycler_item,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        myViewHolder.textView= (TextView) view.findViewById(R.id.item_tv);//item中的控件可以通过初始化获取或者构造获取

        return myViewHolder;
    }

    @Override
    //绑定数据
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        //获取textview的宽高等信息
        ViewGroup.LayoutParams layoutParams = holder.textView.getLayoutParams();
        layoutParams.height=heightList.get(position);//设置textview的高度
        holder.textView.setLayoutParams(layoutParams);
        holder.textView.setText(list.get(position));


        //如果设置了自定义的点击回调监听器，则设置点击事件
        if (myListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=holder.getLayoutPosition();//点击的item控件的位置
                    myListener.onItemClick(holder.itemView,pos);
                    /*
                    此方法返回的pos值与onBindViewHolder方法传入的position值有可能不同。
                    根据SDK中的解释，在Recyclerview 进行添加、移除item等操作时，position位置可能会变化，
                    而所有的adapter的刷新并不总是及时的，
                    只有这个方法返回的才是当前item经过一些变换后所处的真正位置。
                     */
                }
            });
            //设置长按点击监听事件
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos=holder.getLayoutPosition();
                    myListener.onItemLongClick(holder.itemView,pos);
                    return false;
                }
            });
        }

    }

    @Override
    //数据条目
    public int getItemCount() {
        return list.size();
    }
    //自定义内部静态类继承自viewholder
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(View itemView) {
            super(itemView);
        }
        TextView textView;
    }
    public void addData(int position) {//增加一个item条目
        list.add(position, "Insert One");//插入的位置
        notifyItemInserted(position);
    }

    public void removeData(int position) {//删除一个item条目
        list.remove(position);//删除的位置
        notifyItemRemoved(position);
    }

}
