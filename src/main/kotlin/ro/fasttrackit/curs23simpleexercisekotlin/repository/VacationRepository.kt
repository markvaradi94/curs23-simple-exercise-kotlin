package ro.fasttrackit.curs23simpleexercisekotlin.repository

import org.springframework.data.jpa.repository.JpaRepository
import ro.fasttrackit.curs23simpleexercisekotlin.domain.Vacation

interface VacationRepository : JpaRepository<Vacation, Int> {

    fun findVacationsByLocationIgnoreCase(location: String?): List<Vacation>

    fun findVacationsByPriceLessThanEqual(maxPrice: Int?): List<Vacation>
}