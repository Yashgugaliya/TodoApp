package com.example.todoapp.ui.fragments.list

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.todoapp.R
import com.example.todoapp.utils.hideKeyboard
import com.example.todoapp.model.ToDoData
import com.example.todoapp.viewModel.ToDoViewModel
import com.example.todoapp.viewModel.SharedViewModel
import com.example.todoapp.utils.SwipeToDelete
import com.example.todoapp.ui.fragments.list.adapter.ListAdapter
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val listadapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        val recyclerView = view.recyclerView
        recyclerView.adapter = listadapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 300
        }
        swipeToDelete(recyclerView)
        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            mSharedViewModel.checkIfDatabaseIsEmpty(data)
            listadapter.setData(data)
        })
        mSharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
            showEmptyDatabaseView(it)
        })
        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
        hideKeyboard(requireActivity())
        setHasOptionsMenu(true)
        return view
    }

    private fun showEmptyDatabaseView(emptyDatabase: Boolean) {
        if (emptyDatabase) {
            view?.no_data_iv?.visibility = View.VISIBLE
            view?.no_data_tv?.visibility = View.VISIBLE
        } else {
            view?.no_data_iv?.visibility = View.INVISIBLE
            view?.no_data_tv?.visibility = View.INVISIBLE
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
        val search  = menu.findItem(R.id.search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> confirmItemRemoval()
            R.id.menu_priority_high -> mToDoViewModel.sortByHighPriority.observe(this, Observer { listadapter.setData(it) })
            R.id.menu_priority_low -> mToDoViewModel.sortByLowPriority.observe(this, Observer { listadapter.setData(it) })
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query!=null)
            searchThroughDatabase(query)
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query!=null)
            searchThroughDatabase(query)
        return true
    }

    private fun restoreDeletedData(view: View, deletedData: ToDoData, position: Int){
        val snackbar = Snackbar.make(view, "Deleted '${deletedData.title}'",Snackbar.LENGTH_LONG)
        snackbar.setAction("Undo"){
            mToDoViewModel.insertData(deletedData)
        }
        snackbar.show()
    }
    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemToDelete = listadapter.dataList[viewHolder.adapterPosition]
                mToDoViewModel.deleteItem(itemToDelete)
                listadapter.notifyItemChanged(viewHolder.adapterPosition)
                //restore deleted
                restoreDeletedData(viewHolder.itemView, itemToDelete, viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mToDoViewModel.deleteAll()
            Toast.makeText(requireContext(), "Successfully Removed Everything!", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("NO") { _, _ -> }
        builder.setTitle("Delete Everything?")
        builder.setMessage("Are you sure you want to remove Everything?")
        builder.create().show()
    }

    private fun searchThroughDatabase(query: String) {
        var searchQuery = "%$query%"
        mToDoViewModel.searchDatabase(searchQuery).observe(this, Observer {list->
            list?.let {
                listadapter.setData(it)
            }
        })
    }
}