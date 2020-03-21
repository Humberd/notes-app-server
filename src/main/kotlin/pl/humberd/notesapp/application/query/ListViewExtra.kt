package pl.humberd.notesapp.application.query

import org.springframework.data.domain.Page

open class ListViewExtra(
    val pageNumber: Int,
    val pageSize: Int,
    val totalPages: Int,
    val totalElements: Long,
    val isFirst: Boolean,
    val isLast: Boolean
) {
    companion object {
        fun from(page: Page<*>): ListViewExtra = ListViewExtra(
            pageNumber = page.number,
            pageSize = page.size,
            totalPages = page.totalPages,
            totalElements = page.totalElements,
            isFirst = page.isFirst,
            isLast = page.isLast
        )
    }
}
