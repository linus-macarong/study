package linus.test.common.entity

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@MappedSuperclass
abstract class LongIdEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "created_at")
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "updated_at")
    @UpdateTimestamp
    val updatedAt: LocalDateTime = LocalDateTime.now()

    fun getId(): Long {
        return this.id ?: throw IllegalStateException("Id is null")
    }
}
