package com.versilistyson.welldone.data.db.sensor

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

sealed class SensorData {
    @Entity(tableName = "sensor_table")
    data class Sensor(
        @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = false) val id: Int,

        @ColumnInfo(name = "sensor_id") val  sensorId: Int,
        @ColumnInfo(name = "status") val sensorStatus: Int?,
        @ColumnInfo(name = "last_upload_date") val lastUploadDate: Int?,


    )
}