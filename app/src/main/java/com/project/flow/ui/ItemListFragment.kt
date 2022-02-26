package com.project.flow.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.flow.R
import com.project.flow.data.remote.model.character
import com.project.flow.databinding.FragmentItemListBinding
import com.project.flow.data.remote.APIService
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by Federico Bal on 22/2/2022.
 */

class ItemListFragment : Fragment() {

    private var _binding: FragmentItemListBinding? = null
    lateinit var characterViewModel: CharacterViewModel
    lateinit var characterAdapter: CharacterAdapter
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.itemList

        val itemDetailFragmentContainer: View? = view.findViewById(R.id.item_detail_nav_container)

        val onClickListener = View.OnClickListener { itemView ->
            val item = itemView.tag as character
            val bundle = Bundle()
            bundle.putString(
                ItemDetailFragment.ARG_ITEM_ID,
                item.id.toString()
            )
            if (itemDetailFragmentContainer != null) {
                itemDetailFragmentContainer.findNavController()
                    .navigate(R.id.fragment_item_detail, bundle)
            } else {
                itemView.findNavController().navigate(R.id.show_item_detail, bundle)
            }
        }
        val onContextClickListener = View.OnContextClickListener { v ->
            val item = v.tag as character
            Toast.makeText(
                v.context,
                "Context click of item " + item.id,
                Toast.LENGTH_LONG
            ).show()
            true
        }
        setupViewModel()
        setupRecyclerView(onClickListener, onContextClickListener)
        setupValues()
    }

    private fun setupViewModel() {
        val factory = CharacterViewModelFactory(APIService())
        characterViewModel = ViewModelProvider(this, factory).get(CharacterViewModel::class.java)
    }
    private fun setupRecyclerView(
        onClickListener: View.OnClickListener,
        onContextClickListener: View.OnContextClickListener
    ) {
        characterAdapter = CharacterAdapter(onClickListener,onContextClickListener)
        binding.itemList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = characterAdapter.withLoadStateHeaderAndFooter(
                header = CharacterLoadStateAdapter { characterAdapter.retry() },
                footer = CharacterLoadStateAdapter { characterAdapter.retry() }
            )
            setHasFixedSize(true)
        }

    }
    private fun setupValues() {
        lifecycleScope.launch {
            characterViewModel.characters.collectLatest { pagedData ->
                characterAdapter.submitData(pagedData)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}