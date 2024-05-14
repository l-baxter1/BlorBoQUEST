package com.zybooks.blorboquest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UpgradeOptionsViewModel : ViewModel() {

    private val _selectedUpgradeOption = MutableLiveData<UpgradeOption?>()
    val selectedUpgradeOption: LiveData<UpgradeOption?> = _selectedUpgradeOption

    fun selectUpgradeOption(upgradeOption: UpgradeOption) {
        _selectedUpgradeOption.value = upgradeOption
    }

    fun resetSelectedUpgradeOption() {
        _selectedUpgradeOption.value = null
    }
}