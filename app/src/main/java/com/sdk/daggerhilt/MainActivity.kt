package com.sdk.daggerhilt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sdk.daggerhilt.adapter.UserAdapter
import com.sdk.daggerhilt.databinding.ActivityMainBinding
import com.sdk.daggerhilt.utils.ApiState
import com.sdk.daggerhilt.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var userAdapter: UserAdapter
    private lateinit var binding: ActivityMainBinding
    private val viewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews() {
        setupRv()
        lifecycleScope.launch {
            viewModel.response.collect {
                when (it) {
                    is ApiState.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.recyclerView.isVisible = false
                    }
                    is ApiState.Error -> {
                        binding.progressBar.isVisible = false
                    }
                    is ApiState.Success -> {
                        binding.progressBar.isVisible = false
                        binding.recyclerView.isVisible = true
                        userAdapter.submitList(it.users)
                    }
                }
            }
        }
    }

    private fun setupRv() = binding.recyclerView.apply {
        userAdapter = UserAdapter()
        adapter = userAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }
}