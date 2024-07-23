package ru.otus.animations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tikTokView = findViewById<TikTokAnimateCircle>(R.id.tiktok)
        val rippleView = findViewById<RippleAnimationView>(R.id.ripple)

        tikTokView.setOnClickListener {
            tikTokView.startFirstAnimation()
        }

        rippleView.setOnClickListener {
            rippleView.generateAnimatedCircle()
        }
    }
}
