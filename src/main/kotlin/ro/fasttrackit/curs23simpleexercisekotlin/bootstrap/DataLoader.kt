package ro.fasttrackit.curs23simpleexercisekotlin.bootstrap

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import ro.fasttrackit.curs23simpleexercisekotlin.domain.MainMenu
import ro.fasttrackit.curs23simpleexercisekotlin.domain.Vacation
import ro.fasttrackit.curs23simpleexercisekotlin.service.VacationService

@Component
class DataLoader(private val vacationService: VacationService) : CommandLineRunner {

    override fun run(vararg args: String?) {
        val count = vacationService.vacationRepository.count()
        if (count == 0L) loadData()
        val menu = MainMenu(vacationService)
        menu.run()
    }

    private fun loadData() {
        vacationService.addVacation(Vacation("Dubai", 3100, 10))
        vacationService.addVacation(Vacation("Paris", 5400, 14))
        vacationService.addVacation(Vacation("London", 2300, 4))
        vacationService.addVacation(Vacation("Moscow", 7900, 6))
        vacationService.addVacation(Vacation("Paris", 3000, 5))
        vacationService.addVacation(Vacation("Dubai", 1800, 5))
        vacationService.addVacation(Vacation("Tokyo", 6800, 8))
        vacationService.addVacation(Vacation("Istanbul", 2000, 7))
        vacationService.addVacation(Vacation("London", 2400, 5))
        vacationService.addVacation(Vacation("Paris", 3900, 8))

        println("Loaded vacations ...")
    }
}