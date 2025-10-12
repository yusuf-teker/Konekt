package org.yusufteker.konekt.di


import org.koin.dsl.module
import org.yusufteker.konekt.ui.screen.tasklist.presentation.TaskListViewModel
import org.koin.core.module.dsl.viewModel

val sharedModule = module {

    //single<TaskRepository> { TaskRepositoryImpl() }
    viewModel { TaskListViewModel() }

}
