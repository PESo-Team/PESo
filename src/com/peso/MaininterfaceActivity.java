package com.peso;

/*
 *将上拉刷新的代码思路总结后，开始写代码
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
	private SlidingMenu mLeftMenu;// 侧滑菜单
	// private Button mLeftMenu;//侧滑菜单按钮
	private Button Search;
	private TextView Title; // 点击登录按钮
	private TextView Login; // 点击登录按钮
	private ImageView Login1; // 点击登录按钮
	private TextView Logout; // 登录注销按钮
	private LinearLayout Personalinformation; // 个人信息
	private LinearLayout Mycollection; // 我的收藏
	private LinearLayout Systemsetting; // 系统设置
	private LinearLayout Systemmessage; // 系统消息
	private LinearLayout Mydownload; // 我的下载
	private LinearLayout ChangeItem; // 切换背景

	private TextView personal_text;
	private TextView recommend_text;
	private TextView index_text;
	private TextView download_text;
	private TextView favs_text;
	private TextView notepad_text;
	private TextView setting_text;

	private ItemView mItemView; // 适配器recommend_text
	private ImageView listview_image;
	private ListView mMainListView;
	// Bundle类用作携带数据，它类似于Map，用于存放key-value名值对形式的值
	SharedPreferences sharedPreferences;
	private int rec_load_position = 1;
	boolean isLoading;// 正在加载；
	boolean runFirst = true;
	View footer;// 底部布局；
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

	// Activity从后台重新回到前台时被调用
	@Override
	protected void onStart() {
		super.onRestart();
		/* sharedpreference */
		sharedPreferences = getSharedPreferences("UserInfo",
				MODE_PRIVATE);
		/*
		 * Editor editor = sharedPreferences .edit();
		 * editor.putString("username", "");
		 * editor.putString("password", "");//无信息表示未登陆 editor.commit();
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
		/*--------------绑定id--------------*/
		// 绑定控件
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
		// 侧滑文字
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
		/*--------------注册点击事件--------------*/
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

	/*--------------------------item单击事件---------------------------------------*/
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

	/*---------------------listview滚动事件-----------------------*/
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
					/*---------------在此处添加新增的listview列表内容------------*/
					/*
					 * dataList.add(new ItemBean(
					 * R.drawable.ic_launcher, "新加的标题",
					 * "新加的作者", "新增加的正文"));
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
			// 跳动效果
			Animation animation = AnimationUtils.loadAnimation(
					MaininterfaceActivity.this,
					R.anim.listview_image_change);
			listview_image.startAnimation(animation);

			footer.findViewById(R.id.load_layout)
					.setVisibility(View.VISIBLE);
			System.out.println("footer加载好了");
			Log.i("----", "footer加载好了了");
			Log.i("tag", "start");
			/* 获取num个最新的论文 */
			sharedPreferences = getSharedPreferences("UserInfo",
					MODE_PRIVATE);
			final String mes = sharedPreferences
					.getString("username", ""); // getIntent().getStringExtra("username")括弧里边username为
									// sp的key
									// 第二个参数为
									// 默认值，这里默认获取登录传来的user信息的string
									// //
									// getIntent().getStringExtra("username")
			// String
			// mes=getIntent().getStringExtra("username");
			commthread = new CommThread("recomendations" + mes
					+ "position" + rec_load_position);// 通过
										// 构造user信息去查询推荐表的推荐记录
			commthread.start();// 启动推荐线程
			rec_load_position += 2;
			// 已传来数据
			// 接受数据需要时间！！！！！
			// 隐藏加载圈
			cycle_index = 0;
			final Handler handler = new Handler();
			handler.postDelayed(new Thread() {
				@Override
				public void run() {
					cycle_index++;
					// 获取更多数据
					// 延迟超过8秒直接断开连接
					// 每隔1秒执行一次该线程
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
						Log.i("传来论文数量", ""
								+ commthread.publist
										.size());
						pub.addAll(commthread.publist);// 在这里往列表里面加
						if (runFirst) {
							mItemView = new ItemView(
									MaininterfaceActivity.this,
									pub);
							listview_image.clearAnimation();
							// 清除动画效果以后 执行变大效果
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
						Log.i("loading", "正在上拉加载");
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
