package ru.fredboy.toxoid.clean.presentation.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.fredboy.toxoid.R
import ru.fredboy.toxoid.clean.domain.usecase.InitializeWithMockDataUseCase
import ru.fredboy.toxoid.databinding.ActivityMainBinding
import ru.fredboy.toxoid.tox.ToxService
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var initializeWithMockDataUseCase: InitializeWithMockDataUseCase

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeMockDataIfNeed()

        val toxServiceIntent = Intent(applicationContext, ToxService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(toxServiceIntent)
        } else {
            startService(toxServiceIntent)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun initializeMockDataIfNeed() {
        if (initializeWithMockDataUseCase.isFirstLaunch()) {
            val welcomeIntent = Intent(applicationContext, WelcomeActivity::class.java)
            startActivity(welcomeIntent)
            initializeWithMockDataUseCase.setFirstLaunch()
            finish()
        }
    }
}