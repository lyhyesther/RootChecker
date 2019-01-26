package com.hook.rooter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.hook.rooter.util.Utils;


/**
 * class to pretend we are doing some really clever stuff that takes time
 * <p/>
 * Old skool Async - this could been nicer but just threw together at the mo
 */
public class CheckRootTask extends AsyncTask<Boolean, Integer, Boolean> {

    private static final int SLEEP_TIME = 70;
    private static final String TAG = "CheckRootTask";

    private final Context mContext;


    private OnCheckRootFinishedListener mListener;

    private boolean mIsCheck;

    public interface OnCheckRootFinishedListener {
        void onCheckRootFinished(boolean isRooted);
    }

    public CheckRootTask(Context ctx, OnCheckRootFinishedListener listener
    ) {
        mListener = listener;

        mContext = ctx;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mIsCheck = false;
    }

    @Override
    protected Boolean doInBackground(Boolean... params) {
        RootChecker check = new RootChecker(mContext);
        check.setLogging(true);

        for (int i = 0; i < 90; i++) {
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {

            }
            switch (i) {
                case 8:
                    mIsCheck = check.detectRootManagementApps();
                    Log.d(TAG, "Root Management Apps " + (mIsCheck ? "detected" : "not detected"));
                    break;
                case 16:
                    mIsCheck = check.detectPotentiallyDangerousApps();
                    Log.d(TAG, "PotentiallyDangerousApps " + (mIsCheck ? "detected" : "not detected"));
                    break;
                case 24:
                    mIsCheck = check.detectTestKeys();
                    Log.d(TAG, "TestKeys " + (mIsCheck ? "detected" : "not detected"));
                    break;
                case 32:
                    mIsCheck = check.checkForBusyBoxBinary();
                    Log.d(TAG, "BusyBoxBinary " + (mIsCheck ? "detected" : "not detected"));
                    break;
                case 40:
                    mIsCheck = check.checkForSuBinary();
                    Log.d(TAG, "SU Binary " + (mIsCheck ? "detected" : "not detected"));
                    break;
                case 48:
                    mIsCheck = check.checkSuExists();
                    Log.d(TAG, "2nd SU Binary check " + (mIsCheck ? "detected" : "not detected"));
                    break;
                case 56:
                    mIsCheck = check.checkForRWPaths();
                    Log.d(TAG, "ForRWPaths " + (mIsCheck ? "detected" : "not detected"));
                    break;
                case 64:
                    mIsCheck = check.checkForDangerousProps();
                    Log.d(TAG, "DangerousProps " + (mIsCheck ? "detected" : "not detected"));
                    break;
                case 72:
                    mIsCheck = check.checkForRootNative();
                    Log.d(TAG, "Root via native check " + (mIsCheck ? "detected" : "not detected"));
                    break;
                case 80:
                    mIsCheck = check.detectRootCloakingApps();
                    Log.d(TAG, "RootCloakingApps " + (mIsCheck ? "detected" : "not detected"));
                    break;
                case 88:
                    mIsCheck = Utils.isSelinuxFlagInEnabled();
                    Log.d(TAG, "Selinux Flag Is Enabled " + (mIsCheck ? "true" : "false"));
                    break;
                case 89:
                    mIsCheck = check.checkForMagiskBinary();
                    Log.d(TAG, "Magisk " + (mIsCheck ? "deteced" : "not deteced"));
                    break;
            }
            publishProgress(i);
        }
        return check.isRooted();
    }

    @Override
    protected void onPostExecute(Boolean isRooted) {
        super.onPostExecute(isRooted);
        mListener.onCheckRootFinished(isRooted);
    }

}
