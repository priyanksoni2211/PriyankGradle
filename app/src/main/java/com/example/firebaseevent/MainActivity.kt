package com.example.firebaseevent

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.analytics.FirebaseAnalytics


class MainActivity : AppCompatActivity() {

    private lateinit var addTocart: Button
    private lateinit var cashOnDelivery: Button
    private lateinit var onlineTransfer: Button

    private lateinit var analystic: FirebaseAnalytics
    private lateinit var mAdView: AdView

    private var mInterstitialAd: InterstitialAd? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this)
        val adView = AdView(this)

       // adView.adSize = AdSize.BANNER
        adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"

        analystic = FirebaseAnalytics.getInstance(this)

        analystic.setAnalyticsCollectionEnabled(true)
        addTocart = findViewById(R.id.buttonAddToCart)
        cashOnDelivery = findViewById(R.id.buttonCashOnDelivery)
        onlineTransfer = findViewById(R.id.buttonOnlineTransfer)


        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        mAdView.adListener = object: AdListener() {
            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
            }

            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
        }

        addTocart.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("AddToCart", "Add To cart item1")
            bundle.putString("NAME", "iphone 15")
            bundle.putString("PRICE", "125000")
            analystic.logEvent("add_to_cart", bundle)
        }

        cashOnDelivery.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("CashOnDelivery", "Cash")
            analystic.logEvent("cash_on_delivery", bundle)
        }

        onlineTransfer.setOnClickListener {
           /* val bundle = Bundle()
            bundle.putString("OnlineTransfer", "online_pay")
            analystic.logEvent("online_pay", bundle) */

            val adRequest = AdRequest.Builder().build()
            InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError.toString().let { Log.d("TAG420", it) }
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d("TAG420", "Ad was loaded.")
                    mInterstitialAd = interstitialAd
                }
            })

            mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                override fun onAdClicked() {
                    // Called when a click is recorded for an ad.
                    Log.d("TAG12245", "Ad was clicked.")
                }

                override fun onAdDismissedFullScreenContent() {
                    // Called when ad is dismissed.
                    Log.d("TAG12245", "Ad dismissed fullscreen content.")
                    mInterstitialAd = null
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    // Called when ad fails to show.
                    Log.e("TAG12245", "Ad failed to show fullscreen content.")
                    mInterstitialAd = null
                }

                override fun onAdImpression() {
                    // Called when an impression is recorded for an ad.
                    Log.d("TAG12245", "Ad recorded an impression.")
                }

                override fun onAdShowedFullScreenContent() {
                    // Called when ad is shown.
                    Log.d("TAG12245", "Ad showed fullscreen content.")
                }
            }

        }
    }



}