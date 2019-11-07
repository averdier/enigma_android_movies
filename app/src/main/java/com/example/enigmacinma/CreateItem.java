package com.example.enigmacinma;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.enigmacinma.models.MovieContract;
import com.example.enigmacinma.models.MovieDBHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateItem extends AppCompatActivity {

    EditText mName;
    EditText mDate;
    EditText mTime;
    EditText mDescription;
    RatingBar mR;
    RatingBar mS;
    RatingBar mM;
    Button mCreate;

    SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        Toolbar toolbar = (Toolbar) findViewById(R.id.create_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mName = findViewById(R.id.ed_name);
        mDate = findViewById(R.id.ed_date);
        mTime = findViewById(R.id.ed_time);
        mDescription = findViewById(R.id.ml_description);
        mR = findViewById(R.id.rt_r);
        mS = findViewById(R.id.rt_s);
        mM = findViewById(R.id.rt_m);
        mCreate = findViewById(R.id.b_create);
        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long result = createItem();
                if (result != -1) startActivity(new Intent(CreateItem.this, ItemListActivity.class));
            }
        });

        mDb = new MovieDBHelper(this).getWritableDatabase();
    }

    Boolean isValidItem() {
        return
                mName.getText().length() != 0
                && mDate.getText().length() != 0
                && mDescription.getText().length() != 0
                && mDate.getText().length() != 0
                && mTime.getText().length() != 0;
    }

    long createItem() {
        if (!isValidItem()) {
            Toast.makeText(this, "Invalid form", Toast.LENGTH_SHORT).show();
            return -1;
        };

        try {
            Date dt = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(
                    mDate.getText().toString() + " " + mTime.getText().toString()
            );

            ContentValues cv = new ContentValues();
            cv.put(MovieContract.MovieEntry.COLUMN_NAME, mName.getText().toString());
            cv.put(MovieContract.MovieEntry.COLUMN_DESCRIPTION, mDescription.getText().toString());
            cv.put(MovieContract.MovieEntry.COLUMN_M_REVIEW, mM.getRating());
            cv.put(MovieContract.MovieEntry.COLUMN_R_REVIEW, mR.getRating());
            cv.put(MovieContract.MovieEntry.COLUMN_S_REVIEW, mS.getRating());
            cv.put(MovieContract.MovieEntry.COLUMN_SHOW_DT, dt.getTime());

            return mDb.insert(MovieContract.MovieEntry.TABLE_NAME, null, cv);
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Invalid date or time format", Toast.LENGTH_SHORT).show();
        }

        return -1;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, ItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
