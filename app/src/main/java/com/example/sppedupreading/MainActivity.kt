package com.example.sppedupreading

import android.content.Context
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sppedupreading.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvAdapter: GameFieldAdapter
    private var lastItemDecoration: GridItemDecoration? = null

    private var tableSize: Int = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setUpGameBoard()
        overrideRecyclerSize()
        binding.reshuffleBtn.setOnClickListener {
            rvAdapter.setupData(generateTableNumbers(tableSize))
            rvAdapter.notifyDataSetChanged()
        }
    }

    private fun setUpGameBoard() {
        val tableSizeArray = resources.getStringArray(R.array.table_size)
        binding.tableSizeSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    when (tableSizeArray[p2]) {
                        "5x5" -> {
                            tableSize = 5
                            setupAdapter()
                        }

                        "6x6" -> {
                            tableSize = 6
                            setupAdapter()
                        }

                        "7x7" -> {
                            tableSize = 7
                            setupAdapter()
                        }

                        "8x8" -> {
                            tableSize = 8
                            setupAdapter()
                        }

                        "9x9" -> {
                            tableSize = 9
                            setupAdapter()
                        }
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
    }

    private fun overrideRecyclerSize() {
        val lp = binding.myRv.layoutParams
        lp.height = getScreenWidth()
        lp.width = getScreenWidth()
    }

    private fun getScreenWidth(): Int {
        val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        return dm.widthPixels
    }

    private fun setupAdapter() {
        rvAdapter = GameFieldAdapter()
        val data = generateTableNumbers(tableSize)
        rvAdapter.setupData(data)
        rvAdapter.setScreenWidth(getScreenWidth())
        binding.myRv.apply {
            lastItemDecoration?.let { removeItemDecoration(it) }
            val myLayoutManager = object : GridLayoutManager(this@MainActivity, tableSize) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }
            layoutManager = myLayoutManager
            lastItemDecoration = GridItemDecoration(this@MainActivity, tableSize)
            addItemDecoration(lastItemDecoration!!)
            adapter = rvAdapter
        }
    }

    private fun generateTableNumbers(tableSize: Int): List<Int> {
        val set = mutableSetOf<Int>()
        while (set.size < tableSize * tableSize) {
            val randValue = kotlin.random.Random.nextInt(1, (tableSize * tableSize) + 1)
            set.add(randValue)
        }
        return set.toList()
    }
}