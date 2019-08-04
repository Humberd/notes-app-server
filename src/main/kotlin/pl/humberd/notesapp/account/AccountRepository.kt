package pl.humberd.notesapp.account

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepository : JpaRepository<Account, Long> {

    fun findByGoogleAuth_Id(googleAuthId: String): Optional<Account>
}
