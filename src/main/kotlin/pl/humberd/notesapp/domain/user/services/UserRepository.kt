package pl.humberd.notesapp.domain.user.services

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.user.models.User

@Repository
interface UserRepository: JpaRepository<User, String>;
