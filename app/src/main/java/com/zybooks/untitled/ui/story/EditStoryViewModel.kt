package com.zybooks.untitled.ui.story

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.toRoute
import com.zybooks.untitled.UntitledApplication
import com.zybooks.untitled.data.Story
import com.zybooks.untitled.data.UntitledRepository
import com.zybooks.untitled.ui.Routes
import kotlinx.coroutines.launch

class EditStoryViewModel(
    savedStateHandle: SavedStateHandle,
    private val untitledRepository: UntitledRepository
) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as UntitledApplication)
                EditStoryViewModel(this.createSavedStateHandle(), application.untitledRepository)
            }
        }
    }

//    private val storyId: Long = savedStateHandle.toRoute<Routes.EditStory>().questionId
//
//    var story by mutableStateOf(Story(0))
//        private set
//
//    fun changeQuestion(ques: Question) {
//        question = ques
//    }
//
//    fun updateQuestion() {
//        studyRepo.updateQuestion(question)
//    }
//
//    init {
//        viewModelScope.launch {
//            question = studyRepo.getQuestion(questionId).filterNotNull().first()
//        }
//    }


}