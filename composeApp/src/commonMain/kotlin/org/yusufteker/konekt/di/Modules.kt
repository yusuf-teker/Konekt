package org.yusufteker.konekt.di


import com.russhwolf.settings.ExperimentalSettingsApi
import io.ktor.client.engine.HttpClientEngine
import org.koin.core.module.Module
import org.koin.dsl.module
import org.yusufteker.konekt.ui.screen.tasklist.presentation.TaskListViewModel
import org.koin.core.module.dsl.viewModel
import org.yusufteker.konekt.app.AppViewModel
import org.yusufteker.konekt.data.datasource.local.TaskLocalDataSource
import org.yusufteker.konekt.data.datasource.local.TaskLocalDataSourceImpl
import org.yusufteker.konekt.data.datasource.remote.TaskRemoteDataSource
import org.yusufteker.konekt.data.datasource.remote.TaskRemoteDataSourceImpl
import org.yusufteker.konekt.data.network.api.TaskApi
import org.yusufteker.konekt.data.network.api.TaskApiImpl
import org.yusufteker.konekt.data.network.createHttpClient
import org.yusufteker.konekt.data.preferences.AppPreference
import org.yusufteker.konekt.data.repository.MessageRepositoryImpl
import org.yusufteker.konekt.data.repository.NoteRepositoryImpl
import org.yusufteker.konekt.data.repository.SettingsRepositoryImpl
import org.yusufteker.konekt.data.repository.TaskRepositoryImpl
import org.yusufteker.konekt.domain.repository.MessageRepository
import org.yusufteker.konekt.domain.repository.NoteRepository
import org.yusufteker.konekt.domain.repository.SettingsRepository
import org.yusufteker.konekt.domain.repository.TaskRepository
import org.yusufteker.konekt.ui.popup.PopupManager
import org.yusufteker.konekt.ui.screen.dashboard.presentation.DashboardViewModel
import org.yusufteker.konekt.ui.screen.notes.presentation.NotesViewModel
import org.yusufteker.konekt.ui.screen.settings.presentation.SettingsViewModel

@OptIn(ExperimentalSettingsApi::class)
val sharedModule = module {

    single { PopupManager() }

    single { createHttpClient(get<HttpClientEngine>()) }

    single<TaskApi> { TaskApiImpl(get()) }
    single<TaskLocalDataSource> { TaskLocalDataSourceImpl() }
    single<TaskRemoteDataSource> { TaskRemoteDataSourceImpl(get()) }
    single<TaskRepository> { TaskRepositoryImpl(get(),get()) }
    viewModel { TaskListViewModel(get()) }

    single<MessageRepository> { MessageRepositoryImpl() }
    viewModel { DashboardViewModel(get(), get()) }

    single<NoteRepository> { NoteRepositoryImpl() }
    viewModel { NotesViewModel(get()) }


    single { AppPreference(get()) }

    single<SettingsRepository> {
        SettingsRepositoryImpl(get())
    }
    viewModel { SettingsViewModel(get()) }

    viewModel { AppViewModel(get()) }
}

expect val platformModule: Module


