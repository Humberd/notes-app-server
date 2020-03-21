package pl.humberd.notesapp.domain.configuration.repository

import org.springframework.data.jpa.repository.support.JpaEntityInformation
import org.springframework.data.jpa.repository.support.SimpleJpaRepository
import javax.persistence.EntityManager
import javax.transaction.Transactional


class RefreshableJpaRepositoryImpl<T, ID>(
    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    repositoryInformation: JpaEntityInformation<T, ID>,
    val entityManager: EntityManager
) :
    SimpleJpaRepository<T, ID>(repositoryInformation, entityManager),
    RefreshableJpaRepository<T, ID> {

    @Transactional
    override fun saveFlushRefresh(entity: T): T {
        return this.saveAndFlush(entity).also {
            this.refresh(it)
        }
    }

    @Transactional
    override fun refresh(entity: T) {
        entityManager.refresh(entity)
    }
}
