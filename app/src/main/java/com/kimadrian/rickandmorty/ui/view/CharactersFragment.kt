package com.kimadrian.rickandmorty.ui.view
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import androidx.paging.LoadState
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.google.android.material.snackbar.Snackbar
//import com.kimadrian.rickandmorty.data.repository.Repository
//import com.kimadrian.rickandmorty.databinding.FragmentCharactersBinding
//import com.kimadrian.rickandmorty.ui.adapter.CharactersRecyclerViewAdapter
//import com.kimadrian.rickandmorty.ui.viewmodel.CharactersViewModel
//import com.kimadrian.rickandmorty.ui.viewmodel.CharactersViewModelFactory
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import timber.log.Timber
//
//class CharactersFragment : Fragment() {
//
//    lateinit var binding: FragmentCharactersBinding
//    lateinit var viewModel: CharactersViewModel
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val adapter = CharactersRecyclerViewAdapter()
//        val characterRepository = Repository()
//        val viewModelFactory = CharactersViewModelFactory(characterRepository)
//
//        // Inflate the layout for this fragment
//        binding = FragmentCharactersBinding.inflate(inflater, container, false)
//        viewModel = ViewModelProvider(this, viewModelFactory)[CharactersViewModel::class.java]
//
//        CoroutineScope(Dispatchers.IO).launch {
//            viewModel.getCharacters().collect { data ->
//                 adapter.submitData(pagingData = data)
//            }
//        }
//
//        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
//        binding.recyclerView.adapter = adapter
//
//        adapter.addLoadStateListener { loadState ->
//
//            if (loadState.refresh is LoadState.Loading){
//                if (adapter.snapshot().isEmpty()){
//                    binding.shimmer.startShimmer()
//                }
//            } else {
//                binding.shimmer.stopShimmer()
//                binding.shimmer.visibility = View.GONE
//                val error = when {
//                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
//                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
//                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
//                    else -> null
//                }
//
//                error?.let {
//                    if (adapter.snapshot().isEmpty()) {
//                        binding.errorImage.visibility = View.VISIBLE
//                        binding.recyclerView.visibility = View.GONE
//                        Snackbar.make(binding.root, "Something went wrong. Check your internet connection", Snackbar.LENGTH_LONG).show()
//                    }
//                    Timber.e(it.error.message)
//                }
//            }
//
//        }
//
//        return binding.root
//    }
//}