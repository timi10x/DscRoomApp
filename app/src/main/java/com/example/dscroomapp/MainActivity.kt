package com.example.dscroomapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dscroomapp.databinding.ActivityMainBinding
import com.example.dscroomapp.presentation.MainViewModel
import com.example.dscroomapp.presentation.adapter.UserAdapter
import com.example.dscroomapp.utils.Resource
import com.example.dscroomapp.utils.initRecyclerViewWithLineDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: UserAdapter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRV()
        setupObserver()
    }

    private fun setUpRV(){
        adapter = UserAdapter()
        binding.userRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.userRecyclerView.initRecyclerViewWithLineDecoration(this)
        binding.userRecyclerView.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.users.observe(this, {
            when(it.status){
                Resource.Status.SUCCESS -> {
                    binding.shimmerViewContainer.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
                    binding.userRecyclerView.visibility = View.VISIBLE
                    binding.errorOnUserLoading.visibility = View.GONE
                }
                Resource.Status.LOADING -> {
                    binding.shimmerViewContainer.visibility = View.VISIBLE
                }
                Resource.Status.ERROR -> {
                    binding.shimmerViewContainer.visibility = View.GONE
                    binding.userRecyclerView.visibility = View.GONE
                }
            }
        })
    }
}