package pl.humberd.notesapp.domain.entities.login_password.services

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.entities.login_password.models.LoginPasswordAuth
import pl.humberd.notesapp.domain.entities.user.models.UserId

@Repository
interface LoginPasswordRepository : JpaRepository<LoginPasswordAuth, UserId>
