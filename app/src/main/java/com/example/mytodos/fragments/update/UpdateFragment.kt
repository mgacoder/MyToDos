package com.example.mytodos.fragments.update

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.mytodos.R
import com.example.mytodos.data.models.Priority
import com.example.mytodos.databinding.FragmentUpdateBinding
import com.example.mytodos.fragments.SharedViewModel

class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    private lateinit var mTitle: EditText
    private lateinit var mDescription: EditText
    private lateinit var mPriority: Spinner
    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUpdateBinding.inflate(inflater, container, false)
        mTitle.setText(args.currentItem.title)
        mDescription.setText(args.currentItem.description)
        mPriority.setSelection(parsePriority(args.currentItem.priority))
        mPriority.onItemSelectedListener = mSharedViewModel.listener
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun parsePriority(priority: Priority): Int {
        return when(priority) {
            Priority.HIGH -> 0
            Priority.MEDIUM -> 1
            Priority.LOW -> 2
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu);
    }
}

