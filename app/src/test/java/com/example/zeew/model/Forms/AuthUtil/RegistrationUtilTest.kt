package com.example.zeew.model.Forms.AuthUtil

import com.example.zeew.model.Forms.RegistrationForm
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegistrationUtilTest{

    @Test
    fun `validate registration Form`(){
        var registrationForm=RegistrationForm(
                "dd",
                "dd",
                "ff",
                "sdf",
                "32131",
                "2",
                "sds",
                "12")
        assertThat(RegistrationUtil().validateRegistrationForm(registrationForm)).isTrue()
    }
    @Test
    fun `validate empty fields Form`(){
        var registrationForm=RegistrationForm(
                "dd",
                "dd",
                "ff",
                "sdf",
                "32131",
                "2",
                "",
                "")
        assertThat(RegistrationUtil().isEmptyException(registrationForm)).isEqualTo("")
    }
    @Test
    fun `validate confirmation password Form`(){
        var registrationForm=RegistrationForm(
                "dd",
                "dd",
                "ff",
                "sdf",
                "32131",
                "2",
                "",
                "2")
        assertThat(RegistrationUtil().confirmationPasswordException(registrationForm)).isEqualTo("")
    }
}