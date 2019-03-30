package com.project.babylon.comments.commentDetail;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.babylon.R;
import com.project.babylon.comments.model.CommentModel;
import com.project.babylon.common.AppConstant;
import com.project.babylon.common.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class CommentsDetailActivity extends BaseActivity {

    private static final String TAG = CommentsDetailActivity.class.getName();
    @BindView(R.id.tb_title)
    TextView tbTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_comments)
    TextView tvComments;
    @BindView(R.id.cv_comment_adapter)
    LinearLayout cvCommentAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_comment_detail;
    }

    @Override
    protected void init() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent = getIntent();

        if (intent.getExtras() != null) {
            CommentModel commentModel = (CommentModel)
                    intent.getSerializableExtra(AppConstant.COMMENTS_DETAIL);
            tvName.setText(commentModel.getName());
            tvEmail.setText(commentModel.getEmail());
            tvComments.setText(commentModel.getBody());
        }
    }

    @OnClick(R.id.toolbar)
    public void onViewClicked() {
        onBackPressed();
    }

}
