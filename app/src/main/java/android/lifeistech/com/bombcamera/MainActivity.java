package android.lifeistech.com.bombcamera;

import android.content.res.Resources;
import android.graphics.drawable.Animatable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MainActivity extends AppCompatActivity {

    //定義
    private LinearLayout bombbtn;
    private LinearLayout menu;
    private ImageView imageView;
    private TextView text_status;
    private SoundPool soundPool;
    private Apngdraw apngdraw;

    //爆破タイプを定義
    int bombtype = 1;

    //ロードするAPNGを配列で定義
    private String[] bomb = {
            "bomb1.apng",
            "bomb2.apng",
            "bomb3.apng"
    };

    //ロードするSEを配列で定義
    private int[] bombse = {
            R.raw.bomb1,
            R.raw.bomb2,
            R.raw.bomb3
    };
    //SoundPoolのデータを格納する配列を定義
    private int[] bombseID = new int[bombse.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this); //初期化
        setContentView(R.layout.activity_main);

        //タイトルを設定
        setTitle("BombCamera");

        //関連付け
        imageView = (ImageView)findViewById(R.id.imageView);
        text_status = (TextView)findViewById(R.id.text_status);
        bombbtn = (LinearLayout)findViewById(R.id.bombbtn);
        menu = (LinearLayout)findViewById(R.id.menu);

        //Apngdrawのインスタンスを生成
        apngdraw = new Apngdraw();
        //デフォルトでApngを読み込む
        apngdraw.startLoad(getApplicationContext(), imageView, text_status, bomb[0], null, null);

        //SoundPoolのインスタンスを生成
        soundPool = new SoundPool(bombseID.length,AudioManager.STREAM_MUSIC, 0);
        //読み込む音声ファイルの数だけ読み込んで bombseID[] に格納
        for (int i = 0; i < bombse.length; i++ ){
            bombseID[i] = soundPool.load(getApplicationContext(), bombse[i], 0);
        }

        //デフォルトで爆破を止めて隠す
        stopapng();

        //爆破ボタンを表示してメニューを非表示に
        findViewById(R.id.bombbtn).setVisibility(View.VISIBLE);
        findViewById(R.id.menu).setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //メモリ解放
        soundPool.release();
        apngdraw.release();
    }


    //爆破BBの再生処理
    public void startbomb(View v){
        //APNGを表示
        imageView.setVisibility(View.VISIBLE);
        //soundPoolのbombtype目を再生する
        soundPool.play(bombseID[bombtype-1],1.0F,1.0F,0,0,1.0F);
        //APNGを再生
        apngdraw.startAnimation();
    }

    //爆破BBの停止処理
    public void stopbomb(View v){
        stopapng();
    }

    //APNGのロード処理
    public void loadapng(){
        //タイトルを設定
        setTitle("BombCamera　-　爆破タイプ" + bombtype);
        //APNGを読み込む
        apngdraw.startLoad(getApplicationContext(), imageView, text_status, bomb[bombtype-1], null, null);
        //爆破を止めて隠す
        stopapng();
    }

    //APNGの停止処理
    public void stopapng(){
        //APNGを止める
        apngdraw.stopAnimation();
        //APNGを隠す
        imageView.setVisibility(View.GONE);
    }

    //メニューの表示・非表示
    public void showmenu(View v){
        //メニューを表示して爆破ボタンを隠す
        findViewById(R.id.menu).setVisibility(View.VISIBLE);
        findViewById(R.id.bombbtn).setVisibility(View.GONE);
    }

    public void hidemenu(View v){
        //メニューを隠して爆破ボタンを表示
        findViewById(R.id.bombbtn).setVisibility(View.VISIBLE);
        findViewById(R.id.menu).setVisibility(View.GONE);
    }

    //爆破タイプの切り替え
    public void type1(View v){
        bombtype = 1;
        //APNGをロード
        loadapng();
    }

    public void type2(View v){
        bombtype = 2;
        //APNGをロード
        loadapng();
    }

    public void type3(View v){
        bombtype = 3;
        //APNGをロード
        loadapng();
    }
}
