package com.yl.myimageupdata.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yl.myimageupdata.R;
import com.yl.myimageupdata.golbal.MenuBean;
import com.yl.myimageupdata.service.LoopsService;
import com.yl.myimageupdata.utils.Utils;
import com.yl.myimageupdata.widget.BlankFragment;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private int[] imgResId = new int[]{R.drawable.banner01, R.drawable.banner02, R.drawable.banner03};
    private Handler handler = new Handler();
    private ViewPager viewPager;
    private GridView gvMenus;
    private ImageView left, right;
    private RelativeLayout photographBtn, homeBtn, meBtn;
    private ArrayList<MenuBean> mList;
    //初始化数据
    private String[] items = new String[]{"模块一", "模块二", "模块三", "模块四", "模块五", "模块六"};
    private Integer[] icons = new Integer[]{R.mipmap.ic1, R.mipmap.ic2,
            R.mipmap.ic3, R.mipmap.ic4, R.mipmap.ic5, R.mipmap.ic6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.im);
        left = (ImageView) findViewById(R.id.left);
        right = (ImageView) findViewById(R.id.right);
        photographBtn = (RelativeLayout) findViewById(R.id.photographBtn);
        homeBtn = (RelativeLayout) findViewById(R.id.homeBtn);
        meBtn = (RelativeLayout) findViewById(R.id.meBtn);
        gvMenus = (GridView) findViewById(R.id.gv_menus);

        left.setOnClickListener(this);
        right.setOnClickListener(this);
        photographBtn.setOnClickListener(this);
        homeBtn.setOnClickListener(this);
        meBtn.setOnClickListener(this);
        viewPager.setAdapter(new MyPaerAdapter(getSupportFragmentManager()));
        autoScroll();
        initView();
        startService(new Intent(getApplicationContext(), LoopsService.class));
        initData();
    }

    private void initData() {
        gvMenus.setAdapter(new GridViewAdapter());
        //分类
        gvMenus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(Utils.getContext(), OneActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(Utils.getContext(), TwoActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(Utils.getContext(), ThreeActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(Utils.getContext(), FourActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(Utils.getContext(), FiveActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(Utils.getContext(), SixActivity.class));
                        break;
                }
            }
        });
    }

    //适配器
    class GridViewAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public MenuBean getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(Utils.getContext(), R.layout.list_menus_item, null);
            ImageView ivItem = (ImageView) view.findViewById(R.id.iv_item);
            TextView tvItem = (TextView) view.findViewById(R.id.tv_name);
            MenuBean info = getItem(position);
            tvItem.setText(info.name);
            ivItem.setImageResource(info.iconId);
            return view;
        }

    }

    //初始化数据
    private void initView() {
        //菜单6个item数据
        mList = new ArrayList<>();
        for (int i = 0; i < items.length; i++) {
            MenuBean info = new MenuBean();
            info.iconId = icons[i];
            info.name = items[i];
            mList.add(info);
        }
    }

    //自动轮播
    private void autoScroll() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                viewPager.setCurrentItem(currentItem + 1);
                handler.postDelayed(this, 2000);
            }
        }, 2000);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.right:
                startActivity(new Intent(this, ChangePasswordActivity.class));
                break;
            case R.id.photographBtn:
                startActivity(new Intent(this, PhotographActivity.class));
                break;
            case R.id.homeBtn:
                startActivity(new Intent(this, HomeActivity.class));
                break;
            case R.id.meBtn:
                startActivity(new Intent(this, MeActivity.class));
                break;
        }
    }

    class MyPaerAdapter extends FragmentPagerAdapter {

        public MyPaerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            position %= imgResId.length;
            BlankFragment fragment = new BlankFragment();
            fragment.setImage(imgResId[position]);
            return fragment;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       /* Intent stopIntent = new Intent(getApplicationContext(), LoopsService.class);
        stopService(stopIntent);*/
    }
}
