package com.peso.Sliding;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peso.MaininterfaceActivity;
import com.peso.R;

/**
 * ������Ϣҳ��
 * 
 * @author Anne daxiong
 * 
 */
public class PersonalInformationActivity extends Activity implements
		OnDateSetListener {
	protected static final OnDateSetListener DatePickerListener = null;
	private ImageView P_return;
	private TextView nickname;
	String name;
	private TextView signature;
	private LinearLayout birthday;
	private TextView birthday_text;
	private TextView interest;
	private TextView sex;
	private TextView email;
	private TextView school;
	private TextView major;
	private TextView signature_text;
	private TextView interest_text;
	private TextView sex_text;
	private TextView email_text;
	private TextView school_text;
	private TextView major_text;
	private Context mContext;
	private Date myDate;
	private DatePickerDialog dlg;
	private Button logout_button;
	SharedPreferences sharedPreferences;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personalinformation);
		mContext = this;
		
		P_return = (ImageView) findViewById(R.id.p_return);
		
		nickname = (TextView) findViewById(R.id.nickname);
		signature = (TextView) findViewById(R.id.signature);
		birthday = (LinearLayout) findViewById(R.id.birthday);
		birthday_text= (TextView) findViewById(R.id.birthday_text);
		interest= (TextView) findViewById(R.id.interest);
		sex=(TextView) findViewById(R.id.sex);
		email=(TextView) findViewById(R.id.email);
		school=(TextView) findViewById(R.id.school);
		major=(TextView) findViewById(R.id.major);
		signature_text = (TextView) findViewById(R.id.signature_text);

		interest_text= (TextView) findViewById(R.id.interest_text);
		sex_text=(TextView) findViewById(R.id.sex_text);
		email_text=(TextView) findViewById(R.id.email_text);
		school_text=(TextView) findViewById(R.id.school_text);
		major_text=(TextView) findViewById(R.id.major_text);
		
		logout_button= (Button) findViewById(R.id.logout_button);
		// ����ҵ��ǳ�
		sharedPreferences = getSharedPreferences("UserInfo",
				MODE_PRIVATE);
		name = sharedPreferences.getString("username", "Username");
		nickname.setText(name);
		
		logout_button.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				sharedPreferences = getSharedPreferences(
						"UserInfo", MODE_PRIVATE);
				Editor editor = sharedPreferences
						.edit();
				editor.remove("username");
				editor.remove("password");
				editor.commit();// �ύ�޸�
				
				Intent intent = new Intent(mContext,
						MaininterfaceActivity.class);
				startActivity(intent);		
			}
		});

		nickname.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext,
						ChangenicknameActivity.class);
				startActivityForResult(intent, 1);
			}
		});
		// ������ؼ�
		P_return.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		// ����ҵĸ�ǩ
		signature_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(
						mContext,
						PersonalitysignatureActivity.class);
				startActivity(intent);
			}
		});
		birthday.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Calendar d = Calendar.getInstance(Locale.CHINA);
				// ����һ����������d��ͨ����̬����getInstance() ��ָ��ʱ��
				// Locale.CHINA ���һ������ʵ��
				// ����һ��Dateʵ��
				d.setTime(new Date());
				// ����������ʱ�䣬��һ���½�Dateʵ��myDate����
				int year = d.get(Calendar.YEAR);
				int month = d.get(Calendar.MONTH);
				int day = d.get(Calendar.DAY_OF_MONTH);
				// ��������е� year month day
				DatePickerDialog dlg = new DatePickerDialog(
						PersonalInformationActivity.this,
						DatePickerListener, year,
						month, day);
				// �½�һ��DatePickerDialog ���췽����
				// ���豸�����ģ�OnDateSetListenerʱ�����ü�������Ĭ���꣬Ĭ���£�Ĭ���գ�
				dlg.show();
				// ��DatePickerDialog��ʾ����
			}
		});
	}

	/*
	 * public void onClick(View v){ switch (v.getId()){ case R.id.p_return:
	 * finish(); break; case R.id.nickname: Intent intent=new
	 * Intent(this,ChangenicknameActivity.class);
	 * startActivityForResult(intent,1);
	 */
	// case R.id.signature :
	// Intent intent1=new Intent(this,PersonalitysignatureActivity.class);
	// startActivityForResult(intent1,3);
	// }
	// }
	// ����ת�Ľ��洫�뷵��ֵ
	protected void onActivityResult(int requestCode, int resultCode,
			Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && resultCode == 2) {
			String content = data.getStringExtra("data");
			nickname.setText(content);
		}
		if (requestCode == 3 && resultCode == 4) {
			String content1 = data.getStringExtra("data");
			signature_text.setText(content1);
		}
	}

	//
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// DatePickerDialog �а�ťSet����ʱ�Զ�����

		// ͨ��id���TextView����
		birthday_text.setText(monthOfYear+ ". "
				+ Integer.toString(dayOfMonth) + " "
				+ Integer.toString(year));
		// ����text
	}
}
