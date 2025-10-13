package org.yusufteker.konekt.di


import org.koin.dsl.module
import org.yusufteker.konekt.ui.screen.tasklist.presentation.TaskListViewModel
import org.koin.core.module.dsl.viewModel
import org.yusufteker.konekt.data.MessageRepositoryImpl
import org.yusufteker.konekt.data.NoteRepositoryImpl
import org.yusufteker.konekt.data.TaskRepositoryImpl
import org.yusufteker.konekt.domain.repository.MessageRepository
import org.yusufteker.konekt.domain.repository.NoteRepository
import org.yusufteker.konekt.domain.repository.TaskRepository
import org.yusufteker.konekt.ui.screen.dashboard.presentation.DashboardViewModel
import org.yusufteker.konekt.ui.screen.notes.presentation.NotesViewModel

val sharedModule = module {

    single<TaskRepository> { TaskRepositoryImpl() }
    viewModel { TaskListViewModel(get()) }

    single<MessageRepository> { MessageRepositoryImpl() }
    viewModel { DashboardViewModel(get(), get()) }

    single<NoteRepository> { NoteRepositoryImpl() }
    viewModel { NotesViewModel(get()) }
}
