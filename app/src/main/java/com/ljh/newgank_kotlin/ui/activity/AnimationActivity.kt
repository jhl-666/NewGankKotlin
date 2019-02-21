package com.ljh.newgank_kotlin.ui.activity

import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.LinearInterpolator
import com.ljh.newgank_kotlin.R
import com.ljhdemo.newgank.common.utils.UIUtil
import kotlinx.android.synthetic.main.activity_animation.*



class AnimationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)

        vpaBtn.setOnClickListener {
            //旋转
            vpaBtn.animate()
                .translationYBy(vpaBtn.height.toFloat())//**By(),类似偏移量，每次都位移这个大小。不带的话是总移动量
                .setDuration(500L)
                .setInterpolator(LinearInterpolator())
                .start()
            //组合
            /* vpaBtn.animate()
                 .alpha(0.5f)
                 .rotation(360f)
                 .scaleX(1.5f)
                 .scaleY(1.5f)
                 .translationX(50f)
                 .translationY(50f)
                 .withEndAction {
                     it.animate().alpha(2f)
                         .rotation(360f)
                         .scaleX(0.5f)
                         .scaleY(0.5f).duration = 2000
                 }.duration = 2000*/
        }

        jitterBtn.setOnClickListener {

            /*左右抖动和上下抖动。左右抖动通常用于表单验证失败的时候，上下抖动通常用于吸引用于用户去点击。动画主要使用了Keyframe和PropertyValuesHolder。
            Keyframe是一个时间/值对，用于定义在某个时刻动画的状态。比如Keyframe.ofInt(.5f, Color.RED)定义了当动画进行了50%的时候，颜色的值应该是Color.RED。
            PropertyValuesHolder保存了view的属性的信息以及在动画进行过程中该属性的值。通过PropertyValuesHolder.ofKeyframe方法来构建PropertyValuesHolder的实例，改方法接收一个属性名以及多个Keyframe对象作为参数。当你想通过动画改变多个属性的时候PropertyValuesHolder就非常有用。
            */

            //抖动吸引用户注意（这个效果可以）
            jitter(it)

            //左右抖动
            val nopeAnimator = nope(vpaBtn)
            nopeAnimator.repeatCount = ValueAnimator.INFINITE
            nopeAnimator.start()
        }
    }

    private fun jitter(view: View){
        jitter(view, 1f)
    }

    private fun jitter(view: View, shakeFactor: Float){

        var pvhScaleX = PropertyValuesHolder.ofKeyframe(
            View.SCALE_X,
            Keyframe.ofFloat(0f, 1f),
            Keyframe.ofFloat(.1f, .9f),
            Keyframe.ofFloat(.2f, .9f),
            Keyframe.ofFloat(.3f, 1.1f),
            Keyframe.ofFloat(.4f, 1.1f),
            Keyframe.ofFloat(.5f, 1.1f),
            Keyframe.ofFloat(.6f, 1.1f),
            Keyframe.ofFloat(.7f, 1.1f),
            Keyframe.ofFloat(.8f, 1.1f),
            Keyframe.ofFloat(.9f, 1.1f),
            Keyframe.ofFloat(1f, 1f)
        )

        var pvhScaleY = PropertyValuesHolder.ofKeyframe(
            View.SCALE_Y,
            Keyframe.ofFloat(0f, 1f),
            Keyframe.ofFloat(.1f, .9f),
            Keyframe.ofFloat(.2f, .9f),
            Keyframe.ofFloat(.3f, 1.1f),
            Keyframe.ofFloat(.4f, 1.1f),
            Keyframe.ofFloat(.5f, 1.1f),
            Keyframe.ofFloat(.6f, 1.1f),
            Keyframe.ofFloat(.7f, 1.1f),
            Keyframe.ofFloat(.8f, 1.1f),
            Keyframe.ofFloat(.9f, 1.1f),
            Keyframe.ofFloat(1f, 1f)
        )

        var pvhRotate = PropertyValuesHolder.ofKeyframe(
            View.ROTATION,
            Keyframe.ofFloat(0f, 0f),
            Keyframe.ofFloat(.1f, -3f * shakeFactor),
            Keyframe.ofFloat(.2f, -3f * shakeFactor),
            Keyframe.ofFloat(.3f, 3f * shakeFactor),
            Keyframe.ofFloat(.4f, -3f * shakeFactor),
            Keyframe.ofFloat(.5f, 3f * shakeFactor),
            Keyframe.ofFloat(.6f, -3f * shakeFactor),
            Keyframe.ofFloat(.7f, 3f * shakeFactor),
            Keyframe.ofFloat(.8f, -3f * shakeFactor),
            Keyframe.ofFloat(.9f, 3f * shakeFactor),
            Keyframe.ofFloat(1f, 0f)
        )
        var animator = ObjectAnimator.ofPropertyValuesHolder(view, pvhScaleX, pvhScaleY, pvhRotate).setDuration(1000)
        animator.repeatCount = ValueAnimator.INFINITE
        animator.start()
    }

    // nope方法实现了左右抖动。左右抖动的原理就是对view进行x轴的平移。
    private fun nope(view: View): ObjectAnimator {
        var delta =  UIUtil.dp(10).toFloat()

        var pvhTranslateX = PropertyValuesHolder.ofKeyframe (View.TRANSLATION_X,
        Keyframe.ofFloat(0f, 0f),
        Keyframe.ofFloat(.10f, -delta),
        Keyframe.ofFloat(.26f, delta),
        Keyframe.ofFloat(.42f, -delta),
        Keyframe.ofFloat(.58f, delta),
        Keyframe.ofFloat(.74f, -delta),
        Keyframe.ofFloat(.90f, delta),
        Keyframe.ofFloat(1f, 0f)
        )

        return ObjectAnimator.ofPropertyValuesHolder(view, pvhTranslateX).setDuration(500);
    }

}
