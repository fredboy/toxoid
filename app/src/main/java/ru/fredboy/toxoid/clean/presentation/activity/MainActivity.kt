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

    companion object {
        const val RESULT_USER_CREATED = 101
        const val KEY_LOCAL_USER = "key_local_user"
    }

    @Inject
    lateinit var initializeWithMockDataUseCase: InitializeWithMockDataUseCase

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

        if (initializeWithMockDataUseCase.isFirstLaunch()) {
            val welcomeIntent = Intent(applicationContext, WelcomeActivity::class.java)
            startActivity(welcomeIntent)
            initializeWithMockDataUseCase.setFirstLaunch()
        } else {
            initToxServiceUseCase.execute()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_main), binding.root)
        setupActionBarWithNavController(navController, appBarConfiguration)

        setupSlider()
        binding.slider.setupWithNavController(navController)
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
                        iconDrawable = Identicon(ToxAddress(hexStringToByteArray(user.id)))
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