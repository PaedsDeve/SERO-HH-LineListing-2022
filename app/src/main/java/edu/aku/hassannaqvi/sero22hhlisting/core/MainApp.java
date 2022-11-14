package edu.aku.hassannaqvi.sero22hhlisting.core;

import static edu.aku.hassannaqvi.sero22hhlisting.database.CreateTable.DATABASE_NAME;
import static edu.aku.hassannaqvi.sero22hhlisting.database.DatabaseHelper.DATABASE_PASSWORD;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.media.ToneGenerator;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import androidx.core.app.ActivityCompat;

import com.edittextpicker.aliazaz.EditTextPicker;
import com.scottyab.rootbeer.RootBeer;
import com.validatorcrawler.aliazaz.Clear;

import net.sqlcipher.database.SQLiteDatabase;

import org.json.JSONArray;

import java.io.File;
import java.util.List;

import edu.aku.hassannaqvi.sero22hhlisting.BuildConfig;
import edu.aku.hassannaqvi.sero22hhlisting.R;
import edu.aku.hassannaqvi.sero22hhlisting.models.Child;
import edu.aku.hassannaqvi.sero22hhlisting.models.Cluster;
import edu.aku.hassannaqvi.sero22hhlisting.models.Listings;
import edu.aku.hassannaqvi.sero22hhlisting.models.Mwra;
import edu.aku.hassannaqvi.sero22hhlisting.models.Users;
import edu.aku.hassannaqvi.sero22hhlisting.ui.LockActivity;


public class MainApp extends Application {

    public static final String PROJECT_NAME = "sero22listing";
    public static final String DIST_ID = null;
    public static final String SYNC_LOGIN = "sync_login";
    public static final String _IP = "https://vcoe1.aku.edu";// .LIVE server
    // public static final String _IP = "http://f49461:8080/prosystem";// .TEST server
    //public static final String _IP = "http://43.245.131.159:8080";// .TEST server


    //public static final String _HOST_URL = MainApp._IP + "/tpvics_r2/api/";// .TEST server;
    public static final String _HOST_URL = MainApp._IP + "/sero_2022/api/";// .TEST server;


    public static final String _SERVER_URL = "syncGCM.php";
    public static final String _SERVER_GET_URL = "getDataGCM.php";
    public static final String _PHOTO_UPLOAD_URL = _HOST_URL + "uploads.php";


    //public static final String _UPDATE_URL = MainApp._IP + "/tpvics_r2/app/listing";
    public static final String _UPDATE_URL = MainApp._IP + "/sero_2022/app/listing";


    public static final String _APP_FOLDER = "../app/listing";
    public static final String _EMPTY_ = "";
    public static final String _USER_URL = "resetpasswordgcm.php";
    private static final String TAG = "MainApp";
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
    public static int TRATS = 8;
    public static String IBAHC = "";
    //COUNTRIES
    public static int PAKISTAN = 1;
    public static int TAJIKISTAN = 3;
    public static File sdDir;
    public static String[] downloadData;
    // Tables
    public static Listings listings;
    public static Mwra mwra;
    public static Child child;
    public static String DeviceURL = "devices.php";
    public static AppInfo appInfo;
    public static Users user;
    public static Boolean admin = false;
    public static List<JSONArray> uploadData;
    public static SharedPreferences.Editor editor;
    public static SharedPreferences sharedPref;
    public static String deviceid;
    public static int versionCode = BuildConfig.VERSION_CODE;
    public static String versionName = BuildConfig.VERSION_NAME;
    public static int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 2;
    public static long TWO_MINUTES = 1000 * 60 * 2;
    public static boolean permissionCheck = false;
    public static int idType = 0;
    public static boolean mwraComplete;
    public static boolean childComplete;
    public static boolean pregComplete;
    public static int mwraCount = 0;
    public static int num_chlid_12_23 = 0;
    public static int isback_family_listing = 0;
    public static int childCount = 0;
    public static int pregCount = 0;
    public static String selectedFemale = "";
    public static String selectedChild = "";
    public static String selectedPreg = "";
    public static int mwraCountComplete = 0;
    public static int childCountComplete = 0;
    public static int pregCountComplete = 0;
    public static List<String> subjectNames;
    public static Cluster selectedCluster;
    public static int maxStructure;
    public static int hhid;
    public static int HHCount = 0;
    public static String[] clusterInfo;
    public static String selectedTab;
    protected static LocationManager locationManager;
    public static CountDownTimer timer;
    static ToneGenerator toneGen1;

    public static void hideSystemUI(View decorView) {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    public static String getDeviceId(Context context) {
        String deviceId;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            deviceId = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } else {
           /* final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephony.getDeviceId() != null) {
                deviceId = mTelephony.getDeviceId();
            } else {
                deviceId = Settings.Secure.getString(
                        context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }*/
        }
        return "deviceId";
    }

    public static void cbCheck(CheckBox cb1, CheckBox cb2, CheckBox cb3, EditTextPicker edt) {
        cb1.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                Clear.clearAllFields(edt, false);
                cb2.setChecked(false);
                cb2.setEnabled(false);
                cb3.setChecked(false);
                cb3.setEnabled(false);
            } else {
                Clear.clearAllFields(edt, true);
                cb2.setEnabled(true);
                cb3.setEnabled(true);
            }
        });
    }

    public static Boolean isNetworkAvailable(Context c) {
        ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network nw = connectivityManager.getActiveNetwork();
            if (nw == null) return false;
            NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
            return actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH));
        } else {
            NetworkInfo nwInfo = connectivityManager.getActiveNetworkInfo();
            return nwInfo != null && nwInfo.isConnected();
        }
    }

    public static void lockScreen(Context c) {

        if (timer != null) {
            timer.cancel();
        }

        //   Context mContext = c;
        Activity activity = (Activity) c;


        timer = new CountDownTimer(15 * 60 * 1000, 1000) {
            //timer = new CountDownTimer(30 * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                //Some code
                //bi.timeLeft.setText((millisUntilFinished / 1000) + " secs left");
                if ((millisUntilFinished / 1000) < 14) {
                    toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                }

            }

            public void onFinish() {
                //Logout
                //
                //   finish();
                // lockScreen();
                Intent intent = new Intent();
                intent.setClass(c, LockActivity.class);
                c.startActivity(intent);
                timer.cancel();
                //  startActivity(new Intent(((Activity) c).getLocalClassName(), LockActivity.class));
            }
        };
        timer.start();

    }


    @Override
    public void onCreate() {
        super.onCreate();
        RootBeer rootBeer = new RootBeer(this);
        if (rootBeer.isRooted()) {
            android.os.Process.killProcess(android.os.Process.myPid());
            throw new RuntimeException("This is a crash");

            //System.exit(1);
        }

        //Initiate DateTime
        //Initializ App info
        appInfo = new AppInfo(this);
        sharedPref = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        editor = sharedPref.edit();
        MainApp.editor
                .putString("mh01", "")
                .apply();
        deviceid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MINIMUM_TIME_BETWEEN_UPDATES,
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new GPSLocationListener(this) // Implement this class from code

        );

        initSecure();
    }

    private void initSecure() {
        // Initialize SQLCipher library
        SQLiteDatabase.loadLibs(this);
        File databaseFile = getDatabasePath(DATABASE_NAME);
       /* databaseFile.mkdirs();
        databaseFile.delete();*/
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(databaseFile, DATABASE_PASSWORD, null);
        // Prepare encryption KEY
        ApplicationInfo ai = null;
        try {
            ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            TRATS = bundle.getInt("YEK_TRATS");
            //IBAHC = bundle.getString("YEK_REVRES").substring(TRATS, TRATS + 16);
            IBAHC = bundle.getString("YEK_REVRES");
            Log.d(TAG, "onCreate: YEK_REVRES = " + IBAHC);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

}
