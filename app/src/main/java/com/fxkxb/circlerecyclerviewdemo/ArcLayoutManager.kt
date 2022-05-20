package com.fxkxb.circlerecyclerviewdemo

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup.LayoutParams.*
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.*
class FillItemOrientation(){
    companion object  {
        val LEFT_START = 5
        val RIGHT_START = 1
    }
}
class ArcLayoutManager (context: Context): RecyclerView.LayoutManager() {

    private var mCurrAngle = -90.0
    private var mInitialAngle = -90.0
    private var mVisibleItemCount = 0
    private val centerOfCircleX = 720
    private val centerOfCircleY = 1520
    private val radius = 500
    private val orientation = 1
    private val DEFAULT_RATIO = 100
    private val DEFAULT_SCROLL_DAMP = 1
    private val SPEECH_MILLIS_INCH = 1
    private var shouldCenter = true


//    override fun canScrollVertically() = true
//    override fun scrollVerticallyBy(
//        dy: Int,
//        recycler: RecyclerView.Recycler,
//        state: RecyclerView.State
//    ): Int {
//        // 根据滑动距离 dy 计算滑动角度
//        // 将竖直方向的滑动距离，近似看成是在圆上的弧长
//        // 弧度 = 角度 * π / 180
//        val theta = ((-dy * 180) * orientation / (Math.PI * radius * DEFAULT_RATIO)) * DEFAULT_SCROLL_DAMP
//        // 根据滑动角度修正开始摆放的角度
//        mCurrAngle = (mCurrAngle + theta) % (Math.PI * 2)
//        offsetChildrenVertical(-dy)
//        fill(recycler)
//        return dy
//    }
//
//    // 当所有子View计算并摆放完毕会调用该函数
//    override fun onLayoutCompleted(state: RecyclerView.State) {
//        super.onLayoutCompleted(state)
//        stabilize()
//    }
//
//// 修正子View位置
//    private fun stabilize() {
//        if (childCount < mVisibleItemCount / 2 || isSmoothScrolling) return
//
//        var minDistance = Int.MAX_VALUE
//        var nearestChildIndex = 0
//        for (i in 0 until childCount) {
//            val child = getChildAt(i) ?: continue
//            if (orientation == FillItemOrientation.LEFT_START && getDecoratedRight(child) > centerOfCircleX)
//                continue
//            if (orientation == FillItemOrientation.RIGHT_START && getDecoratedLeft(child) < centerOfCircleX)
//                continue
//
//            val y = (getDecoratedTop(child) + getDecoratedBottom(child)) / 2
//            if (abs(y - centerOfCircleY) < abs(minDistance)) {
//                nearestChildIndex = i
//                minDistance = (y - centerOfCircleY).toInt()
//            }
//        }
//        if (minDistance in 0..10) return
//        getChildAt(nearestChildIndex)?.let {
//            startSmoothScroll(
//                getPosition(it),
//                true
//            )
//        }
//    }
//
//    private val scroller by lazy {
//        object : LinearSmoothScroller(context) {
//
//            override fun calculateDtToFit(
//                viewStart: Int,
//                viewEnd: Int,
//                boxStart: Int,
//                boxEnd: Int,
//                snapPreference: Int
//            ): Int {
//                if (shouldCenter) {
//                    val viewY = (viewStart + viewEnd) / 2
//                    var modulus = 1
//                    val distance: Int
//                    if (viewY > centerOfCircleY) {
//                        modulus = -1
//                        distance = (viewY - centerOfCircleY).toInt()
//                    } else {
//                        distance = (centerOfCircleY - viewY).toInt()
//                    }
//                    val alpha = asin(distance.toDouble() / radius)
//                    return (PI * radius * DEFAULT_RATIO * alpha / (180 * DEFAULT_SCROLL_DAMP) * modulus).roundToInt()
//                } else {
//                    return super.calculateDtToFit(
//                        viewStart,
//                        viewEnd,
//                        boxStart,
//                        boxEnd,
//                        snapPreference
//                    )
//                }
//            }
//
//            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float =
//                (SPEECH_MILLIS_INCH / displayMetrics.densityDpi).toFloat()
//        }
//    }
//
////     滚动
//    private fun startSmoothScroll(
//        targetPosition: Int,
//        shouldCenter: Boolean
//    ) {
//        this.shouldCenter = shouldCenter
//        scroller.targetPosition = targetPosition
//        startSmoothScroll(scroller)
//    }
//



    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
        if (recycler != null) {
            fill(recycler)
        }
    }
    // mCurrAngle: 当前初始摆放角度
// mInitialAngle：初始角度
    private fun fill(recycler: RecyclerView.Recycler) {

        mVisibleItemCount = itemCount
        if (itemCount == 0) {
            removeAndRecycleAllViews(recycler)
            return
        }

        detachAndScrapAttachedViews(recycler)

        val angleStep = Math.PI * 2 / (mVisibleItemCount)

        if (mCurrAngle == 0.0) {
            mCurrAngle = mInitialAngle
        }

        var angle: Double = mCurrAngle
        val count = itemCount


        for (i in 0 until count) {
            val child = recycler.getViewForPosition(i)
            measureChildWithMargins(child, 0, 0)
            addView(child)

            //测量的子View的宽，高
            val cWidth: Int = getDecoratedMeasuredWidth(child)
            val cHeight: Int = getDecoratedMeasuredHeight(child)

            val x1 = (centerOfCircleX - radius * cos(angle)).toInt()
            val y1 = (centerOfCircleY + radius * sin(angle)).toInt()

            //设置子view的位置
            var left = x1 - cWidth / 2
            val top = y1 - cHeight / 2
            var right = x1 + cWidth / 2
            val bottom = y1 + cHeight / 2

            layoutDecoratedWithMargins(
                child,
                left,
                top,
                right,
                bottom
            )
//            scaleChild(child)

            angle += angleStep * orientation
        }

        recycler.scrapList.toList().forEach {
            recycler.recycleView(it.itemView)
        }
    }
//    private fun scaleChild(child: View) {
//        val y = (child.top + child.bottom) / 2
//        val scale = if (abs( y - centerOfCircleY) > child.measuredHeight / 2) {
//            child.translationX = 0f
//            1f
//        } else {
//            child.translationX = -child.measuredWidth * 0.2f
//            1.2f
//        }
//        child.pivotX = 0f
//        child.pivotY = child.height / 2f
//        child.scaleX = scale
//        child.scaleY = scale
//    }



}