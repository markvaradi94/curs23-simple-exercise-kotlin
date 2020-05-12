package ro.fasttrackit.curs23simpleexercisekotlin.controller

import org.springframework.web.bind.annotation.*
import ro.fasttrackit.curs23simpleexercisekotlin.domain.Vacation
import ro.fasttrackit.curs23simpleexercisekotlin.service.VacationService

@RestController
@RequestMapping("vacations")
class VacationController(val vacationService: VacationService) {

    @GetMapping
    fun getAllVacations(@RequestParam(required = false) location: String?,
                        @RequestParam(required = false) maxPrice: Int?) =
            vacationService.getAllVacations(location, maxPrice)

    @GetMapping("{id}")
    fun vacationById(@PathVariable id: Int): Vacation = vacationService.getVacationById(id)

    @PostMapping
    fun addVacation(@RequestBody vacation: Vacation) = vacationService.addVacation(vacation)

    @PutMapping("{id}")
    fun replaceVacation(@PathVariable id: Int, @RequestBody vacation: Vacation) =
            vacationService.replaceVacation(id, vacation)

    @DeleteMapping("{id}")
    fun deleteVacation(@PathVariable id: Int) = vacationService.deleteVacation(id)
}