package com.example.bookskotlin.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookskotlin.BookDetailsActivity
import com.example.bookskotlin.R
import com.example.bookskotlin.models.Book

class BooksAdapter(private val books: MutableList<Book>,context : Context) : RecyclerView.Adapter<BooksAdapter.BookViewHolder>() {


    val context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books.get(position)
        holder.bind(book)
    }

    override fun getItemCount(): Int {
        return books.size
    }

    fun shuffleBooks() {
        books.shuffle()
        notifyDataSetChanged()
    }

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        private val bookAuthor: TextView = itemView.findViewById(R.id.textView2)
        private val bookTitle: TextView = itemView.findViewById(R.id.textView3)
        private val bookImage: ImageView = itemView.findViewById(R.id.roundedImageView)
        private val bookGender: TextView = itemView.findViewById(R.id.textView4)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(book: Book) {

            bookAuthor.text = book.author
            bookTitle.text = book.title
            bookGender.text = book.genre
            Glide.with(context)
                .load(book.simple_thumb)
                .into(bookImage)


        }

        override fun onClick(view: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val book = books[position]

                val intent = Intent(context, BookDetailsActivity::class.java)
                intent.putExtra("BOOK_EXTRA", book)
                context.startActivity(intent)
            }
        }
    }

}