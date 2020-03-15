package com.me.data.weather.datasource.local.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.EmptyResultSetException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.me.data.WeatherLogFactory
import com.me.data.base.AppDatabase
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class IWeatherDaoTest {

    private lateinit var weatherDao: IWeatherDao
    private lateinit var db: AppDatabase

    @Rule
    @JvmField
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()


    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).allowMainThreadQueries().build()
        weatherDao = db.getWeatherDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertWeatherLogsThenReturnTheInsertedWeatherLogs() {
        val cachedList = WeatherLogFactory.Factory.makeWeatherLogDataList(5).sortedBy { it.dateCreated }

        weatherDao.saveAllWeatherLogs(cachedList)

        val retrievedList = weatherDao.getAllWeatherLogs().blockingFirst().sortedBy { it.dateCreated }

        assertEquals(retrievedList, cachedList)
    }

    @Test
    fun insertTwoListsWithSameIdsThenReturnTheNewlyInsertedList() {
        val cachedList1 = WeatherLogFactory.Factory.makeWeatherLogDataList(5).sortedBy { it.dateCreated }

        val cachedList2 = WeatherLogFactory.Factory.makeWeatherDataListSameIdsDiffCont(cachedList1).sortedBy { it.dateCreated }


        weatherDao.saveAllWeatherLogs(cachedList1)
        weatherDao.saveAllWeatherLogs(cachedList2)

        assertNotEquals(cachedList1, cachedList2)

        val retrievedList = weatherDao.getAllWeatherLogs().blockingFirst().sortedBy { it.dateCreated }

        assertNotEquals(retrievedList, cachedList1)

        assertEquals(retrievedList, cachedList2)
    }

    @Test
    fun updateWeatherLogThenReturnTheUpdatedWeatherLog() {
        val cachedItem = WeatherLogFactory.Factory.makeWeatherLogData()
        val updatedCachedItem = WeatherLogFactory.Factory.makeWeatherLogData(cachedItem.dateCreated)

        weatherDao.saveWeatherLog(cachedItem)
        weatherDao.updateWeatherLog(updatedCachedItem).blockingAwait()

        val test = weatherDao.getWeatherLog(cachedItem.dateCreated).test()

        test.assertValue(updatedCachedItem)
        test.assertValueCount(1)
        test.assertNoErrors()
    }

    @Test
    fun removeWeatherLogThenReturnNotReturnTheRemovedWeatherLog() {
        val cachedItem = WeatherLogFactory.Factory.makeWeatherLogData()
        weatherDao.saveWeatherLog(cachedItem)
        weatherDao.deleteWeatherLog(cachedItem.dateCreated).blockingAwait()

        val test = weatherDao.getWeatherLog(cachedItem.dateCreated).test()

        test.assertNoValues()
        test.assertValueCount(0)
        test.assertError(EmptyResultSetException::class.java)
    }
}