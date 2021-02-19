package org.ieseljust.pmdm.places.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutViewModel : ViewModel() {

    // Definim els valors privats _maintainer i _currentVersion,
    // com a MutableLiveData, així com les propietats observables
    // equivalents maintainer i _currentVersions
    private val _maintainer = MutableLiveData<String>().apply {
        value = "Artur Badenes"
    }
    val maintainer: LiveData<String> = _maintainer

    private val _currentVersion = MutableLiveData<String>().apply {
        value = "2.0"
    }
    val currentVersion: LiveData<String> = _currentVersion
}