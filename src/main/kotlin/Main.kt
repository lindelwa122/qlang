package org.example

import org.example.data.*

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val crimeData = Query(QueryChain())
        .from(crimeSceneReports())
        .where( { data ->
            data["year"] == 2024 &&
            data["month"] == 7 &&
            data["day"] == 28 &&
            data["street"] == "Humphrey Street"
        })
        .select { data -> 
            mapOf("descr" to data["description"]!!) 
        }
        .execute()

    // Crime happened @ 10:15am

    val interviews = Query(QueryChain())
        .from(interviews())
        .where( { data ->
            data["year"] == 2024 &&
            data["month"] == 7 &&
            data["day"] == 28
        })
        .select { data ->
            mapOf(
                "name" to data["name"]!!,
                "transcript" to data["transcript"]!!
            )
        }
        .execute()

    // the thief left the town on the 29th of July 
    // the thief withdrew money the same morning of the event
    // they called someone for less than a minute
    // they drove away from the bakery within 10 minutes
    
    val bakerySecurityLogs = Query(QueryChain())
        .from(bakerySecurityLogs())
        .where( { data ->
            data["year"] == 2024 &&
            data["month"] == 7 &&
            data["day"] == 28 &&
            data["hour"] == 10 &&
            data["minute"] as Int >= 15 &&
            data["minute"] as Int <= 25 &&
            data["activity"] == "exit"
        })
        .select { data -> 
            mapOf(
               "licensePlate" to data["licensePlate"]!! 
            )
        }
        .execute()
 
    val calls = Query(QueryChain())
        .from(phoneCalls())
        .where( { data ->
            data["year"] == 2024 &&
            data["month"] == 7 &&
            data["day"] == 28 &&
            data["duration"] as Int <= 60
        })
        .select { data -> 
            mapOf(
                "caller" to data["caller"]!!,
                "receiver" to data["receiver"]!!
            )
        }
        .execute()

    val transactions = Query(QueryChain())
        .from(atmTransactions())
        .where( { data ->
            data["year"] == 2024 &&
            data["month"] == 7 &&
            data["day"] == 28 &&
            data["atmLocation"] == "Leggett Street" &&
            data["transactionType"] == "withdraw"
        })
        .select { data ->
            mapOf(
                "accountNumber" to data["accountNumber"]!!
            )
        }
        .execute()

    val flights = Query(QueryChain())
        .from(flights())
        .where( { data ->
            data["year"] == 2024 &&
            data["month"] == 7 &&
            data["day"] == 29 &&
            data["hour"] as Int <= 11
        })
        .select { data -> 
            mapOf(
                "id" to data["id"]!!,
                "originAirportId" to data["originAirportId"]!!,
                "destinationAirportId" to data["destinationAirportId"]!!,
            )
        }
        .execute()

    val passengers = Query(QueryChain())
        .from(passengers())
        .where( { data ->
            data["flightId"] in getValues(flights, "id")
        })
        .execute()

    val bankAccounts = Query(QueryChain())
        .from(bankAccounts())
        .where( { data ->
            data["accountNumber"] in getValues(transactions, "accountNumber")
        })
        .execute()

    val people = Query(QueryChain())
        .from(people())
        .where( { data ->
            data["licensePlate"] in getValues(bakerySecurityLogs, "licensePlate") &&
            data["phoneNumber"] in getValues(calls, "caller") &&
            data["passportNumber"] in getValues(passengers, "passportNumber") &&
            data["id"] in getValues(bankAccounts, "personId")
        })
        .execute()
    
        prettyPrintTable(people)
}
