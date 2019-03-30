package com.project.babylon.postDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.widget.TextView;

import com.project.babylon.R;
import com.project.babylon.common.AppConstant;
import com.project.babylon.common.BaseActivity;
import com.project.babylon.model.PostModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PostDetailActivity extends BaseActivity {

    private static final String TAG = PostDetailActivity.class.getName();
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_body)
    TextView tvBody;

    @Override
    protected int getLayout() {
        return R.layout.activity_post_detail;
    }

    @Override
    protected void init() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent = getIntent();

        if (intent.getExtras() != null) {
            PostModel postModel = (PostModel) intent.getSerializableExtra(AppConstant.POST_DETAIL);
            tvTitle.setText(postModel.getTitle());
            tvBody.setText(postModel.getBody());
        }
    }

    @OnClick(R.id.toolbar)
    public void onViewClicked() {
        onBackPressed();
    }
}
