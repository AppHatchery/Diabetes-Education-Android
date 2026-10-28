package edu.emory.diabetes.education.presentation.remoteconfig

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.emory.diabetes.education.data.remoteconfig.RemoteConfigRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RemoteConfigViewModel @Inject constructor(
    repository: RemoteConfigRepository
) : ViewModel() {
    val halloweenEnabled: StateFlow<Boolean> = repository.halloweenEnabled
    val isReady: StateFlow<Boolean> = repository.isReady
}
