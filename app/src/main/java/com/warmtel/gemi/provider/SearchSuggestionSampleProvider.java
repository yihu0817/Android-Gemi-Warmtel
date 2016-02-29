package com.warmtel.gemi.provider;

import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import com.warmtel.gemi.GemiAppliction;
import com.warmtel.gemi.R;
import com.warmtel.gemi.model.AutoDTO;
import com.warmtel.gemi.model.AutoMessageDTO;

import java.util.List;

public class SearchSuggestionSampleProvider extends SearchRecentSuggestionsProvider {
	public final static String AUTHORITY = "com.warmtel.gemi.provider.SearchSuggestionSampleProvider";
	public final static int MODE = DATABASE_MODE_QUERIES;

	public SearchSuggestionSampleProvider() {
		super();
		setupSuggestions(AUTHORITY, MODE);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs, String sortOrder) {
		String[] items;
		if (selectionArgs[0] == null || "".equals(selectionArgs[0])) {
			Cursor cursor = super.query(uri, projection, selection, selectionArgs, sortOrder);
			int arrayLength = cursor.getCount();
			if (arrayLength != 0) {
				items = new String[arrayLength + 1];
				cursor.moveToFirst();
				int i = 0;
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					int number = cursor.getColumnIndex("suggest_intent_query");
					items[i] = cursor.getString(number);
					i++;
				}
				items[i] = getContext().getResources().getString(R.string.search_clear_key);
			} else {
				return null;
			}
		} else {
			String charKey = selectionArgs[0];    //因为平常只有一个，所以暂时就只需要这么处理
			AutoMessageDTO autoMessageDTO = GemiAppliction.mAction.getCheapAutoComplete(charKey);
			items = parseKey(autoMessageDTO.getInfo());
		}
		String[] columns = new String[]{"suggest_format", "suggest_text_1", "suggest_intent_query", "_id"};
		MatrixCursor stringCursor = new MatrixCursor(columns);
		String row[] = new String[4];
		int i = 0;
		for (CharSequence item : items) {
			row[0] = "" + 0;
			row[1] = item.toString();
			row[2] = item.toString();
			row[3] = "" + (++i);
			stringCursor.addRow(row);
		}
		return stringCursor;
	}


	public String[] parseKey(List<AutoDTO> returnKey){
		String[] keyArray = new String[returnKey.size()];
		for(int i = 0; i < returnKey.size(); i ++){
			keyArray[i] = returnKey.get(i).getKeyWord();
		}
		return keyArray;
	}
}
