package ro.fasttrackit.curs23simpleexercisekotlin.domain

import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
@NoArgsConstructor
@AllArgsConstructor
data class Vacation(@Column(name = "location") val location: String,
                    @Column(name = "price") val price: Int,
                    @Column(name = "duration") val duration: Int) {
    @Id
    @GeneratedValue
    val id: Int = 0

    data class Builder(
            var location: String? = null,
            var price: Int? = null,
            var duration: Int? = null) {

        fun location(location: String) = apply { this.location = location }
        fun price(price: Int) = apply { this.price = price }
        fun duration(duration: Int) = apply { this.duration = duration }
        fun build() = location?.let { price?.let { it1 -> duration?.let { it2 -> Vacation(it, it1, it2) } } }
    }
}