package com.neolix.test;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class AppListAdapter extends BaseAdapter {

    private final Context context;
    private List<ResolveInfo> resInfo;
    private ResolveInfo res;
    public int currentPosition = -1;

    public AppListAdapter(List<ResolveInfo> resInfo, Context context) {
        this.resInfo = resInfo;
        this.context = context;
    }

    @Override
    public int getCount() {
        return resInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return resInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AppListItem item = convertView == null ? new AppListItem(context) : (AppListItem) convertView;
        boolean isCurrentItem = currentPosition == position;
        item.changeView(isCurrentItem);
        PackageManager packageManager = context.getPackageManager();

        res = resInfo.get(position);
        String className = res.activityInfo.name;
        Drawable drawable = res.loadIcon(packageManager);
        String name = res.loadLabel(packageManager).toString();
        item.bindData(name, drawable, className);
        return item;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

}