package com.peso.Sliding;

import java.text.SimpleDateFormat;
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
 * 个人信息页面
 * 
 * @author Anne daxiong
 * 
 */
public class PersonalInformationActivity extends Activity
		implements OnDateSetListener {
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
	Calendar cal;

	private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker arg0, int arg1, int arg2,
				int arg3) {
			cal.set(Calendar.YEAR, arg1);
			cal.set(Calendar.MONTH, arg2);
			cal.set(Calendar.DAY_OF_MONTH, arg3);
			Date date = cal.getTime();
			String[] datea = date.toString().split(" ");
			birthday_text.setText(datea[1] + ". " + datea[2] + "  "
					+ datea[5]);
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personalinformation);

		initViews();
		initViewsEven();

		// 点击我的昵称
		sharedPreferences = getSharedPreferences("UserInfo",
				MODE_PRIVATE);
		name = sharedPreferences.getString("username", "Username");
		nickname.setText(name);
	}

	private void initViewsEven() {
		// TODO Auto-generated method stub
		logout_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				sharedPreferences = getSharedPreferences(
						"UserInfo", MODE_PRIVATE);
				Editor editor = sharedPreferences.edit();
				editor.remove("username");
				editor.remove("password");
				editor.commit();// 提交修改

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
		// 点击返回键
		P_return.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		birthday.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				cal = Calendar.getInstance(Locale.US);
				// 创建一个日历引用d，通过静态方法getInstance() 从指定时区
				// Locale.CHINA 获得一个日期实例
				// 创建一个Date实例
				cal.setTime(new Date());
				new DatePickerDialog(
						PersonalInformationActivity.this,
						listener,
						cal.get(Calendar.YEAR),
						cal.get(Calendar.MONTH),
						cal.get(Calendar.DAY_OF_MONTH))
								.show();
			}
		});
		signature_text.setOnClickListener(null);
	}

	private void initViews() {
		// TODO Auto-generated method stub
		mContext = this;

		P_return = (ImageView) findViewById(R.id.p_return);

		nickname = (TextView) findViewById(R.id.nickname);
		signature = (TextView) findViewById(R.id.signature);
		birthday = (LinearLayout) findViewById(R.id.birthday);
		birthday_text = (TextView) findViewById(R.id.birthday_text);
		interest = (TextView) findViewById(R.id.interest);
		sex = (TextView) findViewById(R.id.sex);
		email = (TextView) findViewById(R.id.email);
		school = (TextView) findViewById(R.id.school);
		major = (TextView) findViewById(R.id.major);
		signature_text = (TextView) findViewById(R.id.signature_text);

		interest_text = (TextView) findViewById(R.id.interest_text);
		sex_text = (TextView) findViewById(R.id.sex_text);
		email_text = (TextView) findViewById(R.id.email_text);
		school_text = (TextView) findViewById(R.id.school_text);
		major_text = (TextView) findViewById(R.id.major_text);

		logout_button = (Button) findViewById(R.id.logout_button);

	}

	// 从跳转的界面传入返回值
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

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub

	}
}
