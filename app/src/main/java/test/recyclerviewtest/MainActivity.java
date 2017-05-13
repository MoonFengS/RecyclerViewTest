package test.recyclerviewtest;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener, MyAdapter.OnMyItemClickListener {
    MyAdapter myAdapter;
    RecyclerView recyclerView;
    List<String> datas;
    private SystemBarTintManager tintManager;
    private ImageView add,delete;//增删按钮


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initWindow();
        getData();//先写入数据
        recyclerView= (RecyclerView) findViewById(R.id.my_recycler_view);
        add= (ImageView) findViewById(R.id.add_img);
        delete= (ImageView) findViewById(R.id.delete_img);
        //设置流布局管理
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        //为RecyclerView的item添加系统分隔线
        //recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        //设置系统默认的item增删动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        myAdapter=new MyAdapter(this,datas);
        //设置适配器
        recyclerView.setAdapter(myAdapter);
        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        myAdapter.setOnMyItemClickListener(this);



    }
    private void getData(){
        datas=new ArrayList<String>();
        for (int i='A';i<='Z';i++){
            datas.add(""+(char)i);
        }
    }
    private void initWindow() {//初始化窗口属性，让状态栏和导航栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(this);
            int statusColor = Color.parseColor("#043ba9");
            tintManager.setStatusBarTintColor(statusColor);
            tintManager.setStatusBarTintEnabled(true);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_img:
                myAdapter.addData(1);
                break;
            case R.id.delete_img:
                myAdapter.removeData(1);
                break;
        }
    }

    @Override
    //item的点击事件
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "点击了"+datas.get(position), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}

