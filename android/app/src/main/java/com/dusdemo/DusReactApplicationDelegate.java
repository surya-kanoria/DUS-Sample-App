package com.dusdemo;

import android.app.Activity;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.bridge.UiThreadUtil;
import com.flipkart.dus.DUSContracts;

import javax.annotation.Nullable;

/**
 * Created by surya.kanoria on 08/01/18.
 */

public class DusReactApplicationDelegate extends ReactActivityDelegate {
    private String mBundleName;
    private Context appContext;

    public DusReactApplicationDelegate(Activity activity, @Nullable String mainComponentName, @NonNull String bundleName, Context context) {
        super(activity, mainComponentName);
        mBundleName = bundleName;
        appContext = activity;
    }

    @Override
    protected void loadApp(final String appKey) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                Uri uri = DUSContracts.buildFetchPageUriWithRetry(mBundleName);
                final Cursor cursor = appContext.getContentResolver().query(uri, null, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    final String status = cursor.getString(cursor.getColumnIndex(DUSContracts.COLUMN_STATUS));
                    if (DUSContracts.LOADING.equalsIgnoreCase(status)) {
                        appContext.getContentResolver().registerContentObserver(DUSContracts.buildFetchPageUriWithRetry(mBundleName), true, new ContentObserver(null) {
                            @Override
                            public void onChange(boolean selfChange, final Uri uri) {
                                appContext.getContentResolver().unregisterContentObserver(this);
                                AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (DUSContracts.TRUE.equalsIgnoreCase(uri.getQueryParameter(DUSContracts.QUERY_ERROR))) {
                                            DusReactApplicationDelegate.super.loadApp(appKey);
                                        } else {
                                            Cursor requeryCursor = appContext.getContentResolver().query(DUSContracts.buildFetchPageUri(mBundleName), null, null, null, null);
                                            if (requeryCursor != null) {
                                                requeryCursor.moveToFirst();
                                                final String response = requeryCursor.getString(requeryCursor.getColumnIndex(DUSContracts.COLUMN_RESPONSE));
                                                if (TextUtils.isEmpty(response)) {
                                                    System.out.println("Bundle fetch failed");
                                                } else {
                                                    ((MainApplication) appContext.getApplicationContext()).setJSBundle(response);
                                                    DusReactApplicationDelegate.super.loadApp(appKey);
                                                }
                                                requeryCursor.close();
                                            } else {
                                                UiThreadUtil.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        DusReactApplicationDelegate.super.loadApp(appKey);
                                                    }
                                                });
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    } else if (DUSContracts.LOADED.equalsIgnoreCase(status)) {
                        final String response = cursor.getString(cursor.getColumnIndex(DUSContracts.COLUMN_RESPONSE));
                        ((MainApplication) appContext.getApplicationContext()).setJSBundle(response);
                        UiThreadUtil.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DusReactApplicationDelegate.super.loadApp(appKey);
                            }
                        });
                    } else if (DUSContracts.ERROR.equalsIgnoreCase(status)) {
                        System.out.println("Bundle Fetch failed");
                    }
                    cursor.close();
                }
            }
        });
    }
}
