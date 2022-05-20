package com.fxkxb.circlerecyclerviewdemo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class FruitAdapter(private val data: List<Fruit>) :

    RecyclerView.Adapter<FruitAdapter.ViewHolder>() {
    class ViewHolder(var fruitView: View) : RecyclerView.ViewHolder(
        fruitView) {
        var imageView: ImageView
        init {
            imageView = fruitView.findViewById<View>(R.id.fruit) as ImageView
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.fruit_item, parent, false)
        val holder = ViewHolder(view)
        holder.fruitView.setOnClickListener { view ->
            val position = holder.adapterPosition
            val fruit = data[position]
            Log.e("TAG", "onClick: " + fruit.name)
            Toast.makeText(view.context, fruit.name, Toast.LENGTH_SHORT).show()
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit = data[position]
        holder.imageView.setImageResource(fruit.name)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}