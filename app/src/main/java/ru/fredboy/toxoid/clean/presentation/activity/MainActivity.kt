package ru.fredboy.toxoid.clean.presentation.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.iconDrawable
import com.mikepenz.materialdrawer.model.interfaces.nameText
import com.mikepenz.materialdrawer.util.addItems
import com.mikepenz.materialdrawer.util.setupWithNavController
import com.mikepenz.materialdrawer.widget.AccountHeaderView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import ru.fredboy.toxoid.R
import ru.fredboy.toxoid.clean.domain.model.Identicon
import ru.fredboy.toxoid.clean.domain.model.ToxAddress
import ru.fredboy.toxoid.clean.domain.usecase.bootstrap.IsFirstLaunchUseCase
import ru.fredboy.toxoid.clean.domain.usecase.bootstrap.SetFirstLaunchUseCase
import ru.fredboy.toxoid.clean.domain.usecase.tox.InitToxServiceUseCase
import ru.fredboy.toxoid.clean.domain.usecase.user.GetAllUsersUseCase
import ru.fredboy.toxoid.clean.domain.usecase.user.GetCurrentUserUseCase
import ru.fredboy.toxoid.clean.domain.usecase.tox.InitializeWithMockDataUseCase
import ru.fredboy.toxoid.databinding.ActivityMainBinding
import ru.fredboy.toxoid.tox.ToxService
import ru.fredboy.toxoid.utils.hexStringToByteArray
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var isFirstLaunchUseCase: IsFirstLaunchUseCase

    @Inject
    lateinit var getAllUsersUseCase: GetAllUsersUseCase

    @Inject
    lateinit var currentUserUseCase: GetCurrentUserUseCase

    @Inject
    lateinit var initToxServiceUseCase: InitToxServiceUseCase

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_main), binding.root)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.slider.setupWithNavController(navController)
    }

    override fun onStart() {
        super.onStart()
        setupSlider()
    }

    override fun onResume() {
        super.onResume()
        if (isFirstLaunchUseCase.execute()) {
            val welcomeIntent = Intent(applicationContext, WelcomeActivity::class.java)
            startActivity(welcomeIntent)
        } else {
            initToxServiceUseCase.execute()
        }
    }

    private fun setupSlider() {
        with(binding.slider) {
            val accountHeader = AccountHeaderView(this@MainActivity)
            accountHeader.attachToSliderView(this)

            runBlocking {
                val users = getAllUsersUseCase.execute()
                val profiles = users.map { user ->
                    ProfileDrawerItem().apply {
                        identifier = user.id.hashCode().toLong()
                        nameText = user.name
                        iconDrawable = Identicon(ToxAddress(hexStringToByteArray(user.id)).toToxPublicKey())
                            .getDrawable(resources, 160)
                    }
                }.toTypedArray()

                accountHeader.addProfiles(*profiles)

                addItems(

                )
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}