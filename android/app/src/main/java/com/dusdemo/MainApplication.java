package com.dusdemo;

import android.app.Application;
import android.database.Cursor;
import android.os.AsyncTask;

import com.dusdemo.dusdependencies.ComponentDownloader;
import com.dusdemo.dusdependencies.DusLoggerResolver;
import com.dusdemo.dusdependencies.FileConfigDownloader;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;
import com.flipkart.dus.DUSContracts;
import com.flipkart.dus.DusApplication;
import com.flipkart.dus.DusDependencyResolver;
import com.flipkart.dus.DusReactNativeHost;

import java.util.Arrays;
import java.util.List;

public class MainApplication extends Application implements ReactApplication, DusApplication {

    private final DusReactNativeHost mReactNativeHost = new DusReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            return false;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage()
            );
        }
    };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SoLoader.init(this, /* native exopackage */ false);
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = getApplicationContext().getContentResolver().query(DUSContracts.buildFetchUpdateGraphUri(), null, null, null, null);
                if (cursor != null) {
                    cursor.close();
                }
            }
        });
    }

    @Override
    public DusDependencyResolver getDusDependencyResolver() {
        DusDependencyResolver dependencyResolver = new DusDependencyResolver();
        dependencyResolver.setComponentRequestInterface(new ComponentDownloader())
                .setDusLogger(new DusLoggerResolver())
                .setFileConfigRequestInterface(new FileConfigDownloader())
                .setPackagedDbName("")
                .setPackagedDbVersion(0);
        return dependencyResolver;
    }

    @Override
    public DusReactNativeHost getDusReactNativeHost() {
        return mReactNativeHost;
    }
}
