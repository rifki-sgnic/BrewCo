package com.rfk.brewco.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rfk.brewco.R
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.concurrent.Executors

@Database(entities = [Coffee::class], version = 1, exportSchema = false)
abstract class CoffeeDatabase : RoomDatabase() {

    abstract fun CoffeeDao(): CoffeeDao

    companion object {
        @Volatile
        private var INSTANCE: CoffeeDatabase? = null
        private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

        fun getInstance(context: Context): CoffeeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CoffeeDatabase::class.java,
                    "coffee.db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object: Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            ioThread {
                                fillWithStartingData(context, getInstance(context).CoffeeDao())
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }

        fun ioThread(f: () -> Unit) {
            IO_EXECUTOR.execute(f)
        }

        private fun fillWithStartingData(context: Context, dao: CoffeeDao) {
            val jsonArray = loadJsonArray(context)
            try {
                if (jsonArray != null) {
                    for (i in 0 until jsonArray.length()) {
                        val item = jsonArray.getJSONObject(i)
                        dao.insertAll(
                            Coffee(
                                item.getInt("id"),
                                item.getString("name"),
                                item.getString("imagePath"),
                            )
                        )
                    }
                }
            } catch (exception: JSONException) {
                exception.printStackTrace()
            }
        }

        private fun loadJsonArray(context: Context): JSONArray? {
            val builder = StringBuilder()
            val `in` = context.resources.openRawResource(R.raw.coffee)
            val reader = BufferedReader(InputStreamReader(`in`))
            var line: String?
            try {
                while (reader.readLine().also { line = it } != null) {
                    builder.append(line)
                }
                val json = JSONObject(builder.toString())
                return json.getJSONArray("coffee")
            } catch (exception: IOException) {
                exception.printStackTrace()
            } catch (exception: JSONException) {
                exception.printStackTrace()
            }
            return null
        }
    }
}