package com.star.myrecyclerview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private HomeAdapter mHomeAdapter;
    private List<String> mStringList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        initData();
        initView();
    }

    private void initData() {

        mStringList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            int count = (int) (Math.random() * 10);
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < count; j++) {
                stringBuilder.append("star");
            }
            mStringList.add(i + 1 + stringBuilder.toString());
        }
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);

//        setLinearView();
//        setGridView();
        setWaterfallView();

        mHomeAdapter = new HomeAdapter(this, mStringList);
        mRecyclerView.setAdapter(mHomeAdapter);
    }

    private void setLinearView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
    }

    private void setGridView() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(
                this, 4));
    }

    private void setWaterfallView() {
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(
                4, StaggeredGridLayoutManager.VERTICAL
        ));
    }

    private class HomeHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;

        public HomeHolder(View itemView) {
            super(itemView);

            mTextView = itemView.findViewById(R.id.item);
        }
    }

    private class HomeAdapter extends RecyclerView.Adapter<HomeHolder> {

        private Context mContext;
        private List<String> mStringList;

        public HomeAdapter(Context context, List<String> stringList) {
            mContext = context;
            mStringList = stringList;
        }

        @NonNull
        @Override
        public HomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new HomeHolder(
                    LayoutInflater.from(mContext).inflate(
                            R.layout.list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull HomeHolder holder, int position) {
            holder.mTextView.setText(mStringList.get(position));
            holder.mTextView.setOnClickListener(v ->
                    Toast.makeText(mContext, "点击第" + (holder.getLayoutPosition() + 1) + "条",
                            Toast.LENGTH_SHORT).show());
            holder.mTextView.setOnLongClickListener(v -> {
                    new AlertDialog.Builder(mContext)
                        .setTitle("确认删除吗？")
                        .setPositiveButton("确定", (dialog, which) -> {
                            int pos = holder.getLayoutPosition();
                            mStringList.remove(pos);
                            notifyItemRemoved(pos);
                        })
                        .setNegativeButton("取消", null)
                        .show();
                    return false;
            });
        }

        @Override
        public int getItemCount() {
            return mStringList.size();
        }
    }
}
