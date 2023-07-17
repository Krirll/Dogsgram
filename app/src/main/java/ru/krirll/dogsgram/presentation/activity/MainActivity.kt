package ru.krirll.dogsgram.presentation.activity

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.krirll.dogsgram.R
import ru.krirll.dogsgram.databinding.ActivityMainBinding
import ru.krirll.dogsgram.presentation.activity.adapter.RecyclerViewAdapter
import ru.krirll.dogsgram.presentation.view_model.MainViewModel

class MainActivity : AppCompatActivity() {

    private var _viewBinding: ActivityMainBinding? = null
    private val viewBinding: ActivityMainBinding
        get() = _viewBinding ?: throw RuntimeException("ActivityMainBinding is null")

    private val viewModel: MainViewModel by viewModel()

    private var adapter : RecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        setViewSettings()
        observeViewModel()
        initProgressBar()
        initRecyclerView()
        initScreenMode()
        initSwitchButton()
    }

    private fun initViewBinding() {
        _viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }

    private fun setViewSettings() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.root) { view, windowInsets ->
            val insetsSystemBar = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                with(insetsSystemBar) {
                    leftMargin = left
                    bottomMargin = bottom
                    rightMargin = right
                    topMargin = top
                }
            }

            WindowInsetsCompat.CONSUMED
        }
    }

    private fun initProgressBar() {
        viewBinding.progressBar.setOnClickListener {
            setProgressDrawable(R.drawable.corgi_placeholder)
            viewModel.getDogs()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect {
                    when(it) {
                        is State.Loading -> {
                            setProgressDrawable(R.drawable.corgi_placeholder)
                        }
                        is State.Success -> {
                            adapter?.submitList(it.dogs)
                            viewBinding.progressBar.visibility = View.GONE
                        }
                        is State.Failure -> {
                            Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_LONG).show()
                            setProgressDrawable(R.drawable.retry)
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        adapter = RecyclerViewAdapter()
        viewBinding.recyclerView.adapter = adapter
        viewBinding.recyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                with(recyclerView.layoutManager as LinearLayoutManager) {
                    if ((childCount + findFirstVisibleItemPosition()) >= itemCount) {
                        if (viewModel.state.value != State.Loading)
                            viewModel.getDogs()
                    }
                }
            }
        })

    }

    private fun initScreenMode() {
        when(resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                viewBinding.switchButton.isChecked = true
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                viewBinding.switchButton.isChecked = false
            }
        }
    }

    private fun initSwitchButton() {
        viewBinding.switchButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
                viewBinding.switchButton.isChecked = true
            }
            else {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
                viewBinding.switchButton.isChecked = false
            }
        }
    }

    private fun setProgressDrawable(drawableRes: Int) {
        viewBinding.progressBar.indeterminateDrawable = AppCompatResources.getDrawable(
            this@MainActivity.applicationContext,
            drawableRes
        )
    }
}