package com.example.auth.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.auth.databinding.FragmentAuthBinding
import com.example.navigations.NavigationFlow
import com.example.navigations.ToFlowNavigatable
import com.example.utils.ViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import com.example.utils.toast

@AndroidEntryPoint
class AuthFragment : ViewBindingFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {

    private val viewModel: AuthViewModel by viewModels()

    private var resultAuth =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data: Intent? = result.data
            if (result.resultCode == Activity.RESULT_OK && data != null) {
                val tokenExchangeRequest = AuthorizationResponse.fromIntent(result.data!!)
                    ?.createTokenExchangeRequest()
                val exception = AuthorizationException.fromIntent(data)

                when {
                    tokenExchangeRequest != null && exception == null ->
                        viewModel.onAuthCodeReceived(tokenExchangeRequest)
                    exception != null -> viewModel.onAuthCodeFailed(exception)
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.isFirstStart()) {
            findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToOnboardingMainFragment())
        }
        viewModel.isHaveToken()
        bindViewModel()
    }

    private fun bindViewModel() {
        binding.buttonLoginStrava.setOnClickListener { viewModel.openLoginPage(requireContext()) }
        viewModel.loadingLiveData.observe(viewLifecycleOwner, ::updateIsLoading)
        viewModel.openAuthPageLiveData.observe(viewLifecycleOwner, ::openAuthPage)
        viewModel.toastLiveData.observe(viewLifecycleOwner, ::toast)
        viewModel.isHaveToken.observe(viewLifecycleOwner) {
            if (it) {
                (requireActivity() as ToFlowNavigatable).navigateToFlow(NavigationFlow.MainFrag)
            }
        }
        viewModel.authSuccessLiveData.observe(viewLifecycleOwner) {
            (requireActivity() as ToFlowNavigatable).navigateToFlow(NavigationFlow.MainFrag)
        }
    }

    private fun updateIsLoading(isLoading: Boolean) {
        binding.buttonLoginStrava.isVisible = !isLoading
        binding.loginProgress.isVisible = isLoading
    }

    private fun openAuthPage(intent: Intent) {
        resultAuth.launch(intent)
    }
}