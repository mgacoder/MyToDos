package com.example.mytodos.fragments

import android.view.View
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.example.mytodos.R
import com.example.mytodos.data.models.Priority
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BindingAdapters {

    companion object {

        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {
            view.setOnClickListener {
                if(navigate) {
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>) {
            when(emptyDatabase.value) {
                true  -> view.visibility = View.VISIBLE
                false -> view.visibility = View.INVISIBLE
                else -> view.visibility = View.GONE
            }
        }

        @BindingAdapter("android:parsePriorityToInt")
        @JvmStatic
        fun parsePriorityToInt(view: Spinner, priority: Priority) {
            when(priority) {
                Priority.HIGH -> { view.setSelection(0) }
                Priority.MEDIUM -> { view.setSelection(1) }
                Priority.LOW -> { view.setSelection(2) }
            }
        }
    }
}