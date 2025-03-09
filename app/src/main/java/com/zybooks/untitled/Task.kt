package com.zybooks.untitled

import java.util.UUID

data class Task (
   var id: UUID = UUID.randomUUID(),
   var body: String = "",
   var completed: Boolean = false
)