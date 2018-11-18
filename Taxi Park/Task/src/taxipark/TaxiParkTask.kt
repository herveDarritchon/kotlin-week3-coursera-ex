package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> {

    val listOfDriversWithAtLeastOneTrip = this.trips.map { it.driver }.distinct()
    return this.allDrivers.filter { d -> d !in listOfDriversWithAtLeastOneTrip }.toSet()
}

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> {
    if (minTrips == 0) {
        return this.allPassengers
    }
    val listOfPassengersWithTheirNumberOfTrip = this.trips.flatMap { it.passengers }.groupingBy { it }.eachCount()
    return listOfPassengersWithTheirNumberOfTrip
            .filter { it.value >= minTrips }
            .map { it.key }
            .toSet()
}

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> {
    val mapOfPassenger = this.trips
            .filter { it.driver == driver }
            .flatMap { it.passengers }
            .groupingBy { it }
            .eachCount()
    return mapOfPassenger.filter { it.value > 1 }.map { it.key }.toSet()

}

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> {
    val passengersByNbTrips = this.trips
            .flatMap { it.passengers }
            .groupingBy { it }
            .eachCount()
    val passengersByDiscount = this.trips
            .filter { it.discount != null && it.discount > 0.0 }
            .flatMap { it.passengers }
            .groupingBy { it }
            .eachCount()

    val passengersByNoDiscount = this.trips
            .filter { it.discount == null || it.discount == 0.0 }
            .flatMap { it.passengers }
            .groupingBy { it }
            .eachCount()

    return passengersByDiscount
            .filter { it.value > (passengersByNoDiscount[it.key] ?: 0) }
            .map { it.key }
            .toSet()

/*    return passengersByDiscount
            .filter { it.value >= passengersByNbTrips[it.key]?.div(2) ?: 0 }
            .map { it.key }
            .toSet()*/
}

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    val idx = this.trips
            .groupBy { it.duration / 10 }
            .map { Pair(it.key, it.value.count()) }
            .maxBy { p -> p.second }
            ?.first

    val min = idx?.times(10)
    val max = min?.plus(9) ?: 0
    return min?.rangeTo(max)
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {

    if (this.trips.isEmpty() || this.allDrivers.isEmpty() || this.allPassengers.isEmpty()) {
        return false
    }

    val listOfDrivers = this.allDrivers

    val listOfDriverSortedByIncome = this.trips
            .groupBy { t -> t.driver }
            .map { e -> Pair(e.key, e.value.sumByDouble { t -> t.cost }) }
            .sortedByDescending { (_, value) -> value }
            .toList()


    val twentyPercentOfDrivers = (listOfDrivers.count() * 20) / 100

    val topDrivers = listOfDriverSortedByIncome
            .take(twentyPercentOfDrivers)
            .map { it.first }

    val totalIncome: Double = this.trips
            .sumByDouble { it.cost }

    val eightyPercentIncome = (totalIncome * 80) / 100

    val incomeByTopDriver = this.trips
            .filter { it.driver in topDrivers }
            .map { it.cost }
            .sum()

    return incomeByTopDriver >= eightyPercentIncome
}