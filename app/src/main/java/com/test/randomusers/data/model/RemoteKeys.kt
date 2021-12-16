package com.test.randomusers.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteKeys(@PrimaryKey val id: String?, val prevKey: Int?, val nextKey: Int?)