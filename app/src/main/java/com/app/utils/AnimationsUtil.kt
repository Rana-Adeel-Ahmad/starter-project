package com.app.utils

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import com.app.R

/**
 * @author Muzzamil
 */
/**
 * Animations util
 * Managing animations for weather
 * @constructor Create empty Animations util
 */
object AnimationsUtil {
    fun animateItemFromLeft(
        mContext: Context?,
        viewToAnimate: View
    ) {
        val animation =
            AnimationUtils.loadAnimation(mContext, R.anim.left_in_with_right)
        viewToAnimate.startAnimation(animation)
    }

    fun animateItemFromRight(
        mContext: Context?,
        viewToAnimate: View
    ) {
        val animation =
            AnimationUtils.loadAnimation(mContext, R.anim.right_in_with_left)
        viewToAnimate.startAnimation(animation)
    }

    fun animateItemFromBottom(
        mContext: Context?,
        viewToAnimate: View
    ) {
        val animation =
            AnimationUtils.loadAnimation(mContext, R.anim.up_from_bottom)
        viewToAnimate.startAnimation(animation)
    }

    fun animateItemShake(
        mContext: Context?,
        viewToAnimate: View
    ) {
        val animation =
            AnimationUtils.loadAnimation(mContext, R.anim.shake)
        val random = (Math.random() / 50f).toInt()
        animation.startOffset = random.toLong()
        animation.duration = 150 + random.toLong()
        viewToAnimate.startAnimation(animation)
    }

    @SuppressLint("NewApi")
    fun animateViewFromRight(
        mContext: Context?,
        v: View
    ) {
        v.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                v.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val animation = AnimationUtils.loadAnimation(
                    mContext,
                    R.anim.right_in_with_left
                )
                v.startAnimation(animation)
            }
        })
    }

    @SuppressLint("NewApi")
    fun animateViewFromLeft(mContext: Context?, v: View) {
        v.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                v.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val animation = AnimationUtils.loadAnimation(
                    mContext,
                    R.anim.left_in_with_right
                )
                v.startAnimation(animation)
            }
        })
    }

    fun animateBounce(v: View?, delay: Int) {
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(
            ObjectAnimator.ofFloat(v, "scaleX", 0f, 1.2f).setDuration(150)
        )
        animatorSet.playTogether(
            ObjectAnimator.ofFloat(v, "scaleY", 0f, 1.2f).setDuration(150)
        )
        val animatorSet1 = AnimatorSet()
        animatorSet1.playTogether(
            ObjectAnimator.ofFloat(v, "scaleX", 1.2f, 1.0f).setDuration(150)
        )
        animatorSet1.playTogether(
            ObjectAnimator.ofFloat(v, "scaleY", 1.2f, 1f).setDuration(150)
        )
        val bounce = AnimatorSet()
        bounce.playSequentially(animatorSet, animatorSet1)
        bounce.startDelay = delay.toLong()
        bounce.start()
    }

    fun expandWithAnimator(view: View, onAnimationEnd: ((view: View) -> Unit)) {
        //set Visible
        view.visibility = View.VISIBLE
        val widthSpec = View.MeasureSpec.makeMeasureSpec(
            0,
            View.MeasureSpec.UNSPECIFIED
        )
        val heightSpec = View.MeasureSpec.makeMeasureSpec(
            0,
            View.MeasureSpec.UNSPECIFIED
        )
        view.measure(widthSpec, heightSpec)
        val mAnimator =
            slideAnimator(0, view.measuredHeight, view)
        mAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animator: Animator) {
                val layoutParams = view.layoutParams
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                view.layoutParams = layoutParams
                onAnimationEnd.invoke(view)
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        mAnimator.start()
    }

    fun collapseWithAnimator(view: View) {
        val finalHeight = view.height
        val mAnimator = slideAnimator(finalHeight, 0, view)
        mAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animator: Animator) {
                view.visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        mAnimator.start()
    }

    private fun slideAnimator(start: Int, end: Int, view: View): ValueAnimator {
        val animator = ValueAnimator.ofInt(start, end)
        animator.addUpdateListener { valueAnimator -> //Update Height
            val value = valueAnimator.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.height = value
            view.layoutParams = layoutParams
        }
        return animator
    }

    @SuppressLint("NewApi")
    fun animateBounceFromBottom(v: View, delay: Long) {
        v.translationY = 1090f
        v.animate().translationY(-50f).setInterpolator(AccelerateDecelerateInterpolator())
            .setDuration(500).setStartDelay(delay)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {}
                override fun onAnimationEnd(animator: Animator) {
                    v.animate().translationY(0f).setDuration(200)
                        .setInterpolator(AccelerateDecelerateInterpolator())
                        .setStartDelay(0)
                        .start()
                }

                override fun onAnimationCancel(animator: Animator) {}
                override fun onAnimationRepeat(animator: Animator) {}
            })
    }

    fun animateDownWithAlpha(v: View) {
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(
            ObjectAnimator.ofFloat(v, "translationY", 20f).setDuration(1000)
        )
        animatorSet.playTogether(
            ObjectAnimator.ofFloat(
                v,
                View.ALPHA,
                0f,
                0.8f
            ).setDuration(800)
        )

        // animatorSet.setDuration(800);
        animatorSet.interpolator = AccelerateDecelerateInterpolator()
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {}
            override fun onAnimationEnd(animator: Animator) {
                val scaleUp = AnimatorSet()
                scaleUp.playTogether(
                    ObjectAnimator.ofFloat(v, "translationY", 0f)
                )
                scaleUp.playTogether(
                    ObjectAnimator.ofFloat(
                        v,
                        View.ALPHA,
                        0.8f,
                        1.0f
                    )
                )
                scaleUp.duration = 200
                scaleUp.interpolator = AccelerateDecelerateInterpolator()
                scaleUp.start()
            }

            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
        animatorSet.start()
    }

    @SuppressLint("NewApi")
    fun animateBounceFromLeft(
        v: View,
        duration: Int,
        delay: Long
    ) {
        v.animate().translationX(20f).setInterpolator(AccelerateDecelerateInterpolator())
            .setDuration(duration.toLong()).setStartDelay(delay)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {}
                override fun onAnimationEnd(animator: Animator) {
                    v.animate().translationX(0f).setDuration(200)
                        .setInterpolator(AccelerateDecelerateInterpolator())
                        .setStartDelay(0)
                        .start()
                }

                override fun onAnimationCancel(animator: Animator) {}
                override fun onAnimationRepeat(animator: Animator) {}
            })
    }

    @SuppressLint("NewApi")
    fun animateBounceFromRight(
        v: View,
        duration: Int,
        delay: Long
    ) {
        v.animate().translationX(-20f).setInterpolator(AccelerateDecelerateInterpolator())
            .setDuration(duration.toLong()).setStartDelay(delay)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {}
                override fun onAnimationEnd(animator: Animator) {
                    v.animate().translationX(0f).setDuration(200)
                        .setInterpolator(AccelerateDecelerateInterpolator())
                        .setStartDelay(0)
                        .start()
                }

                override fun onAnimationCancel(animator: Animator) {}
                override fun onAnimationRepeat(animator: Animator) {}
            })
    }

    fun animateWithAlpha(
        v: View,
        duration: Long,
        delay: Int,
        alpha: Float
    ) {
        val animatorSet = AnimatorSet()
        v.alpha = 0f
        animatorSet.playTogether(
            ObjectAnimator.ofFloat(
                v,
                View.ALPHA,
                0f,
                alpha
            )
        )
        animatorSet.setDuration(duration).startDelay = delay.toLong()
        animatorSet.interpolator = AccelerateDecelerateInterpolator()
        animatorSet.start()
    }

    @SuppressLint("NewApi")
    fun animateBounceFromTop(v: View, duration: Int, delay: Long) {
        v.animate().translationY(30f).setInterpolator(AccelerateDecelerateInterpolator())
            .setDuration(duration.toLong()).setStartDelay(delay)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {}
                override fun onAnimationEnd(animator: Animator) {
                    v.animate().translationY(0f).setDuration(200)
                        .setInterpolator(DecelerateInterpolator())
                        .setStartDelay(0)
                        .start()
                }

                override fun onAnimationCancel(animator: Animator) {}
                override fun onAnimationRepeat(animator: Animator) {}
            })
    }
}