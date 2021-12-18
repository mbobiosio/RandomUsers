package com.test.randomusers.data.local.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.squareup.moshi.Types
import com.test.randomusers.data.model.User
import com.test.randomusers.utils.TestUtil.parseJsonFileToObject
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest : BaseDaoTest() {

    @Test
    fun `verify that contact insertion was successful`() = runBlocking {
        val type = Types.newParameterizedType(List::class.java, User::class.java)
        val users = parseJsonFileToObject<List<User>>("sample-user.json", type)!!
        db.userDao().insertAllUsers(users)
        Assert.assertEquals(1, db.userDao().getAllUsers()?.size)
    }

    @Test
    fun `verify that contact query was successful`() = runBlocking {
        val type = Types.newParameterizedType(List::class.java, User::class.java)
        val users = parseJsonFileToObject<List<User>>("sample-user.json", type)!!
        db.userDao().insertAllUsers(users)
        val queriedUser = db.userDao().getAllUsers()?.first()
        Assert.assertEquals("Jessika", queriedUser?.name?.first)
    }
}