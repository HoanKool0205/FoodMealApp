package com.androidexp.englishapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidexp.englishapp.databinding.MealItemBinding
import com.androidexp.englishapp.model.Meal
import com.bumptech.glide.Glide

class SearchMealAdapter : RecyclerView.Adapter<SearchMealAdapter.SearchMealViewHolder>() {

    lateinit var onItemClick: ((Meal) -> Unit)

    var searchMealsList = ArrayList<Meal>()

    fun setSearchMeals(searchMealsList: ArrayList<Meal>) {
        this.searchMealsList = searchMealsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMealViewHolder {
        return SearchMealViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return searchMealsList.size
    }

    override fun onBindViewHolder(holder: SearchMealViewHolder, position: Int) {
        val meal = searchMealsList[position]
        Glide.with(holder.itemView)
            .load(meal.strMealThumb)
            .into(holder.binding.imgMeal)

        holder.binding.tvMealName.text = meal.strMeal

        holder.itemView.setOnClickListener {
            onItemClick.invoke(meal)
        }
    }

    class SearchMealViewHolder(val binding: MealItemBinding) : RecyclerView.ViewHolder(binding.root)
}
