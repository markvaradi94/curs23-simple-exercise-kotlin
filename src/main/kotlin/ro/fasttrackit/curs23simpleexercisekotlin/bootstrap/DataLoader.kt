package ro.fasttrackit.curs23simpleexercisekotlin.bootstrap

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import ro.fasttrackit.curs23simpleexercisekotlin.domain.Vacation
import ro.fasttrackit.curs23simpleexercisekotlin.service.VacationService

@Component
class DataLoader(private val vacationService: VacationService) : CommandLineRunner {

    override fun run(vararg args: String?) {
        val count = vacationService.vacationRepository.count()
        if (count == 0L) loadData()
    }

    private fun loadData() {
        Vacation.Builder()
                .location("Dubai")
                .price(3100)
                .duration(10)
                .build()?.let { vacationService.addVacation(it) }

        Vacation.Builder()
                .location("Paris")
                .price(5400)
                .duration(14)
                .build()?.let { vacationService.addVacation(it) }

        Vacation.Builder()
                .location("London")
                .price(2300)
                .duration(4)
                .build()?.let { vacationService.addVacation(it) }

        Vacation.Builder()
                .location("New York")
                .price(7900)
                .duration(6)
                .build()?.let { vacationService.addVacation(it) }

        Vacation.Builder()
                .location("Paris")
                .price(3000)
                .duration(5)
                .build()?.let { vacationService.addVacation(it) }

        Vacation.Builder()
                .location("Dubai")
                .price(1800)
                .duration(5)
                .build()?.let { vacationService.addVacation(it) }

        Vacation.Builder()
                .location("Tokyo")
                .price(6800)
                .duration(8)
                .build()?.let { vacationService.addVacation(it) }

        Vacation.Builder()
                .location("Istanbul")
                .price(2000)
                .duration(7)
                .build()?.let { vacationService.addVacation(it) }

        Vacation.Builder()
                .location("London")
                .price(2400)
                .duration(5)
                .build()?.let { vacationService.addVacation(it) }

        Vacation.Builder()
                .location("Paris")
                .price(3900)
                .duration(8)
                .build()?.let { vacationService.addVacation(it) }

        println("Loaded vacations ...")
    }
}