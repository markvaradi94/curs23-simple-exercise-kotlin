package ro.fasttrackit.curs23simpleexercisekotlin.service

import org.springframework.stereotype.Service
import ro.fasttrackit.curs23simpleexercisekotlin.domain.Vacation
import ro.fasttrackit.curs23simpleexercisekotlin.exceptions.ResourceNotFoundException
import ro.fasttrackit.curs23simpleexercisekotlin.repository.VacationRepository

@Service
class VacationService(val vacationRepository: VacationRepository) {

    fun getAllVacations(location: String?, maxPrice: Int?): List<Vacation> {
        return if (location == null && maxPrice == null) {
            vacationRepository.findVacationsByLocationIgnoreCase(location).toList()
        } else if (location == null && maxPrice != null) {
            vacationRepository.findVacationsByPriceLessThanEqual(maxPrice).toList()
        } else vacationRepository.findAll().toList()
    }

    fun getVacationById(id: Int): Vacation = getOrThrow(id)

    fun addVacation(vacation: Vacation) = vacationRepository.save(vacation)

    fun replaceVacation(id: Int, vacation: Vacation): Vacation {
        val vacationToReplace = getOrThrow(id)
        vacationToReplace.location = vacation.location
        vacationToReplace.price = vacation.price
        vacationToReplace.duration = vacation.duration
        return vacationRepository.save(vacationToReplace)
    }

    fun deleteVacation(id: Int): Vacation {
        val vacationToDelete = getOrThrow(id)
        vacationRepository.deleteById(id)
        return vacationToDelete
    }

    private fun getOrThrow(id: Int) = vacationRepository
            .findById(id)
            .orElseThrow { ResourceNotFoundException("Could not find vacation with id $id") }
}