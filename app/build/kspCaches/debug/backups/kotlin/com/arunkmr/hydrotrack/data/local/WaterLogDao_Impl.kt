package com.arunkmr.hydrotrack.`data`.local

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.arunkmr.hydrotrack.`data`.local.entity.WaterLogEntity
import javax.`annotation`.processing.Generated
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class WaterLogDao_Impl(
  __db: RoomDatabase,
) : WaterLogDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfWaterLogEntity: EntityInsertAdapter<WaterLogEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfWaterLogEntity = object : EntityInsertAdapter<WaterLogEntity>() {
      protected override fun createQuery(): String = "INSERT OR ABORT INTO `water_logs` (`id`,`timestampEpochMs`,`amountMl`) VALUES (nullif(?, 0),?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: WaterLogEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.timestampEpochMs)
        statement.bindLong(3, entity.amountMl.toLong())
      }
    }
  }

  public override suspend fun insert(log: WaterLogEntity): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfWaterLogEntity.insertAndReturnId(_connection, log)
    _result
  }

  public override fun getLogsForDay(dayStartMs: Long, dayEndMs: Long): Flow<List<WaterLogEntity>> {
    val _sql: String = "SELECT * FROM water_logs WHERE timestampEpochMs BETWEEN ? AND ? ORDER BY timestampEpochMs DESC"
    return createFlow(__db, false, arrayOf("water_logs")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, dayStartMs)
        _argIndex = 2
        _stmt.bindLong(_argIndex, dayEndMs)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTimestampEpochMs: Int = getColumnIndexOrThrow(_stmt, "timestampEpochMs")
        val _columnIndexOfAmountMl: Int = getColumnIndexOrThrow(_stmt, "amountMl")
        val _result: MutableList<WaterLogEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: WaterLogEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTimestampEpochMs: Long
          _tmpTimestampEpochMs = _stmt.getLong(_columnIndexOfTimestampEpochMs)
          val _tmpAmountMl: Int
          _tmpAmountMl = _stmt.getLong(_columnIndexOfAmountMl).toInt()
          _item = WaterLogEntity(_tmpId,_tmpTimestampEpochMs,_tmpAmountMl)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getTotalForDay(dayStartMs: Long, dayEndMs: Long): Flow<Int?> {
    val _sql: String = "SELECT SUM(amountMl) FROM water_logs WHERE timestampEpochMs BETWEEN ? AND ?"
    return createFlow(__db, false, arrayOf("water_logs")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, dayStartMs)
        _argIndex = 2
        _stmt.bindLong(_argIndex, dayEndMs)
        val _result: Int?
        if (_stmt.step()) {
          val _tmp: Int?
          if (_stmt.isNull(0)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(0).toInt()
          }
          _result = _tmp
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getLogsInRange(startMs: Long, endMs: Long): Flow<List<WaterLogEntity>> {
    val _sql: String = "SELECT * FROM water_logs WHERE timestampEpochMs BETWEEN ? AND ? ORDER BY timestampEpochMs DESC"
    return createFlow(__db, false, arrayOf("water_logs")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, startMs)
        _argIndex = 2
        _stmt.bindLong(_argIndex, endMs)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTimestampEpochMs: Int = getColumnIndexOrThrow(_stmt, "timestampEpochMs")
        val _columnIndexOfAmountMl: Int = getColumnIndexOrThrow(_stmt, "amountMl")
        val _result: MutableList<WaterLogEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: WaterLogEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTimestampEpochMs: Long
          _tmpTimestampEpochMs = _stmt.getLong(_columnIndexOfTimestampEpochMs)
          val _tmpAmountMl: Int
          _tmpAmountMl = _stmt.getLong(_columnIndexOfAmountMl).toInt()
          _item = WaterLogEntity(_tmpId,_tmpTimestampEpochMs,_tmpAmountMl)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getDailyTotals(startMs: Long, endMs: Long): Flow<List<DailyTotal>> {
    val _sql: String = "SELECT (timestampEpochMs / 86400000) as dateEpochDay, SUM(amountMl) as totalMl FROM water_logs WHERE timestampEpochMs BETWEEN ? AND ? GROUP BY dateEpochDay ORDER BY dateEpochDay"
    return createFlow(__db, false, arrayOf("water_logs")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, startMs)
        _argIndex = 2
        _stmt.bindLong(_argIndex, endMs)
        val _columnIndexOfDateEpochDay: Int = 0
        val _columnIndexOfTotalMl: Int = 1
        val _result: MutableList<DailyTotal> = mutableListOf()
        while (_stmt.step()) {
          val _item: DailyTotal
          val _tmpDateEpochDay: Long
          _tmpDateEpochDay = _stmt.getLong(_columnIndexOfDateEpochDay)
          val _tmpTotalMl: Int
          _tmpTotalMl = _stmt.getLong(_columnIndexOfTotalMl).toInt()
          _item = DailyTotal(_tmpDateEpochDay,_tmpTotalMl)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getLastLogTimestamp(): Long? {
    val _sql: String = "SELECT MAX(timestampEpochMs) FROM water_logs"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Long?
        if (_stmt.step()) {
          val _tmp: Long?
          if (_stmt.isNull(0)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(0)
          }
          _result = _tmp
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteLogById(id: Long) {
    val _sql: String = "DELETE FROM water_logs WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteAllLogs() {
    val _sql: String = "DELETE FROM water_logs"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
