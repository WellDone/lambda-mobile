package com.versilistyson.welldone.data.datasource.sensor

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.versilistyson.welldone.data.db.sensor.SensorDao
import com.versilistyson.welldone.data.db.sensor.SensorData
import com.versilistyson.welldone.test_util.builder.sensor.SensorDataTestBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SensorLocalDataSourceUnitTest {
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)
    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `Should send save a single sensor entity`() = testScope.runBlockingTest {
        // Setup
        val sensor = SensorDataTestBuilder.withDefaults().build()
        val sensorList = listOf(sensor)
        val mockSensorDao =
            mock<SensorDao> { onBlocking { saveAll(sensorList) } doReturn 1 }
        val sensorLocalDataSource = SensorLocalDataSourceImpl(mockSensorDao)
        val expected = 1
        val result: Int
        // Execute
        result = sensorLocalDataSource.saveSensors(sensorList)
        // Check
        verify(mockSensorDao).saveAll(sensorList)
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `Should save multiple Sensor Entities`() = testScope.runBlockingTest {
        // SETUP
        val sensor1 = SensorDataTestBuilder(1).build()
        val sensor2 = SensorDataTestBuilder(2).build()
        val sensor3 = SensorDataTestBuilder(3).build()
        val sensor4 = SensorDataTestBuilder(4).build()
        val sensorList = listOf(sensor1, sensor2, sensor3)
        val mockSensorDao = mock<SensorDao> {
            onBlocking { saveAll(sensorList) } doReturn 1
        }
        val sensorLocalDataSource = SensorLocalDataSourceImpl(mockSensorDao)
        val expected = 1
        val result: Int
        // Execute
        result = sensorLocalDataSource.saveSensors(sensorList)
        // Check
        verify(mockSensorDao).saveAll(sensorList)
        Assert.assertEquals(expected, result)


    }

    @Test
    fun `Should save Sensor Entities with null values correctly`() = testScope.runBlockingTest {
        // SETUP
        val sensorList = listOf(
            SensorDataTestBuilder.withNullSensorStatusAndPadData(1).build(),
            SensorDataTestBuilder.withNullSensorStatusAndPadData(2).build(),
            SensorDataTestBuilder.withNullSensorStatusAndPadData(3).build()
        )
        val mockSensorDao = mock<SensorDao> {
            onBlocking {saveAll(sensorList)} doReturn 1
        }
        val sensorLocalDataSource = SensorLocalDataSourceImpl(mockSensorDao)
        val result: Int
        val expected = 1
        // EXECUTE
        result = sensorLocalDataSource.saveSensors(sensorList)
        // CHECK
        verify(mockSensorDao).saveAll(sensorList)
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `Should get sensors`() = testScope.runBlockingTest {
        // SETUP
        val sensorList = listOf(
            SensorDataTestBuilder(sensorId = 1).build(),
            SensorDataTestBuilder(sensorId=2).build(),
            SensorDataTestBuilder(sensorId = 3).build()
        )
        val expected = flowOf(sensorList)
        val mockSensorDao = mock<SensorDao> {
            onBlocking {getAll()} doReturn expected
        }
        val sensorLocalDataSource = SensorLocalDataSourceImpl(mockSensorDao)
        val result: Flow<List<SensorData>>
        // EXECUTE
        result = sensorLocalDataSource.getSensors()
        // CHECK
        verify(mockSensorDao).getAll()
        Assert.assertEquals(expected, result)
    }
}
