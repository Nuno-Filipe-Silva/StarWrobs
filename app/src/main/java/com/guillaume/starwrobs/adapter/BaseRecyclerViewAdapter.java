package com.guillaume.starwrobs.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guillaume.starwrobs.R;
import com.guillaume.starwrobs.activities.DetailActivity;
import com.guillaume.starwrobs.data.database.brite.SimpleGenericObjectForRecyclerview;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;
import timber.log.Timber;

public class BaseRecyclerViewAdapter
        extends RecyclerView.Adapter<BaseRecyclerViewAdapter.ViewHolder>
        implements Action1<List<SimpleGenericObjectForRecyclerview>> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<SimpleGenericObjectForRecyclerview> mValues = Collections.emptyList();

    public BaseRecyclerViewAdapter(Context context) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
    }

    public SimpleGenericObjectForRecyclerview getValueAt(int position) {
        return mValues.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mBoundString = mValues.get(position).name;
        holder.mTextView.setText(mValues.get(position).name);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.KEY_CATEGORY, getValueAt(position).categoryId);
                intent.putExtra(DetailActivity.KEY_OBJECT_ID, getValueAt(position).id);
                intent.putExtra(DetailActivity.KEY_NAME, getValueAt(position).name);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public void call(List<SimpleGenericObjectForRecyclerview> strings) {
        this.mValues = strings;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_name)
        TextView mTextView;

        View mView;

        public String mBoundString;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextView.getText();
        }
    }
}
