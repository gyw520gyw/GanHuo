package com.gyw.ganhuo.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyw.ganhuo.R;
import com.gyw.ganhuo.adapters.DiscoBaseAdapter;
import com.gyw.ganhuo.adapters.MainGrilAdapter;
import com.gyw.ganhuo.base.BaseFragment;
import com.gyw.ganhuo.http.GanUri;
import com.gyw.ganhuo.model.GanData;
import com.gyw.ganhuo.presenter.GrilPresenter;
import com.gyw.ganhuo.presenter.view.GrilView;
import com.gyw.ganhuo.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


public class DiscoClassifyFragment extends BaseFragment<GrilPresenter> implements GrilView {

    private static final String ARG_PARAM1 = "param1";

    private String mTitle;

    private int mCurrentPage = 1;

    private LinearLayoutManager layoutManager;

    private boolean isRefresh = true;   //是否是刷新,默认是刷新

    private DiscoBaseAdapter adapter;

    @Bind(R.id.rv_disco_classify)
    RecyclerView mRecyclerView;


    @Bind(R.id.srl_disco_classify)
    SwipeRefreshLayout mRefreshLayout;



    public static DiscoClassifyFragment newInstance(String param1) {
        DiscoClassifyFragment fragment = new DiscoClassifyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_diso_classify, container, false);
    }

    @Override
    protected void initView() {
//第一次进入页面的时候显示加载进度条
        mRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        mRefreshLayout.setRefreshing(true);

        //设置RecyclerView
        layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void initData() {
        p = new GrilPresenter(mContext, this);
        p.getDataFromServer(mTitle, mCurrentPage);
    }

    @Override
    protected void initListener() {

        //TODO 后期提出去 需优化
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                isRefresh = true;

                mCurrentPage = 1;

                p.getDataFromServer(mTitle, mCurrentPage);

            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

//                mRefreshLayout.isRefreshing()
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                LogUtil.d("visibleItemCount : " + visibleItemCount + " totalItemCount : " + totalItemCount + " pastVisibleItems :  " + pastVisibleItems);

                if (!mRefreshLayout.isRefreshing() && (visibleItemCount + pastVisibleItems) >= totalItemCount) {

                    isRefresh = false;

                    mCurrentPage = mCurrentPage + 1;

                    loadMoreData();
                }
            }
        });
    }


    private void loadMoreData() {

        LogUtil.d("mCurrentPage : " + mCurrentPage);

        p.getDataFromServer(mTitle, mCurrentPage);
    }

    @Override
    public void getDataFinished() {
        //数据加载完成
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

    private List<GanData> ganDataList = new ArrayList<>();

    @Override
    public void handleData(List<GanData> list) {

        LogUtil.d("isRefresh : " + isRefresh + " list : " + list.toString());

        //刷新,先清除数据
        if (isRefresh) {
            ganDataList.clear();
        }

        //加载数据,直接添加
        ganDataList.addAll(list);

        if (adapter == null) {
            adapter = new DiscoBaseAdapter(ganDataList);
            mRecyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}