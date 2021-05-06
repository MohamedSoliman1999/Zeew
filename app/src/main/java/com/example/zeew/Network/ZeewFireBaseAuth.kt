package com.example.zeew.Network

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.zeew.MainActivity
import com.example.zeew.R
import com.example.zeew.ZeewApp
import com.example.zeew.vvm.observer.OnActivityResultCallBack
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import javax.inject.Inject

class ZeewFireBaseAuth: OnActivityResultCallBack {
    private lateinit var fragment: Fragment
    lateinit var activity: Activity
    private lateinit var googleSignInClient: GoogleSignInClient
    lateinit var firebaseAuth: FirebaseAuth
    private lateinit var fbUser: FirebaseUser
    private val RC_SIGN_IN = 123
    @Inject
    constructor()
    constructor(f: Fragment) {
//        fragment=f
//        authenticationRequest()
    }
    fun setCurrentFragment(a:Activity){
        activity=a
        this.fragment =(activity as MainActivity).getCurrentFragment()
        authenticationRequest()
    }
    //google auth
    private fun authenticationRequest(){
        // auth instance
        firebaseAuth= FirebaseAuth.getInstance()
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        // [END config_signin]
        googleSignInClient = GoogleSignIn.getClient(fragment!!.requireActivity(), gso)

    }
    fun signInGoogleForClick() {
        //FirebaseAuth.getInstance().signOut()
        val signInIntent = googleSignInClient.signInIntent
        fragment.startActivityForResult(signInIntent, RC_SIGN_IN)
//        if (firebaseAuth.currentUser!=null){
//            Navigation.findNavController(fragment.view!!).navigate(R.id.action_from_LoginContainer_to_home)
//        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        // [START_EXCLUDE silent]
        // showProgressBar()
        // [END_EXCLUDE]
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "signInWithCredential:success")
                    val user = firebaseAuth.currentUser
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "signInWithCredential:failure", task.exception)
                    // [START_EXCLUDE]
                    //   val view = binding.mainLayout
                    // [END_EXCLUDE]
                    Snackbar.make(
                        fragment.requireView(),
                        "Authentication Failed.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    // updateUI(null)
                }

                // [START_EXCLUDE]
                //hideProgressBar()
                // [END_EXCLUDE]
            }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.e("ActivityResult","Done")
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(ContentValues.TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
                if (firebaseAuth.currentUser !== null) {
                    fbUser = firebaseAuth.currentUser!!
                }
                Navigation.findNavController(fragment.requireView()).navigate(R.id.action_from_Login_to_home)
            }catch (e:Exception){
                Log.e("FirebaseAuth",e.message.toString())
                Toast.makeText(ZeewApp.getINSTANCE().applicationContext, "Error: No Authorization", Toast.LENGTH_SHORT).show()
            }
        }
    }

}