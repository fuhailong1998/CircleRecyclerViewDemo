package com.fxkxb.circlerecyclerviewdemo

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.fxkxb.circlerecyclerviewdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    private var fruitList: List<Fruit> = ArrayList()
    private val mImgs = arrayOf(
        R.drawable.lo,
        R.drawable.temp_16_0, R.drawable.temp_16_5, R.drawable.temp_17_0, R.drawable.temp_17_5,
        R.drawable.temp_18_0, R.drawable.temp_18_5, R.drawable.temp_19_0, R.drawable.temp_19_5,
        R.drawable.temp_20_0, R.drawable.temp_20_5, R.drawable.temp_21_0, R.drawable.temp_21_5,
        R.drawable.temp_22_0, R.drawable.temp_22_5, R.drawable.temp_23_0, R.drawable.temp_23_5,
        R.drawable.temp_24_0, R.drawable.temp_24_5, R.drawable.temp_25_0, R.drawable.temp_25_5,
        R.drawable.temp_26_0, R.drawable.temp_26_5, R.drawable.temp_27_0, R.drawable.temp_27_5,
        R.drawable.temp_28_0, R.drawable.hi
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)

        val view = activityMainBinding.root
        setContentView(view)

        initFruits()
        val recyclerView = activityMainBinding.rview
//        val staggeredGridLayoutManager =
//            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
//        CircleScaleLayoutManager layoutManager = new CircleScaleLayoutManager(this);

//        recyclerView.layoutManager = staggeredGridLayoutManager

        val arcLayoutManager = ArcLayoutManager(this)
        recyclerView.layoutManager = arcLayoutManager
        val fruitAdapter = FruitAdapter(fruitList)
        recyclerView.adapter = fruitAdapter
    }

    private fun initFruits() {
        for (i in 0..26) {
            val apple = Fruit(mImgs[i])
            fruitList += apple

        }
    }
}