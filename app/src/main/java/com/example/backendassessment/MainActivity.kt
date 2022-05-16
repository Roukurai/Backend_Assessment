package com.example.backendassessment

import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.backendassessment.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private lateinit var binding:ActivityMainBinding
    private lateinit var adapter: QueryAdapter

    private lateinit var pageInput: TextInputLayout
    private lateinit var pageSizeInput: TextInputLayout
    private var isCached = false

    private val queryItems = mutableListOf<ResponseItem>()
    private var tempQueryList = mutableListOf<ResponseItem>()
    private val queryCaches = mutableListOf<QueryToCache>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pageInput = findViewById(R.id.txtPage)
        pageSizeInput = findViewById(R.id.txtPageSize)



        binding.svQuery.setOnQueryTextListener(this)
        initRecyclerView()

    }

    private fun initRecyclerView() {
        adapter = QueryAdapter(queryItems)
        binding.rvQuery.layoutManager= LinearLayoutManager(this)
        binding.rvQuery.adapter = adapter
    }

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.stackexchange.com/2.3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun completeQuery(page: Int,size:Int,search:String?){
        CoroutineScope(Dispatchers.IO).launch {
            val searchLow = search?.lowercase()
            for (queryCache in queryCaches.indices){
                val curQueryCache = queryCaches[queryCache].query
                val curPageCache = queryCaches[queryCache].page
                val curPageSizeCache = queryCaches[queryCache].pageSize

                if (searchLow == curQueryCache && curPageCache == page && curPageSizeCache == size){
                    isCached=true
                    tempQueryList = queryCaches[queryCache].queryItems.toMutableList()
                    break

                }else{isCached=false}
            }

            val call = getRetrofit().create(APIService::class.java).getResultsByQuery("search?page=${page.toString()}&pagesize=${size.toString()}&intitle=$search&site=stackoverflow")
            val queryResults = call.body()
            runOnUiThread {

                if (call.isSuccessful){
                    if (!isCached){//recyclerView to show results
                        Log.i("Test", "Not cached")
                        val items = queryResults?.items
                        if (items != null) {
                            queryItems.clear()
                            //Clearing list prior to adding items to list
                            for (item in items.indices) {
                                val owner = items?.get(item)?.owner
                                queryItems.add(items?.get(item))
                            }
                        }
                        val cacheData = QueryToCache(searchLow, page, size, queryItems)
                        queryCaches.add(cacheData)

                    }else{
                        queryItems.clear()
                        for (queryItem in tempQueryList.indices) {
                            queryItems.add(tempQueryList[queryItem])
                        }
                        Log.i("Testo","We Cached it sir ${queryItems.size}")
                    }

                    adapter.notifyDataSetChanged()
                }else{
                    //error response
                    Log.i("Test","Error")
                    showError()
                }
            }
        }
    }

    private fun showError() {
        println("Error has been encountered")
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()){
            var pagenull = binding.txtPageInput.text.toString().toIntOrNull()
            var pagesizenull = binding.txtPageSizeInput.text.toString().toIntOrNull()

            val page = pagenull ?: 1
            val pagesize = pagesizenull ?: 1

            completeQuery(page,pagesize,query)

        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}