package com.androidexp.englishapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.androidexp.englishapp.activity.CategoryMealsActivity
import com.androidexp.englishapp.adapter.CategoriesAdapter
import com.androidexp.englishapp.databinding.FragmentCategoryBinding
import com.androidexp.englishapp.videoModel.HomeViewModel

class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var categoriesAdapter: CategoriesAdapter

    companion object {
        const val CATEGORY_NAME = "com.androidexp.englishapp.fragment.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Chuẩn bị RecyclerView
        prepareCategoriesRecyclerView()

        // Quan sát dữ liệu categories từ ViewModel
        observeCategoriesLiveData()

        // Xử lý sự kiện click vào một category
        onCategoryClick()
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        homeMvvm.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer { categories ->
            if (categories.isNotEmpty()) {
                categoriesAdapter.setCategoryList(categories)
            } else {
                // Xử lý trường hợp không có categories
                Log.e("CategoryFragment", "No categories available")
            }
        })
    }

    private fun onCategoryClick() {

        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }
}
