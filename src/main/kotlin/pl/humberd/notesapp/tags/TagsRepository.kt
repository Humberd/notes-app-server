package pl.humberd.notesapp.tags

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TagsRepository: JpaRepository<Tag, String>
