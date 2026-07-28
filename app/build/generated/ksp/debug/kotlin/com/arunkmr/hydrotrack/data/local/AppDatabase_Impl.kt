package com.arunkmr.hydrotrack.`data`.local

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class AppDatabase_Impl : AppDatabase() {
  private val _waterLogDao: Lazy<WaterLogDao> = lazy {
    WaterLogDao_Impl(this)
  }

  private val _settingsDao: Lazy<SettingsDao> = lazy {
    SettingsDao_Impl(this)
  }

  private val _symptomLogDao: Lazy<SymptomLogDao> = lazy {
    SymptomLogDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(2, "9f2068a0c83c83411d53716bfead4191", "ac819b882ef91c02b61aa1fca02c3cb3") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `water_logs` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `timestampEpochMs` INTEGER NOT NULL, `amountMl` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `symptom_logs` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `dateEpochDay` INTEGER NOT NULL, `feltDizzy` INTEGER NOT NULL, `drySkin` INTEGER NOT NULL, `notes` TEXT)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `user_settings` (`id` INTEGER NOT NULL, `weightKg` REAL NOT NULL, `bottleSizeMl` INTEGER NOT NULL, `calculatedTargetMl` INTEGER NOT NULL, `manualTargetMl` INTEGER, `useManualOverride` INTEGER NOT NULL, `reminderIntervalMinutes` INTEGER NOT NULL, `sleepStartHour` INTEGER NOT NULL, `sleepEndHour` INTEGER NOT NULL, `onboardingCompleted` INTEGER NOT NULL, `themeMode` TEXT NOT NULL, `userEmail` TEXT, `userName` TEXT, `isSignedIn` INTEGER NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '9f2068a0c83c83411d53716bfead4191')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `water_logs`")
        connection.execSQL("DROP TABLE IF EXISTS `symptom_logs`")
        connection.execSQL("DROP TABLE IF EXISTS `user_settings`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection): RoomOpenDelegate.ValidationResult {
        val _columnsWaterLogs: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsWaterLogs.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWaterLogs.put("timestampEpochMs", TableInfo.Column("timestampEpochMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWaterLogs.put("amountMl", TableInfo.Column("amountMl", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysWaterLogs: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesWaterLogs: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoWaterLogs: TableInfo = TableInfo("water_logs", _columnsWaterLogs, _foreignKeysWaterLogs, _indicesWaterLogs)
        val _existingWaterLogs: TableInfo = read(connection, "water_logs")
        if (!_infoWaterLogs.equals(_existingWaterLogs)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |water_logs(com.arunkmr.hydrotrack.data.local.entity.WaterLogEntity).
              | Expected:
              |""".trimMargin() + _infoWaterLogs + """
              |
              | Found:
              |""".trimMargin() + _existingWaterLogs)
        }
        val _columnsSymptomLogs: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsSymptomLogs.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSymptomLogs.put("dateEpochDay", TableInfo.Column("dateEpochDay", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSymptomLogs.put("feltDizzy", TableInfo.Column("feltDizzy", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSymptomLogs.put("drySkin", TableInfo.Column("drySkin", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSymptomLogs.put("notes", TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysSymptomLogs: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesSymptomLogs: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoSymptomLogs: TableInfo = TableInfo("symptom_logs", _columnsSymptomLogs, _foreignKeysSymptomLogs, _indicesSymptomLogs)
        val _existingSymptomLogs: TableInfo = read(connection, "symptom_logs")
        if (!_infoSymptomLogs.equals(_existingSymptomLogs)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |symptom_logs(com.arunkmr.hydrotrack.data.local.entity.SymptomLogEntity).
              | Expected:
              |""".trimMargin() + _infoSymptomLogs + """
              |
              | Found:
              |""".trimMargin() + _existingSymptomLogs)
        }
        val _columnsUserSettings: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsUserSettings.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUserSettings.put("weightKg", TableInfo.Column("weightKg", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUserSettings.put("bottleSizeMl", TableInfo.Column("bottleSizeMl", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUserSettings.put("calculatedTargetMl", TableInfo.Column("calculatedTargetMl", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUserSettings.put("manualTargetMl", TableInfo.Column("manualTargetMl", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUserSettings.put("useManualOverride", TableInfo.Column("useManualOverride", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUserSettings.put("reminderIntervalMinutes", TableInfo.Column("reminderIntervalMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUserSettings.put("sleepStartHour", TableInfo.Column("sleepStartHour", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUserSettings.put("sleepEndHour", TableInfo.Column("sleepEndHour", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUserSettings.put("onboardingCompleted", TableInfo.Column("onboardingCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUserSettings.put("themeMode", TableInfo.Column("themeMode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUserSettings.put("userEmail", TableInfo.Column("userEmail", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUserSettings.put("userName", TableInfo.Column("userName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUserSettings.put("isSignedIn", TableInfo.Column("isSignedIn", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysUserSettings: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesUserSettings: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoUserSettings: TableInfo = TableInfo("user_settings", _columnsUserSettings, _foreignKeysUserSettings, _indicesUserSettings)
        val _existingUserSettings: TableInfo = read(connection, "user_settings")
        if (!_infoUserSettings.equals(_existingUserSettings)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |user_settings(com.arunkmr.hydrotrack.data.local.entity.UserSettingsEntity).
              | Expected:
              |""".trimMargin() + _infoUserSettings + """
              |
              | Found:
              |""".trimMargin() + _existingUserSettings)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "water_logs", "symptom_logs", "user_settings")
  }

  public override fun clearAllTables() {
    super.performClear(false, "water_logs", "symptom_logs", "user_settings")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(WaterLogDao::class, WaterLogDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(SettingsDao::class, SettingsDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(SymptomLogDao::class, SymptomLogDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>): List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun waterLogDao(): WaterLogDao = _waterLogDao.value

  public override fun settingsDao(): SettingsDao = _settingsDao.value

  public override fun symptomLogDao(): SymptomLogDao = _symptomLogDao.value
}
