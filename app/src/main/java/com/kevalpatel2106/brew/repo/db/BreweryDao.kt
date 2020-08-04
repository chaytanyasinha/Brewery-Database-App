package com.kevalpatel2106.brew.repo.db

import androidx.paging.PagingSource
import androidx.room.*
import com.kevalpatel2106.brew.repo.dto.BreweryDto
import io.reactivex.Single
import java.io.IOException
import com.kevalpatel2106.brew.repo.db.BreweryTableInfo as TableInfo

@Dao
interface BreweryDao {

    @Throws(IOException::class)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(dto: List<BreweryDto>)

    @Query("SELECT * FROM ${TableInfo.TABLE_NAME} ORDER BY ${TableInfo.NAME} ASC")
    fun observeAll(): PagingSource<Int, BreweryDto>

    @Throws(IOException::class)
    @Query("DELETE FROM ${TableInfo.TABLE_NAME}")
    fun deleteAll()

    @Query("SELECT COUNT(*) FROM ${TableInfo.TABLE_NAME}")
    fun getCount(): Single<Int>

    @Transaction
    fun insertAllAfterDeleteAll(dto: List<BreweryDto>) {
        deleteAll()
        insertAll(dto)
    }
}
