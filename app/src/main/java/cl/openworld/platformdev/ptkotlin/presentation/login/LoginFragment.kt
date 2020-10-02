package cl.openworld.platformdev.ptkotlin.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import cl.openworld.platformdev.ptkotlin.databinding.LoginFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)


    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = LoginFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.loginButton.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()
            uiScope.launch {
                val validation = viewModel.doLogin(email, password)
                if (validation) {


                    findNavController().navigate(
                        LoginFragmentDirections.actionLoginFragmentToHomeMapsFragment(
                            viewModel.userProfile
                        )
                    )
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(context, "Datos incorrectos", Toast.LENGTH_LONG).show()
                }

            }

        }
        return binding.root


    }


}