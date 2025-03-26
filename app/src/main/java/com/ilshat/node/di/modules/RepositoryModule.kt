package com.ilshat.node.di.modules

import com.ilshat.node.data.NodeRepositoryImpl
import com.ilshat.node.domain.NodeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindRequestRepository(
        requestRepositoryImpl: NodeRepositoryImpl
    ): NodeRepository
}