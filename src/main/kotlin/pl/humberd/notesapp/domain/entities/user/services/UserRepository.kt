package pl.humberd.notesapp.domain.entities.user.services

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.entities.user.models.User
import pl.humberd.notesapp.domain.entities.user.models.UserId

@Repository
interface UserRepository : JpaRepository<User, UserId>
