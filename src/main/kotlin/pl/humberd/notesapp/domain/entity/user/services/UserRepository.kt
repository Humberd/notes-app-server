package pl.humberd.notesapp.domain.entity.user.services

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.entity.user.models.User
import pl.humberd.notesapp.domain.entity.user.models.UserId

@Repository
interface UserRepository : JpaRepository<User, UserId>
