package com.arunkmr.hydrotrack.`data`.local

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.arunkmr.hydrotrack.`data`.local.entity.UserSettingsEntity
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.Float
import kotlin.Int
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class SettingsDao_Impl(
  __db: RoomDatabase,
) : SettingsDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfUserSettingsEntity: EntityInsertAdapter<UserSettingsEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfUserSettingsEntity = object : EntityInsertAdapter<UserSettingsEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `user_settings` (`id`,`weightKg`,`bottleSizeMl`,`calculatedTargetMl`,`manualTargetMl`,`useManualOverride`,`reminderIntervalMinutes`,`sleepStartHour`,`sleepEndHour`,`onboardingCompleted`,`themeMode`,`userEmail`,`userName`,`isSignedIn`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: UserSettingsEntity) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindDouble(2, entity.weightKg.toDouble())
        statement.bindLong(3, entity.bottleSizeMl.toLong())
        statement.bindLong(4, entity.calculatedTargetMl.toLong())
        val _tmpManualTargetMl: Int? = entity.manualTargetMl
        if (_tmpManualTargetMl == null) {
          statement.bindNull(5)
        } else {
          statement.bindLong(5, _tmpManualTargetMl.toLong())
        }
        val _tmp: Int = if (entity.useManualOverride) 1 else 0
        statement.bindLong(6, _tmp.toLong())
        statement.bindLong(7, entity.reminderIntervalMinutes.toLong())
        statement.bindLong(8, entity.sleepStartHour.toLong())
        statement.bindLong(9, entity.sleepEndHour.toLong())
        val _tmp_1: Int = if (entity.onboardingCompleted) 1 else 0
        statement.bindLong(10, _tmp_1.toLong())
        statement.bindText(11, entity.themeMode)
        val _tmpUserEmail: String? = entity.userEmail
        if (_tmpUserEmail == null) {
          statement.bindNull(12)
        } else {
          statement.bindText(12, _tmpUserEmail)
        }
        val _tmpUserName: String? = entity.userName
        if (_tmpUserName == null) {
          statement.bindNull(13)
        } else {
          statement.bindText(13, _tmpUserName)
        }
        val _tmp_2: Int = if (entity.isSignedIn) 1 else 0
        statement.bindLong(14, _tmp_2.toLong())
      }
    }
  }

  public override suspend fun insertOrUpdate(settings: UserSettingsEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfUserSettingsEntity.insert(_connection, settings)
  }

  public override fun getSettings(): Flow<UserSettingsEntity?> {
    val _sql: String = "SELECT * FROM user_settings WHERE id = 1"
    return createFlow(__db, false, arrayOf("user_settings")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfWeightKg: Int = getColumnIndexOrThrow(_stmt, "weightKg")
        val _columnIndexOfBottleSizeMl: Int = getColumnIndexOrThrow(_stmt, "bottleSizeMl")
        val _columnIndexOfCalculatedTargetMl: Int = getColumnIndexOrThrow(_stmt, "calculatedTargetMl")
        val _columnIndexOfManualTargetMl: Int = getColumnIndexOrThrow(_stmt, "manualTargetMl")
        val _columnIndexOfUseManualOverride: Int = getColumnIndexOrThrow(_stmt, "useManualOverride")
        val _columnIndexOfReminderIntervalMinutes: Int = getColumnIndexOrThrow(_stmt, "reminderIntervalMinutes")
        val _columnIndexOfSleepStartHour: Int = getColumnIndexOrThrow(_stmt, "sleepStartHour")
        val _columnIndexOfSleepEndHour: Int = getColumnIndexOrThrow(_stmt, "sleepEndHour")
        val _columnIndexOfOnboardingCompleted: Int = getColumnIndexOrThrow(_stmt, "onboardingCompleted")
        val _columnIndexOfThemeMode: Int = getColumnIndexOrThrow(_stmt, "themeMode")
        val _columnIndexOfUserEmail: Int = getColumnIndexOrThrow(_stmt, "userEmail")
        val _columnIndexOfUserName: Int = getColumnIndexOrThrow(_stmt, "userName")
        val _columnIndexOfIsSignedIn: Int = getColumnIndexOrThrow(_stmt, "isSignedIn")
        val _result: UserSettingsEntity?
        if (_stmt.step()) {
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpWeightKg: Float
          _tmpWeightKg = _stmt.getDouble(_columnIndexOfWeightKg).toFloat()
          val _tmpBottleSizeMl: Int
          _tmpBottleSizeMl = _stmt.getLong(_columnIndexOfBottleSizeMl).toInt()
          val _tmpCalculatedTargetMl: Int
          _tmpCalculatedTargetMl = _stmt.getLong(_columnIndexOfCalculatedTargetMl).toInt()
          val _tmpManualTargetMl: Int?
          if (_stmt.isNull(_columnIndexOfManualTargetMl)) {
            _tmpManualTargetMl = null
          } else {
            _tmpManualTargetMl = _stmt.getLong(_columnIndexOfManualTargetMl).toInt()
          }
          val _tmpUseManualOverride: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfUseManualOverride).toInt()
          _tmpUseManualOverride = _tmp != 0
          val _tmpReminderIntervalMinutes: Int
          _tmpReminderIntervalMinutes = _stmt.getLong(_columnIndexOfReminderIntervalMinutes).toInt()
          val _tmpSleepStartHour: Int
          _tmpSleepStartHour = _stmt.getLong(_columnIndexOfSleepStartHour).toInt()
          val _tmpSleepEndHour: Int
          _tmpSleepEndHour = _stmt.getLong(_columnIndexOfSleepEndHour).toInt()
          val _tmpOnboardingCompleted: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfOnboardingCompleted).toInt()
          _tmpOnboardingCompleted = _tmp_1 != 0
          val _tmpThemeMode: String
          _tmpThemeMode = _stmt.getText(_columnIndexOfThemeMode)
          val _tmpUserEmail: String?
          if (_stmt.isNull(_columnIndexOfUserEmail)) {
            _tmpUserEmail = null
          } else {
            _tmpUserEmail = _stmt.getText(_columnIndexOfUserEmail)
          }
          val _tmpUserName: String?
          if (_stmt.isNull(_columnIndexOfUserName)) {
            _tmpUserName = null
          } else {
            _tmpUserName = _stmt.getText(_columnIndexOfUserName)
          }
          val _tmpIsSignedIn: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsSignedIn).toInt()
          _tmpIsSignedIn = _tmp_2 != 0
          _result = UserSettingsEntity(_tmpId,_tmpWeightKg,_tmpBottleSizeMl,_tmpCalculatedTargetMl,_tmpManualTargetMl,_tmpUseManualOverride,_tmpReminderIntervalMinutes,_tmpSleepStartHour,_tmpSleepEndHour,_tmpOnboardingCompleted,_tmpThemeMode,_tmpUserEmail,_tmpUserName,_tmpIsSignedIn)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getSettingsSync(): UserSettingsEntity? {
    val _sql: String = "SELECT * FROM user_settings WHERE id = 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfWeightKg: Int = getColumnIndexOrThrow(_stmt, "weightKg")
        val _columnIndexOfBottleSizeMl: Int = getColumnIndexOrThrow(_stmt, "bottleSizeMl")
        val _columnIndexOfCalculatedTargetMl: Int = getColumnIndexOrThrow(_stmt, "calculatedTargetMl")
        val _columnIndexOfManualTargetMl: Int = getColumnIndexOrThrow(_stmt, "manualTargetMl")
        val _columnIndexOfUseManualOverride: Int = getColumnIndexOrThrow(_stmt, "useManualOverride")
        val _columnIndexOfReminderIntervalMinutes: Int = getColumnIndexOrThrow(_stmt, "reminderIntervalMinutes")
        val _columnIndexOfSleepStartHour: Int = getColumnIndexOrThrow(_stmt, "sleepStartHour")
        val _columnIndexOfSleepEndHour: Int = getColumnIndexOrThrow(_stmt, "sleepEndHour")
        val _columnIndexOfOnboardingCompleted: Int = getColumnIndexOrThrow(_stmt, "onboardingCompleted")
        val _columnIndexOfThemeMode: Int = getColumnIndexOrThrow(_stmt, "themeMode")
        val _columnIndexOfUserEmail: Int = getColumnIndexOrThrow(_stmt, "userEmail")
        val _columnIndexOfUserName: Int = getColumnIndexOrThrow(_stmt, "userName")
        val _columnIndexOfIsSignedIn: Int = getColumnIndexOrThrow(_stmt, "isSignedIn")
        val _result: UserSettingsEntity?
        if (_stmt.step()) {
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpWeightKg: Float
          _tmpWeightKg = _stmt.getDouble(_columnIndexOfWeightKg).toFloat()
          val _tmpBottleSizeMl: Int
          _tmpBottleSizeMl = _stmt.getLong(_columnIndexOfBottleSizeMl).toInt()
          val _tmpCalculatedTargetMl: Int
          _tmpCalculatedTargetMl = _stmt.getLong(_columnIndexOfCalculatedTargetMl).toInt()
          val _tmpManualTargetMl: Int?
          if (_stmt.isNull(_columnIndexOfManualTargetMl)) {
            _tmpManualTargetMl = null
          } else {
            _tmpManualTargetMl = _stmt.getLong(_columnIndexOfManualTargetMl).toInt()
          }
          val _tmpUseManualOverride: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfUseManualOverride).toInt()
          _tmpUseManualOverride = _tmp != 0
          val _tmpReminderIntervalMinutes: Int
          _tmpReminderIntervalMinutes = _stmt.getLong(_columnIndexOfReminderIntervalMinutes).toInt()
          val _tmpSleepStartHour: Int
          _tmpSleepStartHour = _stmt.getLong(_columnIndexOfSleepStartHour).toInt()
          val _tmpSleepEndHour: Int
          _tmpSleepEndHour = _stmt.getLong(_columnIndexOfSleepEndHour).toInt()
          val _tmpOnboardingCompleted: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfOnboardingCompleted).toInt()
          _tmpOnboardingCompleted = _tmp_1 != 0
          val _tmpThemeMode: String
          _tmpThemeMode = _stmt.getText(_columnIndexOfThemeMode)
          val _tmpUserEmail: String?
          if (_stmt.isNull(_columnIndexOfUserEmail)) {
            _tmpUserEmail = null
          } else {
            _tmpUserEmail = _stmt.getText(_columnIndexOfUserEmail)
          }
          val _tmpUserName: String?
          if (_stmt.isNull(_columnIndexOfUserName)) {
            _tmpUserName = null
          } else {
            _tmpUserName = _stmt.getText(_columnIndexOfUserName)
          }
          val _tmpIsSignedIn: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsSignedIn).toInt()
          _tmpIsSignedIn = _tmp_2 != 0
          _result = UserSettingsEntity(_tmpId,_tmpWeightKg,_tmpBottleSizeMl,_tmpCalculatedTargetMl,_tmpManualTargetMl,_tmpUseManualOverride,_tmpReminderIntervalMinutes,_tmpSleepStartHour,_tmpSleepEndHour,_tmpOnboardingCompleted,_tmpThemeMode,_tmpUserEmail,_tmpUserName,_tmpIsSignedIn)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
