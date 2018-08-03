package com.truetech.visearchleak;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.visenze.visearch.android.BaseSearchParams;
import com.visenze.visearch.android.IdSearchParams;
import com.visenze.visearch.android.ResultList;
import com.visenze.visearch.android.ViSearch;

import java.util.ArrayList;
import java.util.List;

public class ViSearchActivity extends AppCompatActivity {

    private static final String TAG = "ViSearchActivity";
    private ViSearch mViSearch;
    private ViSearch.ResultListener viSearchResultListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vi_search);
        mViSearch = new ViSearch
                .Builder("your Key......").build(this);
        viSearchResultListener = new ViSearch.ResultListener() {
            @Override
            public void onSearchResult(ResultList resultList) {
                Log.d(TAG, "onSearchResult: " + resultList.getPage());
                Toast.makeText(getApplicationContext(), "Working OK", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSearchError(String errorMessage) {
                Log.e(">>>>ViSearch", errorMessage);
            }

            @Override
            public void onSearchCanceled() {
                Log.e(">>>>ViSearch", "onSearchCanceled");
            }
        };
        mViSearch.setListener(viSearchResultListener);

        fetchSuggestedItems("your ID");
    }

    private void fetchSuggestedItems(String id) {
        mViSearch.cancelSearch();
        BaseSearchParams baseSearchParams = new BaseSearchParams();
        baseSearchParams.setLimit(16);
        baseSearchParams.setPage(1);
        List<String> fl = new ArrayList<>();
        fl.add("price");
        fl.add("brand");
        baseSearchParams.setFl(fl);
        baseSearchParams.setGetAllFl(true);
        IdSearchParams idSearchParams = new IdSearchParams(id);
        idSearchParams.setBaseSearchParams(baseSearchParams);
        mViSearch.idSearch(idSearchParams);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViSearch = null;
        viSearchResultListener = null;
    }
}
