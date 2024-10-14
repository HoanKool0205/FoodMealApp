package com.androidexp.englishapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.androidexp.englishapp.MainActivity
import com.androidexp.englishapp.adapter.FavoritesMealAdapter
import com.androidexp.englishapp.databinding.FragmentFavoritesBinding
import com.androidexp.englishapp.videoModel.HomeViewModel

class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favoritesAdapter: FavoritesMealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Lấy viewModel từ MainActivity
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeFavorites()  // Đảm bảo phương thức này được gọi
    }

    private fun prepareRecyclerView() {
        favoritesAdapter = FavoritesMealAdapter()
        binding.rvFavorites.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = favoritesAdapter
        }
    }

    private fun observeFavorites() {
        Log.d("TAG", "observeFavorites called") // Log để kiểm tra phương thức có được gọi không

        viewModel.observeFavoritesMealsLiveData().observe(viewLifecycleOwner, Observer { meals ->
            Log.d("TAG", "Number of favorite meals: ${meals.size}") // Kiểm tra số lượng bữa ăn yêu thích

            if (meals.isNotEmpty()) {
                meals.forEach { meal ->
                    Log.d("TAG", "observeFavorites: ${meal.idMeal}")
                }
                favoritesAdapter.differ.submitList(meals) // Cập nhật adapter với danh sách mới
            } else {
                Log.d("TAG", "No favorite meals found.") // Log nếu không có món ăn nào
            }
        })
    }
}
