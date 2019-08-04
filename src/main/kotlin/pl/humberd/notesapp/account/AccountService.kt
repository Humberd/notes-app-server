package pl.humberd.notesapp.account

import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class AccountService(
    private val accountRepository: AccountRepository
) {

    @Transactional
    fun createFromGoogle(email: String, googleUserId: String): Account {
        val account = Account(
            null
        )

        val googleAuth = GoogleAuth(
            account = account,
            id = googleUserId,
            email = email
        )

        account.googleAuth = googleAuth

        return accountRepository.save(account)
    }

    fun getOrCreateFromGoogle(email: String, googleUserId: String): Account {
        val potentialAccount = accountRepository.findByGoogleAuth_Id(googleUserId)

        if (potentialAccount.isPresent) {
            return potentialAccount.get()
        }

        return createFromGoogle(email, googleUserId)
    }

}
