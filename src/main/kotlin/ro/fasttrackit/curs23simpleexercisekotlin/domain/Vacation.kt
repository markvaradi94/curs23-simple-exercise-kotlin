package ro.fasttrackit.curs23simpleexercisekotlin.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Vacation(@Column(name = "location") var location: String,
                    @Column(name = "price") var price: Int,
                    @Column(name = "duration") var duration: Int) {
    @Id
    @GeneratedValue
    val id: Int = 0

    override fun toString(): String {
        return "Vacation(id = $id, location = $location, price = $price, duration = $duration)"
    }
}