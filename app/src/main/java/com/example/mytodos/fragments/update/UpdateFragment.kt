package com.example.mytodos.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mytodos.R
import com.example.mytodos.data.models.Priority
import com.example.mytodos.data.models.ToDoData
import com.example.mytodos.data.viewmodel.ToDoViewModel
import com.example.mytodos.databinding.FragmentUpdateBinding
import com.example.mytodos.fragments.SharedViewModel

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mToDoViewModel: ToDoViewModel by viewModels()

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        binding.args = args
        binding.currentPrioritySpinner.onItemSelectedListener = mSharedViewModel.listener
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object: MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.update_fragment_menu, menu);
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId) {
                    R.id.menu_save -> updateItem()
                    R.id.menu_delete -> confirmItemRemoval()
                    android.R.id.home -> requireActivity().onBackPressed()
                }
                return true
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun updateItem() {
        val title = binding.currentTitleEditText.text.toString()
        val description = binding.currentDescriptionEditTextTextMultiLine.text.toString()
        val priority = binding.currentPrioritySpinner.selectedItem.toString()

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

