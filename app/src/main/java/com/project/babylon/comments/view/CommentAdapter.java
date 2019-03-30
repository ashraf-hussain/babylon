package com.project.babylon.comments.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.project.babylon.R;
import com.project.babylon.comments.model.CommentModel;
import com.project.babylon.comments.commentDetail.CommentsDetailActivity;
import com.project.babylon.common.AppConstant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private static final String TAG = CommentAdapter.class.getName();

    private Context context;
    private List<CommentModel> commentModelList;
    private int lastPosition = -1;


    public CommentAdapter(List<CommentModel> musicModelList) {
        this.commentModelList = musicModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_comment, parent, false);
        context = view.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        setAnimation(holder.itemView, position);

        final CommentModel commentModel = commentModelList.get(position);
        holder.tvName.setText(commentModel.getName());
        holder.tvEmail.setText(commentModel.getEmail());
        holder.tvComments.setText(commentModel.getBody());
        holder.cvCommentAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, CommentsDetailActivity.class);
                i.putExtra(AppConstant.COMMENTS_DETAIL, commentModel);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return commentModelList == null ? 0 : commentModelList.size();

    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_email)
        TextView tvEmail;
        @BindView(R.id.tv_comments)
        TextView tvComments;
        @BindView(R.id.cv_comment_adapter)
        CardView cvCommentAdapter;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
