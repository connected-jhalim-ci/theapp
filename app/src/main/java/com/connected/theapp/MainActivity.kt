package com.connected.theapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.connected.theapp.databinding.ActivityMainBinding
import com.connected.theapp.databinding.ItemUserBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private val searchAdapter: SearchAdapter by lazy { SearchAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            viewModel = mainViewModel

            with(searchResult) {
                adapter = searchAdapter
                setHasFixedSize(true)
            }
        }

        mainViewModel.searchResult.observe(this, Observer {
            searchAdapter.updateData(it)
        })
    }

}

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.VH>() {

    private var data: List<User> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            DataBindingUtil.inflate<ItemUserBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_user,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        with(holder.binding) {
            login.text = data[position].login
            avatar.contentDescription = data[position].avatarUrl
        }
    }

    fun updateData(newData: List<User>) {
        data = newData
        notifyDataSetChanged()
    }

    class VH(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

}
