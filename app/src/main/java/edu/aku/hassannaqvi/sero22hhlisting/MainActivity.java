package edu.aku.hassannaqvi.sero22hhlisting;

import static edu.aku.hassannaqvi.sero22hhlisting.core.MainApp.sharedPref;
import static edu.aku.hassannaqvi.sero22hhlisting.ui.LoginActivity.dbBackup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import java.io.File;

import edu.aku.hassannaqvi.sero22hhlisting.core.MainApp;
import edu.aku.hassannaqvi.sero22hhlisting.database.AndroidManager;
import edu.aku.hassannaqvi.sero22hhlisting.databinding.ActivityMainBinding;
import edu.aku.hassannaqvi.sero22hhlisting.ui.ChangePasswordActivity;
import edu.aku.hassannaqvi.sero22hhlisting.ui.SyncActivity;
import edu.aku.hassannaqvi.sero22hhlisting.ui.lists.ListingsReporter;
import edu.aku.hassannaqvi.sero22hhlisting.ui.sections.SectionAActivity;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding bi;
    SharedPreferences sp;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_main);
        bi.setCallback(this);
        setSupportActionBar(bi.toolbar);
        bi.adminView.setVisibility(MainApp.admin ? View.VISIBLE : View.GONE);
        bi.username.setText("Welcome, " + MainApp.user.getFullname() + "!");


        String latestVersionName = sharedPref.getString("versionName", "");
        int latestVersionCode = Integer.parseInt(sharedPref.getString("versionCode", "0"));

        if (MainApp.appInfo.getVersionCode() < latestVersionCode) {
            bi.newApp.setVisibility(View.VISIBLE);
            bi.newApp.setText("NOTICE: There is a newer version of this app available on server (" + latestVersionName + latestVersionCode + "). \nPlease download update the app now.");
        } else {
            bi.newApp.setVisibility(View.GONE);

        }
//        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    public void sectionPress(View view) {
        switch (view.getId()) {

            case R.id.openChildForm:
                //MainApp.cr = new Listings();
                //    finish();
//                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                startActivity(new Intent(this, SectionAActivity.class));
//                } else Toast.makeText(this, "Please turn on Location", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dbm:
                startActivity(new Intent(this, AndroidManager.class));
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.action_database:
                intent = new Intent(MainActivity.this, AndroidManager.class);
                startActivity(intent);
                break;
            case R.id.action_data_sync:
                intent = new Intent(MainActivity.this, SyncActivity.class);
                startActivity(intent);
                break;
            case R.id.action_view_listing:
                intent = new Intent(MainActivity.this, ListingsReporter.class);
                startActivity(intent);
                break;
            case R.id.changePassword:
                intent = new Intent(MainActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.sendDB:
                sendEmail();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem action_database = menu.findItem(R.id.action_database);

        action_database.setVisible(MainApp.admin);
        return true;

    }

    // Email database to specified email address as attachment
    private void sendEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"khalid.feroz@aku.edu"});
        emailIntent.putExtra(Intent.EXTRA_CC, new String[]{"omar.shoaib@aku.edu", "hussain.siddiqui@aku.edu"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Sero22 HH Listing Database - For Issue Monitoring");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Sero22 HH Listing database upload from the device which has issues while uploading the data." +
                "This is just for testing/checking purpose.");
        File file = dbBackup(this);
//        File file = copyFileToFilesDir(DATABASE_NAME);
        if (file == null || !file.exists() || !file.canRead()) {
            Toast.makeText(this, getString(R.string.file_not_found), Toast.LENGTH_SHORT).show();
            return;
        }
//        Uri uri =  FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file);

//        Uri uri = Uri.fromFile(file);

//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
//        String type = mime.getMimeTypeFromExtension(ext);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            emailIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(this, "edu.aku.hassannaqvi.sero22hhlisting.fileProvider", file);
        }else{
            uri = Uri.fromFile(file);
        }
//        emailIntent.setDataAndType(uri, type);
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(emailIntent, getString(R.string.pick_email_provider)));
    }
}