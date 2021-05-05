package com.example.zeew.vvm.Destination

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.zeew.R
import com.example.zeew.Utils.Const.Companion.USER_TOKEN_KEY
import com.example.zeew.Utils.PreferenceManager
import com.example.zeew.databinding.FragmentRegistrationBinding
import com.example.zeew.vvm.vm.RegistrationVM


class RegistrationFragment : Fragment() {
    var _binding:FragmentRegistrationBinding?=null
    val binding get() = _binding!!
    var vm: RegistrationVM?=null
    var preferenceManager: PreferenceManager?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceManager= PreferenceManager(requireContext())

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
        userHandler()
    }

    fun userHandler(){
        onClickHandler()
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

        if(firstName.isNullOrEmpty()||lastName.isNullOrEmpty()||email.isNullOrEmpty()||phone.isNullOrEmpty()||password.isNullOrEmpty()||confirmationPass.isNullOrEmpty()||referral.isNullOrEmpty()){
            Toast.makeText(requireContext(), "Please fill the empty fields", Toast.LENGTH_SHORT).show()
        }else{
            if (password==confirmationPass){
//            Api Hit
                vm!!.userSignUp(firstName,lastName,email,phone,password,referral)
                vm!!.retrievalData.observe(viewLifecycleOwner, {
                    Toast.makeText(requireContext(), it.getAsJsonObject("result").get("message").toString(), Toast.LENGTH_SHORT).show()
                    if(it.getAsJsonObject("result").get("success").asInt==1){
                        var token=it.getAsJsonObject("result").get("token").toString()
                        Log.e("RegisFragment",token)
                        preferenceManager!!.putString(USER_TOKEN_KEY,token)
                        var action=RegistrationFragmentDirections.actionFromRegistrationToHome()
                        Navigation.findNavController(requireView()).navigate(action)
                    }

                })
            }else{
                Toast.makeText(requireContext(), "Confirmation password should be identical", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
        vm=null
        preferenceManager=null
    }
}