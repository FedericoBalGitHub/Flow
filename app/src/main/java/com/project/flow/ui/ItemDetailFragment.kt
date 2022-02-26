package com.project.flow.ui

import android.content.ClipData
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.project.flow.data.remote.APIService
import com.project.flow.data.remote.model.character
//import com.project.flow.placeholder.PlaceholderContent
import com.project.flow.databinding.FragmentItemDetailBinding
import com.project.flow.util.Resource
import com.project.flow.util.visible

/**
 * Created by Federico Bal on 22/2/2022.
 */

class ItemDetailFragment : Fragment() {
    private var id: Int? = null
    lateinit var itemDetailTextView: TextView
    lateinit var progressbar: ProgressBar
    lateinit var textViewError: TextView
    lateinit var buttonRetry: TextView
    private var toolbarLayout: CollapsingToolbarLayout? = null
    private var _binding: FragmentItemDetailBinding? = null
    lateinit var detailCharacterViewModel: DetailCharacterViewModel
    private val binding get() = _binding!!

    private val dragListener = View.OnDragListener { v, event ->
        if (event.action == DragEvent.ACTION_DROP) {
            val clipDataItem: ClipData.Item = event.clipData.getItemAt(0)
            val dragData = clipDataItem.text
            updateContent()
        }

        true
    }

    private fun setupViewModel() {
        val factory = CharacterViewModelFactory(APIService())
        detailCharacterViewModel = ViewModelProvider(this, factory).get(DetailCharacterViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        arguments?.let {
            Log.d("ItemDetailFragment",it.toString())
            if (it.containsKey(ARG_ITEM_ID)) {
                try {
                    id = (it.getString(ARG_ITEM_ID))!!.toInt()
                }
                catch (e:Exception)
                {
                    Log.e("FLOW",e.toString())
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        val rootView = binding.root

        toolbarLayout = binding.toolbarLayout
        itemDetailTextView = binding.itemDetail
        progressbar = binding.progressbar
        buttonRetry = binding.buttonRetry
        textViewError = binding.textViewError
        ocultar()
        updateContent()
        buttonRetry.setOnClickListener {
            updateContent()
        }
        rootView.setOnDragListener(dragListener)
        return rootView
    }

    private fun updateContent() {
        id?.let {
            detailCharacterViewModel.getCharacter(id!!)
        }
        detailCharacterViewModel.characterResponse.observe(viewLifecycleOwner
            , Observer{
            try
            {
                Log.d("FLOW","observer result")
                when (it) {
                    is Resource.Success -> {
                        sussess(it)
                    }
                    is Resource.Loading -> {
                        loading()
                    }
                    is Resource.Failure -> {
                        failure(it)
                    }
                    else -> {
                        Log.e("FLOW","Error")
                    }
                }
            }
            catch(ex:Exception)
            {
                Log.e("FLOW","Exception:${ex.message}")
            }
        })
    }

    private fun failure(it: Resource.Failure) {
        progressbar.visible(false)
        buttonRetry.visible(true)
        textViewError.visible(true)
        textViewError.text = "Error al conectarse: " + it.exception.message
        Toast.makeText(
            activity,
            "Error al conectarse: " + it.exception.message,
            Toast.LENGTH_SHORT
        ).show()
        Log.e("FLOW", "Exception:${it.exception.message}")
    }

    private fun loading() {
        Log.d("FLOW", "Resource.Loading")
        progressbar.visible(true)
        buttonRetry.visible(false)
        textViewError.visible(false)
    }

    private fun sussess(it: Resource.Success<character>) {
        try {
            Log.d("FLOW", "Resource.Success")
            ocultar()
            toolbarLayout?.title = it.data.name
            itemDetailTextView.text = getDetail(it)
        } catch (ex: Exception) {
            Log.e("FLOW", "Exception:${ex.message}")
        }
    }

    private fun ocultar() {
        progressbar.visible(false)
        buttonRetry.visible(false)
        textViewError.visible(false)
    }

    private fun getDetail(it: Resource.Success<character>) =
        "name:" + it.data.name +
                "status:" + it.data.status +
                "species:" + it.data.species

    companion object {
        const val ARG_ITEM_ID = "item_id"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}