package com.example.mytodos.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mytodos.R
import com.example.mytodos.data.models.Priority
import com.example.mytodos.data.models.ToDoData
import com.example.mytodos.data.viewmodel.ToDoViewModel
import com.example.mytodos.databinding.FragmentUpdateBinding
import com.example.mytodos.fragments.SharedViewModel

class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    private lateinit var mTitle: EditText
    private lateinit var mDescription: EditText
    private lateinit var mPriority: Spinner
    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mToDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUpdateBinding.inflate(inflater, container, false)
        mTitle.setText(args.currentItem.title)
        mDescription.setText(args.currentItem.description)
        mPriority.setSelection(mSharedViewModel.parsePriorityToInt(args.currentItem.priority))
        mPriority.onItemSelectedListener = mSharedViewModel.listener
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> confirmItemRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateItem() {
        val title = mTitle.text.toString()
        val description = mDescription.text.toString()
        val priority = mPriority.selectedItem.toString()

        val validation = mSharedViewModel.verifyDataFromUser(title, description)
        if(validation) {
            val updatedItem = ToDoData(
                args.currentItem.id,
                title,
                mSharedViewModel.parsePriority(priority),
                description
            )
            mToDoViewModel.updateData(updatedItem)
            Toast.makeText(context, "Successfully updated!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(context, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun confirmItemRemoval() {
        AlertDialog.Builder(context)
            .setPositiveButton("Yes") { _, _ ->
                mToDoViewModel.deleteItem(args.currentItem)
                Toast.makeText(
                    context,
                    "Successfully Removed: '${args.currentItem.title}'",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            }
            .setNegativeButton("No") { _,_ -> }
            .setTitle("Delete ${args.currentItem.title}?")
            .setMessage("Are you sure you want to remove ${args.currentItem.title}?")
            .create().show()
    }
}

