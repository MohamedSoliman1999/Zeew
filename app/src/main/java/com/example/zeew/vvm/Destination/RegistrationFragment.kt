package com.example.zeew.vvm.Destination

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.zeew.R
import com.example.zeew.Utils.Const
import com.example.zeew.Utils.DataStorePreferenceManager
import com.example.zeew.databinding.FragmentRegistrationBinding
import com.example.zeew.model.Forms.AuthUtil.RegistrationUtil
import com.example.zeew.model.Forms.RegistrationForm
import com.example.zeew.vvm.vm.RegistrationVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistrationFragment : Fragment() {
    var _binding:FragmentRegistrationBinding?=null
    val binding get() = _binding!!
    var vm: RegistrationVM?=null
    var dataStorePreferenceManager: DataStorePreferenceManager?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataStorePreferenceManager= DataStorePreferenceManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= DataBindingUtil.inflate(inflater, R.layout.fragment_registration, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm= ViewModelProviders.of(this).get(RegistrationVM::class.java)
//        vm!!.repository.getFireBaseInstance().setCurrentFragment(requireActivity())
        userHandler()
    }

    fun userHandler(){
        onClickHandler()
        observation()
    }
    fun onClickHandler(){
        binding.registrationFragmentJoinUsBtn.setOnClickListener {
            signUp()
        }
        binding.registrationFragmentBackBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
    fun signUp(){
        var firstName=binding.fRegistrationTietFirstName.text.toString()
        var lastName=binding.fRegistrationTietLastName.text.toString()
        var email=binding.fRegistrationTietEmailAddress.text.toString()
        var phone=binding.fRegistrationTietPhone.text.toString()
        var password=binding.fRegistrationTietPassword.text.toString()
        var confirmationPass=binding.fRegistrationTietPasswordConfirmation.text.toString()
        var referral=binding.fRegistrationTietReferralCodes.text.toString()

            val signUpForm = RegistrationForm(action = "CustomerSignUp",first_name = firstName,last_name = lastName, username = email,phone_number = "+2$phone",password =  password,referral_code = referral,confirmPassword = confirmationPass)
            var registrationUtil =RegistrationUtil(requireContext())
            if(registrationUtil.validateRegistrationForm(signUpForm)){
                vm!!.userSignUp(signUpForm)
            }
    }
    fun observation(){
        vm!!.retrievalData.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it.getAsJsonObject("result").get("message").toString(), Toast.LENGTH_SHORT).show()
            if(it.getAsJsonObject("result").get("success").asInt==1){
                var token=it.getAsJsonObject("result").get("token").toString()
                Log.e("RegisFragment",token)
                lifecycleScope.launch {
                    vm!!.repository.getDataStore().saveString(Const.USER_TOKEN_KEY,token)
                    vm!!.repository.getDataStore().saveBoolean(Const.IS_Auth_KEY,true)
                }
                var action=RegistrationFragmentDirections.actionFromRegistrationToHome()
                Navigation.findNavController(requireView()).navigate(action)
            }
        })
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
        vm=null
        dataStorePreferenceManager=null
    }
}