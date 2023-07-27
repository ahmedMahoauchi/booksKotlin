package com.example.bookskotlin

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookskotlin.adapters.BooksAdapter
import com.example.bookskotlin.models.Book
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader

class MainActivity : AppCompatActivity() {
    lateinit var mAdView : AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)



        val bookList: MutableList<Book> = parseJsonFile(this)
       // Log.e("joella",bookList.toString())
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val booksAdapter = BooksAdapter(bookList,this)
        booksAdapter.shuffleBooks()
        recyclerView.adapter = booksAdapter

    }

    fun parseJsonFile(context: Context): MutableList<Book> {
        val bookList = mutableListOf<Book>()

        try {
            val rawJson = context.resources.openRawResource(R.raw.data)
                .bufferedReader().use(BufferedReader::readText)

            val jsonArray = JSONArray(rawJson)
            for (i in 0 until jsonArray.length()) {
                val jsonObject: JSONObject = jsonArray.getJSONObject(i)
                val book = Book(
                    jsonObject.getString("author"),
                    jsonObject.getString("cover"),
                    jsonObject.getString("cover_color"),
                    jsonObject.getString("cover_thumb"),
                    jsonObject.getString("epoch"),
                    jsonObject.getString("full_sort_key"),
                    jsonObject.getString("genre"),
                    jsonObject.getBoolean("has_audio"),
                    jsonObject.getString("href"),
                    jsonObject.getString("kind"),
                    null,
                    jsonObject.getString("simple_thumb"),
                    jsonObject.getString("slug"),
                    jsonObject.getString("title"),
                    jsonObject.getString("url")
                    // Set the appropriate type for 'liked' field if possible
                )
                bookList.add(book)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return bookList
    }

}