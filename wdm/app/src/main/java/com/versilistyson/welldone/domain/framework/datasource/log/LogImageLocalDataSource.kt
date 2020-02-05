package com.versilistyson.welldone.domain.framework.datasource.log

import com.versilistyson.welldone.data.db.log.LogImageData
import com.versilistyson.welldone.data.util.StoreKey
import com.versilistyson.welldone.domain.framework.datasource.BaseDataSource
import kotlinx.coroutines.flow.Flow

interface LogImageLocalDataSource: BaseDataSource {
    fun getLogImages(key: StoreKey.LogImageKey, logImageId: Long): Flow<List<LogImageData>>
    suspend fun saveLogImages(key: StoreKey.LogImageKey, logImages: List<LogImageData>)
}