package com.kimadrian.rickandmorty.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kimadrian.rickandmorty.R
import com.kimadrian.rickandmorty.databinding.FragmentCharactersBinding

class CharactersFragment : Fragment() {

    lateinit var binding: FragmentCharactersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCharactersBinding.inflate(inflater, container, false)



        return binding.root
    }
}