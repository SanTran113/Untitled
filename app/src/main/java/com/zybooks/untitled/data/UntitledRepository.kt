package com.zybooks.untitled.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UntitledRepository (context: Context) {
    private val databaseCallback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            CoroutineScope(Dispatchers.IO).launch {
                addStarterData()
            }
        }
    }

    private val database: UntitledDatabase = Room.databaseBuilder(
        context,
        UntitledDatabase::class.java,
        "untitled.db"
    )
        .addCallback(databaseCallback)
        .build()

    private val galaxyDao = database.galaxyDao()
    private val worldDao = database.worldDao()
    private val storyDao = database.storyDao()
    private val chapterDao = database.chapterDao()


//  ------------------------------- GALAXY CALLS -------------------------------
    fun getGalaxy(galaxy: Galaxy) = galaxyDao.updateGalaxy(galaxy)

//  ------------------------------- WORLD CALLS -------------------------------
    fun getWorld(id: Long) = worldDao.getWorld(id)

    fun getAllWorlds() = worldDao.getAllWorlds()

    fun addWorld(world: World) {
        if (world.worldName.trim() != "") {
            CoroutineScope(Dispatchers.IO).launch {
                world.worldId = worldDao.addWorld(world)
            }
        }
    }

    fun updateWorld(world: World) {
        CoroutineScope(Dispatchers.IO).launch {
            worldDao.updateWorld(world)
        }
    }

    fun deleteWorld(world: World) {
        CoroutineScope(Dispatchers.IO).launch {
            worldDao.deleteWorld(world)
        }
    }

//  ------------------------------- STORY CALLS -------------------------------
    fun getStory(id: Long) = storyDao.getStory(id)

    fun getAllStoriesFromWorldId(worldid: Long) = storyDao.getAllStoriesFromWorldId(worldid)

    fun addStory(story: Story) {
        if (story.storyName.trim() != "") {
            CoroutineScope(Dispatchers.IO).launch {
                story.storyId = storyDao.addStory(story)
            }
        }
    }

    fun updateStory(story: Story) {
        CoroutineScope(Dispatchers.IO).launch {
            storyDao.updateStory(story)
        }
    }

    fun deleteStory(story: Story) {
        CoroutineScope(Dispatchers.IO).launch {
            storyDao.deleteStory(story)
        }
    }

//  ------------------------------- CHAPTER CALLS -------------------------------
    fun getChapter(id: Long) = chapterDao.getChapter(id)

    fun getAllChaptersFromStoryId(id: Long) = chapterDao.getAllChaptersFromStoryId(id)

    fun addChapter(chapter: Chapter) {
        if (chapter.chapterName.trim() != "") {
            CoroutineScope(Dispatchers.IO).launch {
                chapter.chapterId = chapterDao.addChapter(chapter)
            }
        }
    }

    fun updateChapter(chapter: Chapter) {
        CoroutineScope(Dispatchers.IO).launch {
            chapterDao.updateChapter(chapter)
        }
    }

    fun deleteStory(chapter: Chapter) {
        CoroutineScope(Dispatchers.IO).launch {
            chapterDao.deleteChapter(chapter)
        }
    }

//  ------------------------------- STARTER DATA -------------------------------

    private fun addStarterData() {
        var galaxyId = galaxyDao.addGalaxy(Galaxy(galaxyName = "HSR"))
        worldDao.addWorld(World(worldName = "Herta Space Station", imageId = 0))
        worldDao.addWorld(World(worldName = "Amphoreus", imageId = 1))
        worldDao.addWorld(World(worldName = "Penacony", imageId = 2))
        worldDao.addWorld(World(worldName = "Xian Zhuo"))
        storyDao.addStory(
            Story(
                storyName = "Tribbie",
                synopsis = "This is the story of the Tribos",
                scratchPad = "",
                worldId = 1
            )
        )
        storyDao.addStory(
            Story(
                storyName = "Aglaea",
                synopsis = "This is the story of Aglaea",
                scratchPad = "",
                worldId = 1
            )
        )
        storyDao.addStory(
            Story(
                storyName = "Phainon",
                synopsis = "This is the story of Mydei",
                scratchPad = "",
                worldId = 1
            )
        )
        storyDao.addStory(
            Story(
                storyName = "Sunday",
                synopsis = "This is the story of Sunday",
                scratchPad = "",
                worldId = 2
            )
        )
        chapterDao.addChapter(
            Chapter(
                chapterName = "Guiding Light",
                chapterBody = "One..Two..Three..Children?",
                storyId = 0
            )
        )
        chapterDao.addChapter(
            Chapter(
                chapterName = "Laughter and Lament",
                chapterBody = "Not only do the three children share each other's senses, " +
                        "their souls are also interconnected",
                storyId = 0
            )
        )
    }

}