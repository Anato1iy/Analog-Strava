package com.example.strava4

import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.example.navigations.NavigationFlow
import com.example.navigations.Navigator
import com.example.navigations.ToFlowNavigatable
import com.example.utils.ActBar
import com.example.utils.DarkTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ToFlowNavigatable, ActBar, DarkTheme {

    private val navigator: Navigator = Navigator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.container_fragment)
        navigator.navController = navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == 2131230815) {
                this.window?.statusBarColor = ContextCompat.getColor(this, R.color.colorOrange)
                actBar(true, "")
            } else {
                this.window?.statusBarColor = ContextCompat.getColor(this, R.color.purple_700)
            }
        }
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(ContextCompat.getColor(this, R.color.purple_500)))
    }

    override fun navigateToFlow(flow: NavigationFlow) {
        navigator.navigateToFlow(flow)
    }

    override fun actBar(isGone: Boolean, nameActBar: String) {

        supportActionBar?.title = nameActBar
        when {
            isGone -> {
                supportActionBar!!.hide()
            }
            else -> {
                supportActionBar!!.show()
            }
        }
    }

    override fun isDarkTheme(): Boolean {
        return resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }
}