package com.versilistyson.welldone.data.db.log

import androidx.room.*
import com.versilistyson.welldone.data.db.sensor.SensorData

@Entity(
    tableName = "log_table",
    foreignKeys = [ForeignKey(
        entity = SensorData::class,
        parentColumns = ["sensor_id"],
        childColumns = ["sensor_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["log_id"], unique = true)]
)
class LogData(
    @ColumnInfo(name = "log_id") @PrimaryKey(autoGenerate = false) val logId: Long,
    @ColumnInfo(name = "sensor_id") val sensorId: Long,
    @ColumnInfo(name = "date_filed") val dateFiled: String,
    @ColumnInfo(name = "last_modified") val lastModified: String,
    @ColumnInfo(name = "status") val status: Int,
    @ColumnInfo(name = "comment") val comment: String,
    //TODO: use type converter for list<string> later
    @ColumnInfo(name = "pictures") val pictures: String,
    @ColumnInfo(name = "operator_id") val operatorId: Long
)