package com.example.challengeroom1anggita_eka.room

import androidx.room.*


@Dao
interface tbsiswaDao {

    @Insert
    suspend fun addtbsiswa(tbsis: tbsiswa)

    @Update
    suspend fun updatetbsiswa(tbsis: tbsiswa)

    @Delete
    suspend fun deletetbsiswa(tbsis: tbsiswa)

    @Query("SELECT * FROM tbsiswa")
    suspend fun tampilsemua(): List<tbsiswa>

    @Query("SELECT * FROM tbsiswa WHERE nis=:nis_id")
    suspend fun tampilid(nis_id: Int): List<tbsiswa>
}