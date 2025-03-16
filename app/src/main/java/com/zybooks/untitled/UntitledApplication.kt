package com.zybooks.untitled

import android.app.Application
import com.zybooks.untitled.data.UntitledRepository

class UntitledApplication: Application() {
   // Needed to create ViewModels with the ViewModelProvider.Factory
   lateinit var untitledRepository: UntitledRepository

   // For onCreate() to run, android:name=".StudyHelperApplication" must
   // be added to <application> in AndroidManifest.xml
   override fun onCreate() {
      super.onCreate()
      untitledRepository = UntitledRepository(this.applicationContext)
   }
}