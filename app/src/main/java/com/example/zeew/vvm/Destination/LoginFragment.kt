package com.example.zeew.vvm.Destination

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.zeew.Network.ZeewFireBaseAuth
import com.example.zeew.R
import com.example.zeew.databinding.FragmentLoginBinding
import com.example.zeew.model.Forms.LoginForm
import com.example.zeew.vvm.vm.LoginVM
import com.example.zeew.vvm.vm.RegistrationVM

class LoginFragment : Fragment() {
    var _binding: FragmentLoginBinding?=null
    val binding get() = _binding!!
    var vm: LoginVM?=null
    var firebaseGoogleAuth:ZeewFireBaseAuth?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm= ViewModelProviders.of(this).get(LoginVM::class.java)
        userHandler()
    }

    fun userHandler(){
        onClickHandler()
    }
    fun loginIn(){
        var email=binding.fLoginFragmentTietEmailAddress.text.toString()
        var password=binding.fLoginFragmentTietPassword.text.toString()
        if(email.isNullOrEmpty()||password.isNullOrEmpty()){
            Toast.makeText(requireContext(), "Please fill the empty fields", Toast.LENGTH_SHORT).show()
        }else{
//            Api Hit
            vm!!.userLogIn(email,password,"FCM_Token")
            vm!!.retrievalData.observe(viewLifecycleOwner, Observer {
                var message=it.getAsJsonObject("result").get("message").toString()
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                if (message.contains("successfully")){
                    var action=LoginFragmentDirections.actionFromLoginToHome()
                    Navigation.findNavController(requireView()).navigate(action)
                }
            })
        }
    }
    fun onClickHandler(){
        binding.LoginFragmentLoginWithGoogleBtn.setOnClickListener {
            googleAuth()
        }
        binding.LoginFragmentLoginBtn.setOnClickListener {
            loginIn()
        }
        binding.LoginFragmentSignUpBtn.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_from_Login_to_registration)
        }
    }
    fun googleAuth(){
        firebaseGoogleAuth= ZeewFireBaseAuth(this)
        firebaseGoogleAuth!!.signInGoogleForClick()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        firebaseGoogleAuth!!.onActivityResult(requestCode,resultCode,data)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
        firebaseGoogleAuth=null
    }
}