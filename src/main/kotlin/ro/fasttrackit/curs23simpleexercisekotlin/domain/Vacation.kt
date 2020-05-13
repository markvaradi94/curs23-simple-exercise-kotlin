package ro.fasttrackit.curs23simpleexercisekotlin.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Vacation(@Column(name = "location") val location: String,
                    @Column(name = "price") val price: Int,
                    @Column(name = "duration") val duration: Int) {
    @Id
    @GeneratedValue
    val id: Int = 0

    override fun toString(): String {
        return "Vacation(id = $id, location = $location, price = $price, duration = $duration)"
    }
}