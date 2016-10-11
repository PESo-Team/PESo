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
 * �����ˣ����۳�
 * �޸��ˣ���ӳ��
 * ��Ҫ���ܣ��߼���������
 */
public class Advanced_Search extends Activity implements
		OnItemSelectedListener, OnClickListener {
	// �����б�ؼ�
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
		// ��ʼ���ؼ�
		spinner = (Spinner) findViewById(R.id.spinner_ad_search);
		allSearch = (EditText) findViewById(R.id.edit_all_search);
		accurateSearch = (EditText) findViewById(R.id.edit_accurate_search);
		authorName = (EditText) findViewById(R.id.edit_author_name);
		publishMonth = (EditText) findViewById(R.id.edit_month);
		publishYear = (EditText) findViewById(R.id.edit_year);
		btnAdvanceSearch = (Button) findViewById(R.id.btn_advance_search);
		// ��������
		List<String> list = new ArrayList<String>();
		list.add("�����κ�λ��");
		list.add("λ�����±���");

		// ������
		@SuppressWarnings({ "unchecked", "rawtypes" })
		ArrayAdapter adapter = new ArrayAdapter(this,
				R.layout.spiner_item, R.id.sp_item_textview,
				list);

		spinner.setAdapter(adapter);
		// spinner.setPrompt("����λ��");

		// ���ü���
		spinner.setOnItemSelectedListener(this);
		btnAdvanceSearch.setOnClickListener(this);
	}

	/*
	 * (non-Javadoc) spinner�ļ�������
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
	 * (non-Javadoc) spinner�ļ�������
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
	 * �߼�������ť�����¼��ļ��� (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@SuppressLint("NewApi")
	@Override
	public void onClick(View arg0) {
		List<String> search_adv = new ArrayList<String>();
		search_adv.add("�߼�����");
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
			Toast.makeText(Advanced_Search.this, "δ����������", 1000)
					.show();
			return;
		}
		Gson gson = new Gson();
		/* te����Ҫ���л��Ķ��� */
		String publist = gson.toJson(advsearch.publist);

		String result = "�߼���������ֵ���ԣ�";
		Intent intent = new Intent();
		intent.putExtra("data", publist);

		/*
		 * ����setResult������ʾ�ҽ�Intent���󷵻ظ�֮ǰ���Ǹ�Activity��
		 * �����Ϳ�����onActivityResult�����еõ�Intent����
		 */
		setResult(1001, intent);
		// ������ǰ���Activity���������
		finish();
		// Toast.makeText(getApplicationContext(), "�߼�����",
		// Toast.LENGTH_SHORT).show();
		finish();

	}
}
