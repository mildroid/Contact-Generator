package com.mildroid.contactgenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.graphics.drawable.toDrawable
import com.mildroid.contactgenerator.core.log
import com.mildroid.contactgenerator.core.viewBinding
import com.mildroid.contactgenerator.databinding.ActivityMainBinding
import com.mildroid.contactgenerator.domain.GenerateUseCase
import com.mildroid.contactgenerator.domain.UseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.viewModel.generate()
    }
}