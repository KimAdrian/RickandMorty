package com.kimadrian.rickandmorty.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kimadrian.rickandmorty.R
import com.kimadrian.rickandmorty.data.repository.Repository
import com.kimadrian.rickandmorty.databinding.FragmentCharactersBinding
import com.kimadrian.rickandmorty.ui.adapter.CharactersRecyclerViewAdapter
import com.kimadrian.rickandmorty.ui.viewmodel.CharactersViewModel
import com.kimadrian.rickandmorty.ui.viewmodel.CharactersViewModelFactory
import com.kimadrian.rickandmorty.utils.Status
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.observeOn
import timber.log.Timber

class CharactersFragment : Fragment() {

    lateinit var binding: FragmentCharactersBinding
    lateinit var viewModel: CharactersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val repository = Repository()
        val viewModelFactory = CharactersViewModelFactory(repository)
        val adapter = CharactersRecyclerViewAdapter()

        // Inflate the layout for this fragment
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, viewModelFactory)[CharactersViewModel::class.java]

        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getCharacters().collect { resource ->
                when(resource.status) {
                    Status.SUCCESS -> {
                        resource.data.let { characters ->
                            CoroutineScope(Dispatchers.Main).launch {
                                binding.progressBar.visibility = View.GONE
                                binding.recyclerView.visibility = View.VISIBLE
                                adapter.submitList(characters)
                            }
                        }
                    }
                    Status.LOADING -> {
                        CoroutineScope(Dispatchers.Main).launch {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                        }
                    }
                    Status.ERROR -> {
                        CoroutineScope(Dispatchers.Main).launch {
                            binding.progressBar.visibility = View.GONE
                            binding.recyclerView.visibility = View.GONE
                            binding.errorImage.visibility = View.VISIBLE
                            Snackbar.make(binding.root, "${resource.message}", Snackbar.LENGTH_LONG).show()
                            Timber.e(resource.message)
                        }
                    }
                }
            }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = adapter

        return binding.root
    }
}