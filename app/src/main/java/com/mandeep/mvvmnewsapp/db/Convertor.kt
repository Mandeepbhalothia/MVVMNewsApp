package com.mandeep.mvvmnewsapp.db

import androidx.room.TypeConverter
import com.mandeep.mvvmnewsapp.model.Source

class Convertor {

    @TypeConverter
    fun fromSource(source: Source): String{
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source = Source(name,name)

}