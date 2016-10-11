package com.peso.model;

import java.util.ArrayList;
import java.util.List;

import Thread.CommThread;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.peso.R;

/*
 * 创建人：林雄城
 * 修改人：周映凯
 * 主要功能：高级搜索界面
 */
public class Advanced_Search extends Activity implements
		OnItemSelectedListener, OnClickListener {
	// 下拉列表控件
	private EditText allSearch;
	private EditText accurateSearch;
	private EditText authorName;
	private EditText publishMonth;
	private EditText publishYear;
	private Button btnAdvanceSearch;
	private Spinner spinner;
	String selected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.advanced_search);
		// 初始化控件
		spinner = (Spinner) findViewById(R.id.spinner_ad_search);
		allSearch = (EditText) findViewById(R.id.edit_all_search);
		accurateSearch = (EditText) findViewById(R.id.edit_accurate_search);
		authorName = (EditText) findViewById(R.id.edit_author_name);
		publishMonth = (EditText) findViewById(R.id.edit_month);
		publishYear = (EditText) findViewById(R.id.edit_year);
		btnAdvanceSearch = (Button) findViewById(R.id.btn_advance_search);
		// 定义数组
		List<String> list = new ArrayList<String>();
		list.add("文章任何位置");
		list.add("位于文章标题");

		// 适配器
		@SuppressWarnings({ "unchecked", "rawtypes" })
		ArrayAdapter adapter = new ArrayAdapter(this,
				R.layout.spiner_item, R.id.sp_item_textview,
				list);

		spinner.setAdapter(adapter);
		// spinner.setPrompt("搜索位置");

		// 设置监听
		spinner.setOnItemSelectedListener(this);
		btnAdvanceSearch.setOnClickListener(this);
	}

	/*
	 * (non-Javadoc) spinner的监听方法
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemSelectedListener#onItemSelected(
	 * android .widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view,
			int position, long id) {
		selected = adapterView.getItemAtPosition(position).toString();
		System.out.println(selected);
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc) spinner的监听方法
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemSelectedListener#onNothingSelected
	 * (android .widget.AdapterView)
	 */
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		System.out.println("Nothing selected!");

	}

	/*
	 * 高级搜索按钮单击事件的监听 (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@SuppressLint("NewApi")
	@Override
	public void onClick(View arg0) {
		List<String> search_adv = new ArrayList<String>();
		search_adv.add("高级搜索");
		search_adv.add(allSearch.getText().toString());
		search_adv.add(accurateSearch.getText().toString());
		search_adv.add(selected);
		search_adv.add(authorName.getText().toString());
		search_adv.add(publishMonth.getText().toString());
		search_adv.add(publishYear.getText().toString());

		// search_adv.add(btnAdvanceSearch.getText().toString());

		String mes = search_adv.toString();
		CommThread advsearch = new CommThread(mes);
		Log.i("TAG", mes);
		advsearch.start();
		while (advsearch.isAlive()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (advsearch.publist.size() == 0) {
			Toast.makeText(Advanced_Search.this, "未搜索到论文", 1000)
					.show();
			return;
		}
		Gson gson = new Gson();
		/* te是需要序列化的对象 */
		String publist = gson.toJson(advsearch.publist);

		String result = "高级搜索，传值测试！";
		Intent intent = new Intent();
		intent.putExtra("data", publist);

		/*
		 * 调用setResult方法表示我将Intent对象返回给之前的那个Activity，
		 * 这样就可以在onActivityResult方法中得到Intent对象，
		 */
		setResult(1001, intent);
		// 结束当前这个Activity对象的生命
		finish();
		// Toast.makeText(getApplicationContext(), "高级搜索",
		// Toast.LENGTH_SHORT).show();
		finish();

	}
}
