package com.wuk.mytools

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


/**
 * 弹性布局
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * 透明度转换
         */
        tv_color_alpha.setOnClickListener{
            val intent = Intent(this, ColorAlphaActivity::class.java)
            startActivity(intent)
        }


        tv_recycler.setOnClickListener{
            val intent = Intent(this, CollectionActivity::class.java)
            startActivity(intent)
        }

        tv_bookshelf.setOnClickListener{
            val intent = Intent(this, BookshelfActivity::class.java)
            startActivity(intent)
        }

        tv_bookshelf_grid.setOnClickListener{
            val intent = Intent(this, BookshelfByGridActivity::class.java)
            startActivity(intent)
        }

        tv_draw.setOnClickListener{
            val intent = Intent(this, DrawActivity::class.java)
            startActivity(intent)
        }
        tv_slide.setOnClickListener{
            val intent = Intent(this, SlideCardActivity::class.java)
            startActivity(intent)
        }
        tv_drop.setOnClickListener{
            val intent = Intent(this, DragActivity::class.java)
            startActivity(intent)
        }
    }

//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//    }

    override fun getResources(): Resources? {
        //禁止app字体大小跟随系统字体大小调节
        val resources = super.getResources()
        if (resources != null && resources.configuration.fontScale != 1.0f) {
            val configuration = resources.configuration
            configuration.setToDefaults()
            resources.updateConfiguration(configuration, resources.displayMetrics)
        }
        return resources
    }
}