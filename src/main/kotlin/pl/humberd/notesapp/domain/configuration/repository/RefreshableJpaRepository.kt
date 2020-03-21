package pl.humberd.notesapp.domain.configuration.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface RefreshableJpaRepository<T, ID> : JpaRepository<T, ID> {
    fun saveFlushRefresh(entity: T): T
    fun refresh(entity: T)
}
