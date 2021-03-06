package kay.golden.json.adapters;

import java.util.ArrayList;

import kay.golden.json.List_view_selection;
import kay.golden.json.R;
import kay.golden.json.ZendeskFace;
import kay.golden.json.ZendeskFace.MyViewHolder;
import kay.golden.json.data.Data;
import kay.golden.json.tasks.IconTask;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Adapter for tickets to fill in the rows in list view
 * 
 * @author Kay Nag
 * 
 */
public class DataAdapter extends BaseAdapter implements OnClickListener {

	private static final String debugTag = "DataAdapter";
	private ZendeskFace activity;
	private IconTask imgFetcher;
	Data position;
	private LayoutInflater layoutInflater;
	private ArrayList<Data> ticketsdata;
	
	public DataAdapter(ZendeskFace a, IconTask i,
			LayoutInflater l, ArrayList<Data> data) {
		this.activity = a;
		this.imgFetcher = i;
		this.layoutInflater = l;
		this.ticketsdata = data;
			}

	@Override
	public int getCount() {
		return this.ticketsdata.size();
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		MyViewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.ticketdetail, parent,
					false);
			 holder = new MyViewHolder();
		
			
			holder.title = (TextView) convertView.findViewById(R.id.subject);
			
		
			
			holder.authour = (TextView) convertView
					.findViewById(R.id.ticket_description);
			holder.icon = (ImageView) convertView.findViewById(R.id.album_icon);

			convertView.setTag(holder);
		} else {
			holder = (MyViewHolder) convertView.getTag();
		}

		convertView.setOnClickListener(this);
		 position  = ticketsdata.get(pos);
		
	
		holder.ticketsdata = position;
		if (position.gettitle() != null) {
			holder.title.setText("Title : " + position.gettitle());

		} else {
			holder.title.setText("Title : No Subject");
		}

		holder.authour.setText("Authour " + position.getauthour());
		
	

		if (position.getImageUrl() != null) {
			holder.icon.setTag(position.getImageUrl());
			Drawable dr = imgFetcher.loadImage(this, holder.icon);
			if (dr != null) {
				holder.icon.setImageDrawable(dr);
			}
		} else {
			holder.icon.setImageResource(R.drawable.filler_icon);
		}
		

		return convertView;
	}

	
	
	
	
	public void onClick(View v) {
		
		if (v instanceof View) {
			MyViewHolder holder = (MyViewHolder) v.getTag();
		
		Intent in = new Intent(this.activity,List_view_selection.class);
			
		String title = holder.ticketsdata.gettitle();
		String authour =  holder.ticketsdata.getauthour();
		String price = holder.ticketsdata.getprice();
		String Image = holder.ticketsdata.getImageUrl();
		String id = holder.ticketsdata.getid();
		
		 in.putExtra("name",title);
         in.putExtra("writer",authour);
         in.putExtra("rate",price);
         in.putExtra("image",Image);
         in.putExtra("id",id);
         
         this.activity.startActivity(in);

	}

		
		

	}
}
