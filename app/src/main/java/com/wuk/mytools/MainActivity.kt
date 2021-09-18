package com.wuk.mytools

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }


}