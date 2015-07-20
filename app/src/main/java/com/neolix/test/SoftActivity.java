package com.neolix.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static com.neolix.test.Configuration.setPrivateKey;

public class SoftActivity extends Activity implements OnItemClickListener {

    private ListView softlist = null;
    private Context mContext;
    private PackageManager mPackageManager;
    private List<ResolveInfo> mAllApps = new ArrayList<>();
    private AppListAdapter appListAdapter;
    private ImageButton msgBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.software);
        setTitle("文件管理器");
        mContext = this;
        mPackageManager = getPackageManager();

        softlist = (ListView) findViewById(R.id.softlist);
        msgBt = (ImageButton) findViewById(R.id.msg);

        msgBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SoftActivity.this, MessageActivity.class);
                startActivity(intent);
            }
        });

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        mAllApps = mPackageManager.queryIntentActivities(mainIntent, 0);
        softlist.setOnItemClickListener(this);
        int markIndext = -1;
        for (int i = 0; i <= mAllApps.size() - 1; i++) {
            if (mAllApps.get(i).activityInfo.packageName.equals("com.neolix.test")) {
                markIndext = i;
            }
        }

        if (markIndext != -1) {
            mAllApps.remove(markIndext);
        }

        appListAdapter = new AppListAdapter(mAllApps, mContext);
        softlist.setAdapter(appListAdapter);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
        ResolveInfo res = mAllApps.get(position);
        String pkg = res.activityInfo.packageName;

        String cls = res.activityInfo.name;
        appListAdapter.setCurrentPosition(position);
        appListAdapter.notifyDataSetChanged();

        setPrivateKey(SoftActivity.this, pkg, cls);
    }

}