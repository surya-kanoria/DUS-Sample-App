package com.dusdemo;

import android.app.Application;

import com.dusdemo.dusdependencies.ComponentDownloader;
import com.dusdemo.dusdependencies.DusLoggerResolver;
import com.dusdemo.dusdependencies.FileConfigDownloader;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;
import com.flipkart.dus.DusApplication;
import com.flipkart.dus.DusDependencyResolver;

import java.util.Arrays;
import java.util.List;

public class MainApplication extends Application implements ReactApplication, DusApplication {
    private String mJSBundle = "";

    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
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

        @Override
        protected String getJSBundleFile() {
            return mJSBundle;
        }
    };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    public void setJSBundle(String jsBundle) {
        mJSBundle = jsBundle;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SoLoader.init(this, /* native exopackage */ false);
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
}
