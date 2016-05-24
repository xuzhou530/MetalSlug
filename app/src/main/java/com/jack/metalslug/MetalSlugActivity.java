package com.jack.metalslug;

import android.app.Activity;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class MetalSlugActivity extends Activity {
    // 定义主布局内的容器：FrameLayout
    public static FrameLayout frameLayout;
    // 主布局的布局参数
    public static FrameLayout.LayoutParams layoutParams;
    // 定义资源管理的核心类
    public static Resources resources;
    public static MetalSlugActivity metalSlugActivity;
    // 定义成员变量记录游戏窗口的宽度、高度
    public static int windowWidth;
    public static int windowHeight;
    // 游戏窗口的主游戏界面
    public static GameView gameView;
    // 播放背景音乐的MediaPlayer
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        metalSlugActivity = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics metric = new DisplayMetrics();
        // 获取屏幕高度、宽度
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        windowHeight = metric.heightPixels;  // 屏幕高度
        windowWidth = metric.widthPixels;  // 屏幕宽度
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        resources = getResources();
        // 加载main.xml界面设计文件
        setContentView(R.layout.main);
        // 获取main.xml界面设计文件中ID为mainLayout的组件
        frameLayout = (FrameLayout) findViewById(R.id.mainLayout);
        // 创建GameView组件
//        gameView = new GameView(this.getApplicationContext(), GameView.STAGE_INIT);
        gameView = new GameView(this, GameView.STAGE_INIT);
        layoutParams = new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        frameLayout.addView(gameView, layoutParams);
        // 播放背景音乐
        mediaPlayer = MediaPlayer.create(this, R.raw.background);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        // 当游戏暂停时，暂停播放背景音乐
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // 当游戏恢复时，如果没有播放背景音乐，开始播放背景音乐
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }
}
