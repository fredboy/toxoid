package ru.fredboy.toxoid.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.fredboy.toxoid.clean.presentation.formatter.DateFormats
import android.text.format.DateFormat as AndroidDateFormat

@Module
@InstallIn(SingletonComponent::class)
class FormatterProvidersModule {
    @Provides
    fun provideDateFormats(@ApplicationContext appContext: Context): DateFormats {
        val dateFormat = AndroidDateFormat.getDateFormat(appContext)
        val timeFormat = AndroidDateFormat.getTimeFormat(appContext)
        return DateFormats(dateFormat, timeFormat)
    }
}