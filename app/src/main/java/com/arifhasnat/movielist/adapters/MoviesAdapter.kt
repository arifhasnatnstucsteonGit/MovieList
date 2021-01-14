package com.arifhasnat.movielist.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arifhasnat.movielist.R
import com.arifhasnat.movielist.view.MovieDetails
import com.arifhasnat.movielist.repositories.database.MovieEntity
import com.arifhasnat.movielist.global.Global
import com.squareup.picasso.Picasso

class MoviesAdapter(var rowsArrayList: List<MovieEntity>) :
    RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {
    private var mContext: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        mContext = parent.context
        val v: View = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.movie_adapter
                , parent, false
            )
        return MoviesViewHolder(v)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.title.text = rowsArrayList[position].title
        Picasso.with(mContext)
            .load(Global.POSTER_ROOT+rowsArrayList[position].posterPath)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(holder.poster)
        holder.itemView.setOnClickListener(View.OnClickListener {

            Global.posterPath = rowsArrayList[position].posterPath
            Global.title = rowsArrayList[position].title
            Global.description = rowsArrayList[position].overview
            mContext?.startActivity(Intent(mContext, MovieDetails::class.java))
        })
    }

    override fun getItemCount(): Int {
        return rowsArrayList.size
    }

    inner class MoviesViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var poster: ImageView

        init {
            title = itemView.findViewById(R.id.title)
            poster = itemView.findViewById(R.id.poster)
        }
    }

}
