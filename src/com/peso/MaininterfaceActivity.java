package com.peso;

/*
 *������ˢ�µĴ���˼·�ܽ�󣬿�ʼд����
 * 
 */
import java.util.ArrayList;

import Thread.CommThread;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.peso.Sliding.MyCollectionActivity;
import com.peso.Sliding.MyDownloadingActivity;
import com.peso.Sliding.PersonalInformationActivity;
import com.peso.Sliding.SystemSettingActivity;
import com.peso.model.CommentActivity;
import com.peso.model.LoginActivity;
import com.peso.model.SearchActivity;
import com.peso.view.SlidingMenu;

import dbmodels.Publication;

public class MaininterfaceActivity extends Activity
		implements OnItemClickListener, OnScrollListener {
	private SlidingMenu mLeftMenu;// �໬�˵�
	// private Button mLeftMenu;//�໬�˵���ť
	private Button Search;
	private TextView Title; // �����¼��ť
	private TextView Login; // �����¼��ť
	private ImageView Login1; // �����¼��ť
	private TextView Logout; // ��¼ע����ť
	private LinearLayout Personalinformation; // ������Ϣ
	private LinearLayout Mycollection; // �ҵ��ղ�
	private LinearLayout Systemsetting; // ϵͳ����
	private LinearLayout Systemmessage; // ϵͳ��Ϣ
	private LinearLayout Mydownload; // �ҵ�����
	private LinearLayout ChangeItem; // �л�����

	private TextView personal_text;
	private TextView recommend_text;
	private TextView index_text;
	private TextView download_text;
	private TextView favs_text;
	private TextView notepad_text;
	private TextView setting_text;

	private ItemView mItemView; // ������recommend_text
	private ImageView listview_image;
	private ListView mMainListView;
	// Bundle������Я�����ݣ���������Map�����ڴ��key-value��ֵ����ʽ��ֵ
	SharedPreferences sharedPreferences;
	private int rec_load_position = 1;
	boolean isLoading;// ���ڼ��أ�
	boolean runFirst = true;
	View footer;// �ײ����֣�
	static int cycle_index;
	private CommThread commthread;
	private ArrayList<Publication> pub = new ArrayList<Publication>();
	private Typeface typeface;
	String username;

	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_maininterface);

		initViews();
		initViewsEven();

		LayoutInflater inflater = getLayoutInflater();
		footer = inflater.inflate(R.layout.footer_loading, null);
		footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
		mMainListView.addFooterView(footer);
		SetTypeface();
		LoadingItemBean();
	}

	// Activity�Ӻ�̨���»ص�ǰ̨ʱ������
	@Override
	protected void onStart() {
		super.onRestart();
		/* sharedpreference */
		sharedPreferences = getSharedPreferences("UserInfo",
				MODE_PRIVATE);
		/*
		 * Editor editor = sharedPreferences .edit();
		 * editor.putString("username", "");
		 * editor.putString("password", "");//����Ϣ��ʾδ��½ editor.commit();
		 */
		/* //sharedpreference */
		username = sharedPreferences.getString("username", "");
		Update_Login();
	}

	private void SetTypeface() {
		// TODO Auto-generated method stub
		Title.setTypeface(typeface);
		Login.setTypeface(typeface);

		personal_text.setTypeface(typeface);
		recommend_text.setTypeface(typeface);
		index_text.setTypeface(typeface);
		download_text.setTypeface(typeface);
		favs_text.setTypeface(typeface);
		notepad_text.setTypeface(typeface);
		setting_text.setTypeface(typeface);
	}

	private void Update_Login() {
		// TODO Auto-generated method stub
		if (username == "") {
			Login.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(
							MaininterfaceActivity.this,
							LoginActivity.class);
					startActivity(intent);
				}
			});

			Login1.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(
							MaininterfaceActivity.this,
							LoginActivity.class);
					startActivity(intent);
				}
			});

		} else {
			sharedPreferences = getSharedPreferences("UserInfo",
					MODE_PRIVATE);
			Login.setText(username);
			Login.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(
							MaininterfaceActivity.this,
							PersonalInformationActivity.class);
					startActivity(intent);
				}
			});

			Login1.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(
							MaininterfaceActivity.this,
							PersonalInformationActivity.class);
					startActivity(intent);
				}
			});
		}
	}

	private void initViews() {
		// TODO Auto-generated method stub
		/*--------------��id--------------*/
		// �󶨿ؼ�
		mMainListView = (ListView) findViewById(R.id.list_view_main);// list_view_image
		listview_image = (ImageView) findViewById(R.id.list_view_image);
		Login = (TextView) findViewById(R.id.user_text);
		Login1 = (ImageView) findViewById(R.id.user_image);
		Title = (TextView) findViewById(R.id.top_textView);
		// Logout=(TextView) findViewById(R.id.Logout);
		Personalinformation = (LinearLayout) findViewById(
				R.id.personalinformation);
		Mycollection = (LinearLayout) findViewById(R.id.mycollection);
		Systemsetting = (LinearLayout) findViewById(R.id.systemsetting);
		Mydownload = (LinearLayout) findViewById(R.id.mydownload);
		mLeftMenu = (SlidingMenu) findViewById(R.id.id_menu);
		Search = (Button) findViewById(R.id.search_button);
		// �໬����
		personal_text = (TextView) findViewById(R.id.personal_text);
		recommend_text = (TextView) findViewById(R.id.recommend_text);
		index_text = (TextView) findViewById(R.id.index_text);
		download_text = (TextView) findViewById(R.id.download_text);
		favs_text = (TextView) findViewById(R.id.favs_text);
		notepad_text = (TextView) findViewById(R.id.notepad_text);
		setting_text = (TextView) findViewById(R.id.setting_text);
		typeface = Typeface.createFromAsset(getAssets(),
				"fonts/GothamRounded-Medium.otf");
	}

	private void initViewsEven() {
		// TODO Auto-generated method stub
		/*--------------ע�����¼�--------------*/
		mMainListView.setOnItemClickListener(this);
		mMainListView.setOnScrollListener(this);

		Personalinformation.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(
						MaininterfaceActivity.this,
						PersonalInformationActivity.class);
				startActivity(intent);
			}
		});

		Mycollection.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(
						MaininterfaceActivity.this,
						MyCollectionActivity.class);
				startActivity(intent);
			}
		});

		Systemsetting.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(
						MaininterfaceActivity.this,
						SystemSettingActivity.class);
				startActivity(intent);
			}
		});

		Mydownload.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(
						MaininterfaceActivity.this,
						MyDownloadingActivity.class);
				startActivity(intent);
			}
		});

		Search.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(
						MaininterfaceActivity.this,
						SearchActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
			}
		});

		mLeftMenu.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(
						MaininterfaceActivity.this,
						SlidingmenuActivity.class);
				startActivity(intent);
			}
		});

		listview_image.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				listview_image.setImageResource(
						R.drawable.peso_logo);
				LoadingItemBean();
			}
		});
	}

	public void toggleMenu(View view) {
		mLeftMenu.toggle();
	}

	/*--------------------------item�����¼�---------------------------------------*/
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(MaininterfaceActivity.this,
				CommentActivity.class);
		intent.putExtra("pub", pub.get(arg2));
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in,
				R.anim.push_left_out);
	}

	/*---------------------listview�����¼�-----------------------*/
	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub
		switch (arg1) {
			case SCROLL_STATE_FLING:
				break;
			case SCROLL_STATE_IDLE:
				break;
			case SCROLL_STATE_TOUCH_SCROLL:
				if (arg0.getLastVisiblePosition() == (arg0
						.getCount() - 1)) {
					/*---------------�ڴ˴����������listview�б�����------------*/
					/*
					 * dataList.add(new ItemBean(
					 * R.drawable.ic_launcher, "�¼ӵı���",
					 * "�¼ӵ�����", "�����ӵ�����"));
					 * mAdapter.notifyDataSetChanged();
					 */
					LoadingItemBean();
				}
				break;
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(0, R.anim.base_slide_right_out);
	}

	private void LoadingItemBean() {
		if (!isLoading) {
			isLoading = true;
			// ����Ч��
			Animation animation = AnimationUtils.loadAnimation(
					MaininterfaceActivity.this,
					R.anim.listview_image_change);
			listview_image.startAnimation(animation);

			footer.findViewById(R.id.load_layout)
					.setVisibility(View.VISIBLE);
			System.out.println("footer���غ���");
			Log.i("----", "footer���غ�����");
			Log.i("tag", "start");
			/* ��ȡnum�����µ����� */
			sharedPreferences = getSharedPreferences("UserInfo",
					MODE_PRIVATE);
			final String mes = sharedPreferences
					.getString("username", ""); // getIntent().getStringExtra("username")�������usernameΪ
									// sp��key
									// �ڶ�������Ϊ
									// Ĭ��ֵ������Ĭ�ϻ�ȡ��¼������user��Ϣ��string
									// //
									// getIntent().getStringExtra("username")
			// String
			// mes=getIntent().getStringExtra("username");
			commthread = new CommThread("recomendations" + mes
					+ "position" + rec_load_position);// ͨ��
										// ����user��Ϣȥ��ѯ�Ƽ�����Ƽ���¼
			commthread.start();// �����Ƽ��߳�
			rec_load_position += 2;
			// �Ѵ�������
			// ����������Ҫʱ�䣡��������
			// ���ؼ���Ȧ
			cycle_index = 0;
			final Handler handler = new Handler();
			handler.postDelayed(new Thread() {
				@Override
				public void run() {
					cycle_index++;
					// ��ȡ��������
					// �ӳٳ���8��ֱ�ӶϿ�����
					// ÿ��1��ִ��һ�θ��߳�
					if (cycle_index < 8) {
						handler.postDelayed(this, 1000);
					} else {
						isLoading = false;
						Toast.makeText(MaininterfaceActivity.this,
								"Network Connection Timeout",
								Toast.LENGTH_SHORT)
								.show();
						listview_image.clearAnimation();

						listview_image.setImageResource(
								R.drawable.peso_logo_break);
						footer.findViewById(
								R.id.load_layout)
								.setVisibility(View.GONE);
						handler.removeCallbacks(this);
					}
					if (commthread.publist.size() > 1) {
						Log.i("������������", ""
								+ commthread.publist
										.size());
						pub.addAll(commthread.publist);// ���������б������
						if (runFirst) {
							mItemView = new ItemView(
									MaininterfaceActivity.this,
									pub);
							listview_image.clearAnimation();
							// �������Ч���Ժ� ִ�б��Ч��
							Animation animation = AnimationUtils
									.loadAnimation(MaininterfaceActivity.this,
											R.anim.listview_image_change2);
							listview_image.startAnimation(
									animation);
							animation.setAnimationListener(
									new AnimationListener() {
								public void onAnimationEnd(
										Animation animation) {
									listview_image.setVisibility(
											View.GONE);
									mMainListView.setVisibility(
											View.VISIBLE);
									mMainListView.setAdapter(
											mItemView);
									runFirst = false;
								}

								@Override
								public void onAnimationStart(
										Animation animation) {
									// TODO Auto-generated method stub	
								}

								@Override
								public void onAnimationRepeat(
										Animation animation) {
									// TODO Auto-generated method stub
								}

							});
						} else {
							mItemView.notifyDataSetChanged();
						}
						Log.i("loading", "������������");
						isLoading = false;
						footer.findViewById(
								R.id.load_layout)
								.setVisibility(View.GONE);
						handler.removeCallbacks(this);
					}
				}
			}, 1000);
		}
	}
}
