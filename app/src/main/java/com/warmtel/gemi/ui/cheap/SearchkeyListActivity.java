package com.warmtel.gemi.ui.cheap;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.warmtel.gemi.R;
import com.warmtel.gemi.provider.SearchSuggestionSampleProvider;

public class SearchkeyListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchkey_list_layout);

        Log.e("tag","intent.getAction() :"+getIntent().getAction());
        doSearchQuery();
    }

    // 搜索
    private void doSearchQuery() {
        final Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // 获得搜索框里值
            String query = intent.getStringExtra(SearchManager.QUERY);
            // 清空搜索历史记录
            if (getString(R.string.search_clear_key).equalsIgnoreCase(query)) {
                clearSearchHistory();
                finish();
            }
            // 保存搜索记录
            else {
                SearchRecentSuggestions suggestions = new SearchRecentSuggestions(
                        this, SearchSuggestionSampleProvider.AUTHORITY,
                        SearchSuggestionSampleProvider.MODE);
                suggestions.saveRecentQuery(query, null);
            }
        }
    }
    // 清除的功能
    private void clearSearchHistory() {
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                SearchSuggestionSampleProvider.AUTHORITY,
                SearchSuggestionSampleProvider.MODE);
        suggestions.clearHistory();
    }
}
