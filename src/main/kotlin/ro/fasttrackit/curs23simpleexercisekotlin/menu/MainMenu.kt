package ro.fasttrackit.curs23simpleexercisekotlin.menu

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment
import org.apache.commons.lang3.StringUtils
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import org.springframework.web.util.UriComponentsBuilder
import ro.fasttrackit.curs23simpleexercisekotlin.domain.Vacation
import ro.fasttrackit.curs23simpleexercisekotlin.exceptions.ResourceNotFoundException
import ro.fasttrackit.curs23simpleexercisekotlin.service.VacationService
import java.util.*

@Component
class MainMenu(val vacationService: VacationService) {

    val rest = RestTemplate()
    val scanner = Scanner(System.`in`)
    val builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/vacations/")

    fun run() {
        var input: Int
        do {
            printMainMenu()
            input = getInput()
            executeInput(input)
        } while (input != 7)
    }

    private fun printMainMenu() {
        println()
        println("===========================================================================")
        println("(1) List all vacations")
        println("(2) Get vacations for a specific location")
        println("(3) Get vacations with maximum price of")
        println("(4) Add a new vacation")
        println("(5) Delete a vacation by id")
        println("(6) Update an existing vacation")
        println("(7) Exit")
        println("===========================================================================")
    }

    private fun getInput(): Int {
        print("Select your option: ")
        return numeric(scanner.next())
    }

    private fun executeInput(input: Int) {
        when (input) {
            1 -> handleListAllVacations()
            2 -> handleVacationsFromLocation()
            3 -> handleVacationsUnderAmount()
            4 -> handleAddVacation()
            5 -> handleDeleteVacation()
            6 -> handleUpdateVacation()
            7 -> handleExit()
            else -> println("Invalid option selected")
        }
    }

    private fun handleListAllVacations() {
        printOptionResult(builder)
    }

    private fun handleVacationsFromLocation() {
        print("Enter location: ")
        val location = scanner.next()
        scanner.nextLine()

        val requestBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/vacations")
                .queryParam("location", location)
                .query("Select * from vacations where location = $location")

        printOptionResult(requestBuilder)
    }

    private fun handleVacationsUnderAmount() {
        print("Enter maximum price: ")
        val maxPrice = numeric(scanner.next())

        val requestBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/vacations")
                .queryParam("maxPrice", maxPrice)
                .query("Select * from vacations where price <= $maxPrice")

        printOptionResult(requestBuilder)
    }

    private fun handleAddVacation() {
        val vacationToAdd = newVacationDetails()
        rest.postForObject(builder.toUriString(), vacationToAdd, Vacation::class.java)
    }

    private fun handleDeleteVacation() {
        print("Enter ID of vacation to be deleted: ")
        val id = numeric(scanner.next())
        scanner.nextLine()
        try {
            val vacationToDelete = vacationService.getVacationById(id)
            rest.delete(builder.toUriString() + vacationToDelete.id)
        } catch (exception: ResourceNotFoundException) {
            println(exception.message)
        }
    }

    private fun handleUpdateVacation() {
        print("Enter ID of vacation to be updated: ")
        val id = numeric(scanner.next())
        scanner.nextLine()
        try {
            val vacationToUpdate = vacationService.getVacationById(id)
            val newVacation = newVacationDetails()
            scanner.nextLine()
            rest.put(builder.toUriString() + vacationToUpdate.id, newVacation)
        } catch (exception: ResourceNotFoundException) {
            println(exception.message)
        }
    }

    private fun handleExit() {
        println("Exiting command line vacation service")
    }

    private fun printOptionResult(requestBuilder: UriComponentsBuilder) {
        val vacations = getListOfVacations(requestBuilder)
        if (vacations != null) {
            if (vacations.isNotEmpty()) renderAsciiTable(vacations) else renderEmptyAsciiTable()
        }
    }

    private fun getListOfVacations(builder: UriComponentsBuilder): List<Vacation>? {
        val headers = HttpHeaders()
        return rest.exchange<List<Vacation>>(builder.toUriString(),
                HttpMethod.GET,
                HttpEntity<Any>(headers),
                object : ParameterizedTypeReference<List<Vacation?>?>() {})
                .body
    }

    private fun newVacationDetails(): Vacation {
        print("Enter location: ")
        val location = scanner.next()
        print("Enter price: ")
        val price = numeric(scanner.next())
        print("Enter duration: ")
        val duration = numeric(scanner.next())
        return Vacation(location, price, duration)
    }

    private fun renderAsciiTable(vacations: List<Vacation>?) {
        val asciiTable = AsciiTable()
        asciiTable.addRule()
        asciiTable.addRow("ID", "Location", "Price", "Duration")
        asciiTable.addRule()
        vacations?.forEach { asciiTable.addRow(it.id, it.location, it.price, it.duration) }
        asciiTable.addRule()
        println(asciiTable.render())
    }

    private fun renderEmptyAsciiTable() {
        val asciiTable = AsciiTable()
        asciiTable.addRule()
        asciiTable.addRow("NO ELEMENTS FOUND FOR YOUR SEARCH").setTextAlignment(TextAlignment.CENTER)
        asciiTable.addRule()
        println(asciiTable.render())
    }

    private fun numeric(input: String): Int {
        var newInput = ""
        if (StringUtils.isNumeric(input)) {
            return input.toInt()
        } else {
            while (!StringUtils.isNumeric(newInput)) {
                print("Incorrect input. Please enter correct value: ")
                newInput = scanner.next()
            }
        }
        return newInput.toInt()
    }
}