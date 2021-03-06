package kay.golden.json.tasks;

import java.util.ArrayList;

import kay.golden.json.R;
import kay.golden.json.ZendeskFace;
import kay.golden.json.data.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * AsyncTask for fetching tickets from Zendesk (very important part of app,do
 * changes carefully )
 * 
 * @author Kay Nag
 */
public class APITask extends AsyncTask<String, Integer, String> {
	private ProgressDialog progDialog;
	private Context context;
	private ZendeskFace activity;
	private static final String debugTag = "APITask";

	/**
	 * Construct a task
	 * 
	 * @param activity
	 */
	public APITask(ZendeskFace activity) {
		super();
		this.activity = activity;
		this.context = this.activity.getApplicationContext();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progDialog = ProgressDialog.show(this.activity, "Search", this.context
				.getResources().getString(R.string.looking_for_tickets), true,
				false);
	}

	@Override
	protected String doInBackground(String... params) {
		
		try {
			Log.d(debugTag, "Background:" + Thread.currentThread().getName());
			String result = JSONFetchHelper.downloadFromServer(params);
			
			return result;
		} catch (Exception e) {
			return new String();
		}
	}

	@Override
	protected void onPostExecute(String result) {
		progDialog.dismiss();
		ArrayList<Data> ticketsdata = new ArrayList<Data>();

		
		if (result.length() == 0) {
			this.activity.alert("Unable to find tickets. Try again later.");
			return;
		}

		try {
			JSONArray tickets = new JSONArray(result);
			
			
			for (int i = 0; i < tickets.length(); i++) {
				String authour,price,image = null;
				JSONObject ticketsdetail = tickets.getJSONObject(i);
				String title = ticketsdetail.getString("title");
				String id = ticketsdetail.getString("id");
				String link = ticketsdetail.getString("link");
				
			String insidejson = null;
					
					try {
						
						 insidejson = JSONFetchHelper.downloadFromServer("http://assignment.gae.golgek.mobi" + link);
						 
						 
						 						
					} catch (Exception e) {
						this.cancel(true);
						
					}					
					if (insidejson.length() == 0) {
						this.activity.alert("Unable to find tickets. Try again later.");
						return;
					}
					
					JSONObject details = new JSONObject(insidejson);
					
					 authour = details.getString("author");
					 price = details.getString("price");
					 image = details.getString("image");
					
										
					 
				
				ticketsdata.add(new Data(title,id,image,authour,link,price));
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.activity.settickets(ticketsdata);

	}
	
	
}