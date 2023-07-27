package com.example.bookskotlin

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.bookskotlin.models.Book
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class BookDetailsActivity : AppCompatActivity() {
    lateinit var image : ImageView
    lateinit var backButton : ImageView
    lateinit var title : TextView
    lateinit var author : TextView
    lateinit var genre : TextView
    lateinit var button : Button

    private var mInterstitialAd: InterstitialAd? = null
    private final var TAG = "MainActivity"

    lateinit var mAdView : AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)

        initViews()

        mAdView = findViewById(R.id.adView)
        val adRequest1 = AdRequest.Builder().build()
        mAdView.loadAd(adRequest1)

        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this,"ca-app-pub-6475131326018443/9624241678", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError.toString())
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })

        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d(TAG, "Ad was clicked.")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                Log.d(TAG, "Ad dismissed fullscreen content.")
                mInterstitialAd = null
            }



            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d(TAG, "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad showed fullscreen content.")
            }
        }



        // Get the Book object from the intent's extras
        val book: Book? = intent.getSerializableExtra("BOOK_EXTRA") as? Book

        val textView = findViewById<TextView>(R.id.textView6)

        if (book != null) {

            Glide.with(this)
                .load(book.simple_thumb)
                .into(image)
            title.text = book.title
            author.text = book.author
            genre.text = book.genre

            button.setOnClickListener {
                // Replace "bookUrl" with the actual URL you want to open
                val bookUrl = book.url // You need to define a url property in the Book class
                if (bookUrl!!.isNotEmpty()) {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(bookUrl))
                    startActivity(browserIntent)
                }
            }

            backButton.setOnClickListener {
                onBackPressed()
                mInterstitialAd?.show(this)
            }


        }
    }

    private fun initViews() {
        image = findViewById(R.id.roundedImageView)
        backButton = findViewById(R.id.imageView)
        title = findViewById(R.id.textView7)
        author = findViewById(R.id.textView8)
        genre = findViewById(R.id.textView4)
        button = findViewById(R.id.button)
    }
}