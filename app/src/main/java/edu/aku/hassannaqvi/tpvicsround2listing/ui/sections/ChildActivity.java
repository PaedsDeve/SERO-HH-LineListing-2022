package edu.aku.hassannaqvi.tpvicsround2listing.ui.sections;

import static edu.aku.hassannaqvi.tpvicsround2listing.core.MainApp.child;
import static edu.aku.hassannaqvi.tpvicsround2listing.core.MainApp.listings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.aku.hassannaqvi.tpvicsround2listing.R;
import edu.aku.hassannaqvi.tpvicsround2listing.contracts.TableContracts;
import edu.aku.hassannaqvi.tpvicsround2listing.core.MainApp;
import edu.aku.hassannaqvi.tpvicsround2listing.database.DatabaseHelper;
import edu.aku.hassannaqvi.tpvicsround2listing.databinding.ActivityChildBinding;
import edu.aku.hassannaqvi.tpvicsround2listing.models.Listings;
import edu.aku.hassannaqvi.tpvicsround2listing.models.Child;

public class ChildActivity extends AppCompatActivity {

    private static final String TAG = "ChildActivity";
    ActivityChildBinding bi;
    String st = "";
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_child);
        bi.setCallback(this);
        bi.setListings(listings);
        setSupportActionBar(bi.toolbar);
        st = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(new Date().getTime());
        db = MainApp.appInfo.dbHelper;

        MainApp.num_chlid_12_23++;
        bi.hhid.setText("SERO22-" + MainApp.listings.getHh01() + "\n" + MainApp.selectedTab + "-" + String.format("%04d", MainApp.maxStructure) + "-" + String.format("%03d", MainApp.hhid));
        bi.childSno.setText("Child#: " + MainApp.num_chlid_12_23 + " of " + MainApp.listings.getHh14a());

        Toast.makeText(this, "Staring Chlid", Toast.LENGTH_SHORT).show();
    }


    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back Press Not Allowed", Toast.LENGTH_LONG).show();
    }


    private boolean insertRecord() {
        long rowId = 0;

        try {

            listings.setHhchlidsno(String.valueOf(MainApp.num_chlid_12_23));

            rowId = db.addChild(listings);

            if (rowId > 0) {
                long updCount = 0;

                listings.setId(String.valueOf(rowId));
                listings.setUid(listings.getDeviceId() + listings.getId());
                listings.setUuid(listings.getDeviceId() + listings.getId() + listings.getHhchlidsno());

                updCount = db.updateChildColumn(TableContracts.ListingsTable.COLUMN_UUID, listings.getUuid());

                if (updCount > 0) {
                    return true;
                }

            } else {
                Toast.makeText(this, "Updating Database… ERROR!", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException(CR):" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return false;
    }


    private boolean updateDB() {
        long updcount = 0;
        try {
            updcount = db.updateFormColumn(TableContracts.ListingsTable.COLUMN_SC, listings.sCtoString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, R.string.upd_db_form + e.getMessage());
            Toast.makeText(this, R.string.upd_db_form + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (updcount > 0) return true;
        else {
            Toast.makeText(this, R.string.upd_db_error, Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    public void btnContinue(View view) {
        if (!formValidation()) return;

        //saveDraft();

        if (insertRecord()) {
            finish();
            if (MainApp.num_chlid_12_23 < Integer.parseInt(MainApp.listings.getHh14a())) {
                startActivity(new Intent(this, ChildActivity.class));
                //     startActivity(new Intent(this, SectionBActivity.class));
            } else {
                MainApp.isback_family_listing = 2;
                startActivity(new Intent(this, FamilyListingActivity.class));
            }
        } else Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
    }


    private void saveDraft_seperate_table() {
        child = new Child();
        child.setUuid(MainApp.listings.getUid());
        child.setSysDate(MainApp.listings.getSysDate());
        child.setUserName(MainApp.user.getUserName());
        child.setDeviceId(MainApp.appInfo.getDeviceID());
        child.setDeviceTag(MainApp.appInfo.getTagName());
        child.setAppver(MainApp.appInfo.getAppVersion());

        child.setIdentification();
        child.setSno(String.valueOf(MainApp.num_chlid_12_23));

        child.setHh20(String.valueOf(MainApp.maxStructure));
        child.setHh21(String.valueOf(MainApp.hhid));
        child.setHh13cname(String.valueOf(bi.hh13cname));

        try {
            child.setsChild(child.sChildtoString());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException(SB): " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    private void saveDraft_seperatetable() {
        child = new Child();
        child.setUuid(MainApp.listings.getUid());
        child.setSysDate(MainApp.listings.getSysDate());
        child.setUserName(MainApp.user.getUserName());
        child.setDeviceId(MainApp.appInfo.getDeviceID());
        child.setDeviceTag(MainApp.appInfo.getTagName());
        child.setAppver(MainApp.appInfo.getAppVersion());

        child.setIdentification();
        child.setSno(String.valueOf(MainApp.num_chlid_12_23));

        child.setHh20(String.valueOf(MainApp.maxStructure));
        child.setHh21(String.valueOf(MainApp.hhid));
        child.setHh13cname(String.valueOf(bi.hh13cname));

        try {
            child.setsChild(child.sChildtoString());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException(SB): " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

}