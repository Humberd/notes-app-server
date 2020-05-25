package pl.humberd.notesapp.application.common.transaction

import org.springframework.transaction.support.TransactionSynchronization
import org.springframework.transaction.support.TransactionSynchronizationManager

object TransactionsHelper {
    fun afterCommit(callback: () -> Unit) {
        TransactionSynchronizationManager.registerSynchronization(object : TransactionSynchronization {
            override fun afterCommit() {
                callback.invoke()
            }
        })
    }
}
