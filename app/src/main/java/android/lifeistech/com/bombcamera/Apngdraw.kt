package android.lifeistech.com.bombcamera

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.lifeistech.com.bombcamera.R.layout.activity_main
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.linecorp.apng.ApngDrawable
import kotlinx.android.synthetic.main.activity_main.imageView
import kotlinx.android.synthetic.main.activity_main.text_status


class Apngdraw : AppCompatActivity(){

    private var drawable: ApngDrawable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @SuppressLint("SetTextI18n")
    public fun startLoad(context: Context, imageView: ImageView, text_status: TextView, name: String, width: Int? = null, height: Int? = null) {
        //TOASTを設定
        Toast.makeText(context, "読み込み中…", Toast.LENGTH_SHORT).show()
        //drawable?.recycle()
        drawable = null
        val asset = context.getAssets()
        val res = context.getResources()
        imageView.setImageDrawable(null)
        val isApng = asset.open(name).buffered().use {
            ApngDrawable.isApng(it)
        }
        //text_status.text = "isApng: $isApng"
        if (isApng) {
            drawable = ApngDrawable.decode(asset, name, width, height) //APNGをデコードして読み込む
            drawable?.setTargetDensity(res.displayMetrics)
            imageView.setImageDrawable(drawable)
            imageView.scaleType = ImageView.ScaleType.CENTER
        }
        Log.d("apng", "size: ${drawable?.allocationByteCount} byte")
        //TOASTを設定
        Toast.makeText(context, "読み込みが完了しました", Toast.LENGTH_SHORT).show()
    }

    public fun startAnimation() {
        drawable?.start()
    }

    public fun stopAnimation() {
        drawable?.stop()
    }

    public fun release() {
        drawable?.recycle()
    }

    public fun runGc() {
        System.gc()
    }
}