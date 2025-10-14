package org.yusufteker.konekt.di


import com.russhwolf.settings.ExperimentalSettingsApi
import org.koin.core.module.Module
import org.koin.dsl.module
import org.yusufteker.konekt.ui.screen.tasklist.presentation.TaskListViewModel
import org.koin.core.module.dsl.viewModel
import org.yusufteker.konekt.app.AppViewModel
import org.yusufteker.konekt.data.preferences.AppPreference
import org.yusufteker.konekt.data.preferences.AppSettingsFactory
import org.yusufteker.konekt.data.repository.MessageRepositoryImpl
import org.yusufteker.konekt.data.repository.NoteRepositoryImpl
import org.yusufteker.konekt.data.repository.SettingsRepositoryImpl
import org.yusufteker.konekt.data.repository.TaskRepositoryImpl
import org.yusufteker.konekt.domain.repository.MessageRepository
import org.yusufteker.konekt.domain.repository.NoteRepository
import org.yusufteker.konekt.domain.repository.SettingsRepository
import org.yusufteker.konekt.domain.repository.TaskRepository
import org.yusufteker.konekt.ui.screen.dashboard.presentation.DashboardViewModel
import org.yusufteker.konekt.ui.screen.notes.presentation.NotesViewModel
import org.yusufteker.konekt.ui.screen.settings.presentation.SettingsViewModel

@OptIn(ExperimentalSettingsApi::class)
val sharedModule = module {

    single<TaskRepository> { TaskRepositoryImpl() }
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


