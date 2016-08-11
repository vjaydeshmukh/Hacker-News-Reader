package me.abhrajit.hackernewsreader.service;
/*
    Copyright 2016 Abhrajit Mukherjee

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

import me.abhrajit.hackernewsreader.webcalls.HnApiCall;
import me.abhrajit.hackernewsreader.widget.HNWidget;

public class NewsGcmService extends GcmTaskService {
    Context mContext;
    public NewsGcmService(Context context){
        mContext=context;
    }
    public NewsGcmService(){

    }
    @Override
    public int onRunTask(TaskParams taskParams) {
        Log.v("test","test");
        if (mContext==null) mContext=this;
        HnApiCall api=new HnApiCall(mContext);
        api.getNews();

//Calling the widget intent
        Intent intent = new Intent(mContext.getApplicationContext(), HNWidget.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int ids[] = AppWidgetManager.getInstance(mContext.getApplicationContext()).getAppWidgetIds(
                new ComponentName(mContext.getApplicationContext(), HNWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        mContext.sendBroadcast(intent);


        return 0;
    }
}
