package com.example.todoapp.ui.fragments.update

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.R
import com.example.todoapp.model.ToDoData
import com.example.todoapp.viewModel.ToDoViewModel
import com.example.todoapp.viewModel.SharedViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        view.current_title_et.setText(args.currentItem.title)
        view.current_description_et.setText(args.currentItem.description)
        view.current_priorities_spinner.setSelection(mSharedViewModel.parsePriorityToInt(args.currentItem.priority))
        view.current_priorities_spinner.onItemSelectedListener = mSharedViewModel.listener
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> updateData()
            R.id.menu_delete -> confirmItemRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ -> mToDoViewModel.deleteItem(args.currentItem)
            Toast.makeText(requireContext(), "Successfully Removed: ${args.currentItem.title}", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("NO") { _, _ -> }
        builder.setTitle("Delete ${args.currentItem.title}?")
        builder.setMessage("Are you sure you want to remove ${args.currentItem.title}?")
        builder.create().show()
    }

    private fun updateData() {
        val mTitle = current_title_et.text.toString()
        val mPriority = current_priorities_spinner.selectedItem.toString()
        val mDescription = current_description_et.text.toString()
        val valid = mSharedViewModel.verifyData(mTitle, mDescription)
        if (valid) {
            val updateData = ToDoData(
                args.currentItem.id,
                mTitle,
                mSharedViewModel.parsePriority(mPriority),
                mDescription
            )
            mToDoViewModel.updateData(updateData)
            Toast.makeText(requireContext(), "Successfully Updated!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else
            Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_SHORT)
                .show()
    }
}