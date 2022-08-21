package edu.aku.hassannaqvi.sero22hhlisting.ui.sections;

import static edu.aku.hassannaqvi.sero22hhlisting.core.MainApp.listings;

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

import edu.aku.hassannaqvi.sero22hhlisting.R;
import edu.aku.hassannaqvi.sero22hhlisting.contracts.TableContracts;
import edu.aku.hassannaqvi.sero22hhlisting.core.MainApp;
import edu.aku.hassannaqvi.sero22hhlisting.database.DatabaseHelper;
import edu.aku.hassannaqvi.sero22hhlisting.databinding.ActivityHh15Binding;

public class Hh15Activity extends AppCompatActivity {

    private static final String TAG = "Hh15Activity";
    ActivityHh15Binding bi;
    String st = "";
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_hh15);
        bi.setCallback(this);
        bi.setListings(listings);
        setSupportActionBar(bi.toolbar);
        st = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(new Date().getTime());
        db = MainApp.appInfo.dbHelper;

        bi.hhid.setText("SERO-" + MainApp.listings.getHh01() + "\n" + MainApp.selectedTab + "-" + String.format("%04d", MainApp.maxStructure) + "-" + String.format("%03d", MainApp.hhid));
        Toast.makeText(this, "Starting Completion", Toast.LENGTH_SHORT).show();

    }


    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);
    }


    public void btnContinue(View view) {
        if (!formValidation()) return;

        if (updateDB()) {
            finish();
            if (listings.getHh15().equals("1")) {
                startActivity(new Intent(this, FamilyListingActivity.class));
            } else {
                startActivity(new Intent(this, SectionBActivity.class));
            }
        } else Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();

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


    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back Press Not Allowed", Toast.LENGTH_LONG).show();
      /*  finish();
        startActivity(new Intent(this, MainActivity.class));*/
    }

}