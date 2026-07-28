package com.arunkmr.hydrotrack.`data`.local

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.arunkmr.hydrotrack.`data`.local.entity.SymptomLogEntity
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class SymptomLogDao_Impl(
  __db: RoomDatabase,
) : SymptomLogDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfSymptomLogEntity: EntityInsertAdapter<SymptomLogEntity>

  private val __updateAdapterOfSymptomLogEntity: EntityDeleteOrUpdateAdapter<SymptomLogEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfSymptomLogEntity = object : EntityInsertAdapter<SymptomLogEntity>() {
      protected override fun createQuery(): String = "INSERT OR ABORT INTO `symptom_logs` (`id`,`dateEpochDay`,`feltDizzy`,`drySkin`,`notes`) VALUES (nullif(?, 0),?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: SymptomLogEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.dateEpochDay)
        val _tmp: Int = if (entity.feltDizzy) 1 else 0
        statement.bindLong(3, _tmp.toLong())
        val _tmp_1: Int = if (entity.drySkin) 1 else 0
        statement.bindLong(4, _tmp_1.toLong())
        val _tmpNotes: String? = entity.notes
        if (_tmpNotes == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpNotes)
        }
      }
    }
    this.__updateAdapterOfSymptomLogEntity = object : EntityDeleteOrUpdateAdapter<SymptomLogEntity>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `symptom_logs` SET `id` = ?,`dateEpochDay` = ?,`feltDizzy` = ?,`drySkin` = ?,`notes` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: SymptomLogEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.dateEpochDay)
        val _tmp: Int = if (entity.feltDizzy) 1 else 0
        statement.bindLong(3, _tmp.toLong())
        val _tmp_1: Int = if (entity.drySkin) 1 else 0
        statement.bindLong(4, _tmp_1.toLong())
        val _tmpNotes: String? = entity.notes
        if (_tmpNotes == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpNotes)
        }
        statement.bindLong(6, entity.id)
      }
    }
  }

  public override suspend fun insert(log: SymptomLogEntity): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfSymptomLogEntity.insertAndReturnId(_connection, log)
    _result
  }

  public override suspend fun update(log: SymptomLogEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfSymptomLogEntity.handle(_connection, log)
  }

  public override fun getLogForDay(dateEpochDay: Long): Flow<SymptomLogEntity?> {
    val _sql: String = "SELECT * FROM symptom_logs WHERE dateEpochDay = ? LIMIT 1"
    return createFlow(__db, false, arrayOf("symptom_logs")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, dateEpochDay)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfDateEpochDay: Int = getColumnIndexOrThrow(_stmt, "dateEpochDay")
        val _columnIndexOfFeltDizzy: Int = getColumnIndexOrThrow(_stmt, "feltDizzy")
        val _columnIndexOfDrySkin: Int = getColumnIndexOrThrow(_stmt, "drySkin")
        val _columnIndexOfNotes: Int = getColumnIndexOrThrow(_stmt, "notes")
        val _result: SymptomLogEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpDateEpochDay: Long
          _tmpDateEpochDay = _stmt.getLong(_columnIndexOfDateEpochDay)
          val _tmpFeltDizzy: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfFeltDizzy).toInt()
          _tmpFeltDizzy = _tmp != 0
          val _tmpDrySkin: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfDrySkin).toInt()
          _tmpDrySkin = _tmp_1 != 0
          val _tmpNotes: String?
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes)
          }
          _result = SymptomLogEntity(_tmpId,_tmpDateEpochDay,_tmpFeltDizzy,_tmpDrySkin,_tmpNotes)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getLogsInRange(startDay: Long, endDay: Long): Flow<List<SymptomLogEntity>> {
    val _sql: String = "SELECT * FROM symptom_logs WHERE dateEpochDay BETWEEN ? AND ? ORDER BY dateEpochDay"
    return createFlow(__db, false, arrayOf("symptom_logs")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, startDay)
        _argIndex = 2
        _stmt.bindLong(_argIndex, endDay)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfDateEpochDay: Int = getColumnIndexOrThrow(_stmt, "dateEpochDay")
        val _columnIndexOfFeltDizzy: Int = getColumnIndexOrThrow(_stmt, "feltDizzy")
        val _columnIndexOfDrySkin: Int = getColumnIndexOrThrow(_stmt, "drySkin")
        val _columnIndexOfNotes: Int = getColumnIndexOrThrow(_stmt, "notes")
        val _result: MutableList<SymptomLogEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SymptomLogEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpDateEpochDay: Long
          _tmpDateEpochDay = _stmt.getLong(_columnIndexOfDateEpochDay)
          val _tmpFeltDizzy: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfFeltDizzy).toInt()
          _tmpFeltDizzy = _tmp != 0
          val _tmpDrySkin: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfDrySkin).toInt()
          _tmpDrySkin = _tmp_1 != 0
          val _tmpNotes: String?
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes)
          }
          _item = SymptomLogEntity(_tmpId,_tmpDateEpochDay,_tmpFeltDizzy,_tmpDrySkin,_tmpNotes)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteAllSymptoms() {
    val _sql: String = "DELETE FROM symptom_logs"
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
