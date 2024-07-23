package ru.otus.animations

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

class TikTokAnimateCircle @JvmOverloads constructor(
    context: Context? = null,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {
    private val colorPurple = Paint().apply {
        style = Paint.Style.FILL
        color = Color.parseColor("#7C4FFF")
    }

    private val colorPink = Paint().apply {
        style = Paint.Style.FILL
        color = Color.parseColor("#FF39D4")
    }

    private val radius: Float
        get() = width / 8f
    private val margin: Float
        get() = radius / 2f
    private val firstCircleCx: Float
        get() = width / 2 - margin / 2 - radius
    private val secondCircleCx: Float
        get() = firstCircleCx + radius * 2 + margin
    private val cy: Float
        get() = height / 2f

    private var firstDx = 0f
    private var secondDx = 0f
    private var firstScale = 1f
    private var secondScale = 1f
    private var opacity = 1f

    private val animatorSet = AnimatorSet()

    init {
        animatorSet.interpolator = AccelerateDecelerateInterpolator()
        animatorSet.startDelay = 200
        animatorSet.duration = 1000
        animatorSet.addListener(object : AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                startSecondAnimation()
            }
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        post {
//            startFirstAnimation()
        }
    }

    fun startFirstAnimation() {
        animatorSet.apply {
            playTogether(
                ValueAnimator.ofFloat(0f, secondCircleCx - firstCircleCx).apply {
                    addUpdateListener {
                        firstDx = it.animatedValue as Float
                        invalidate()
                    }
                },
                ValueAnimator.ofFloat(0f, -(secondCircleCx - firstCircleCx)).apply {
                    addUpdateListener {
                        secondDx = it.animatedValue as Float
                        invalidate()
                    }
                },
                ValueAnimator.ofFloat(1f, 1.1f, 1f).apply {
                    addUpdateListener {
                        firstScale = it.animatedValue as Float
                        invalidate()
                    }
                },
                ValueAnimator.ofFloat(1f, 0.7f, 1f).apply {
                    addUpdateListener {
                        secondScale = it.animatedValue as Float
                        invalidate()
                    }
                },
                ValueAnimator.ofFloat(1f, 0f, 1f).apply {
                    addUpdateListener {
                        opacity = it.animatedValue as Float
                        invalidate()
                    }
                }
            )
            start()

        }
    }

    private fun startSecondAnimation() {
        animatorSet.apply {
            playTogether(
                ValueAnimator.ofFloat(secondCircleCx - firstCircleCx, 0f).apply {
                    addUpdateListener {
                        firstDx = it.animatedValue as Float
                        invalidate()
                    }
                },
                ValueAnimator.ofFloat(-(secondCircleCx - firstCircleCx), 0f).apply {
                    addUpdateListener {
                        secondDx = it.animatedValue as Float
                        invalidate()
                    }
                }
            )
            start()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return

        val firstCircleCx = this.firstCircleCx + firstDx
        val secondCircleCx = this.secondCircleCx + secondDx

        colorPink.alpha = (opacity * 255).toInt()

        canvas.drawCircle(firstCircleCx, cy, radius * firstScale, colorPurple)
        canvas.drawCircle(secondCircleCx, cy, radius * secondScale, colorPink)
    }
}
