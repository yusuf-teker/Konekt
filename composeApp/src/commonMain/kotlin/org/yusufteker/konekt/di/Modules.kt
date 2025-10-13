package org.yusufteker.konekt.di


import org.koin.dsl.module
import org.yusufteker.konekt.ui.screen.tasklist.presentation.TaskListViewModel
import org.koin.core.module.dsl.viewModel
import org.yusufteker.konekt.data.TaskRepositoryImpl
import org.yusufteker.konekt.domain.repository.TaskRepository

val sharedModule = module {

    single<TaskRepository> { TaskRepositoryImpl() }
    viewModel { TaskListViewModel(get()) }

}
