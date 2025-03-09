package com.zybooks.untitled.ui

import androidx.lifecycle.ViewModel
import com.zybooks.untitled.data.Story
import com.zybooks.untitled.data.StoryDataSource

class StoryViewModel : ViewModel() {
    fun getStory(id: Int): Story = StoryDataSource().getStory(id) ?: Story()
}