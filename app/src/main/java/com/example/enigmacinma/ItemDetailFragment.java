package com.example.enigmacinma;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.enigmacinma.models.MovieContract;
import com.example.enigmacinma.models.MovieDBHelper;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    private SQLiteDatabase mDb;
    private Cursor mCursor;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            Activity activity = this.getActivity();
            mDb = new MovieDBHelper(activity).getWritableDatabase();
            Cursor cursor = mDb.rawQuery("SELECT * FROM " + MovieContract.MovieEntry.TABLE_NAME + " WHERE " + MovieContract.MovieEntry._ID + " = " + getArguments().getString(ARG_ITEM_ID), null);
            if (cursor.moveToFirst()) {
                mCursor = cursor;
            }
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null && mCursor != null) {
                appBarLayout.setTitle(mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME)));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mCursor != null) {
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME)));
            ((TextView) rootView.findViewById(R.id.movie_detail_description)).setText(mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_DESCRIPTION)));
            ((RatingBar) rootView.findViewById(R.id.movie_detail_s_review)).setRating(mCursor.getInt(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_S_REVIEW)));
            ((RatingBar) rootView.findViewById(R.id.movie_detail_r_review)).setRating(mCursor.getInt(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_R_REVIEW)));
            ((RatingBar) rootView.findViewById(R.id.movie_detail_m_review)).setRating(mCursor.getInt(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_M_REVIEW)));

            Date dt = new Date(mCursor.getLong(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_SHOW_DT)));
            ((TextView) rootView.findViewById(R.id.movie_detail_show_dt)).setText("Show at : " + new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(dt));

        }

        return rootView;
    }

    @Override
    public void onDestroy() {
        if (mCursor != null) mCursor.close();
        super.onDestroy();
    }
}
