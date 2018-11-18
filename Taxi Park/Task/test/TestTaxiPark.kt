package taxipark

import org.junit.Assert
import org.junit.Test

class TestTaxiPark {
    @Test
    fun testFakeDrivers() {
        val tp = taxiPark(1..3, 1..2, trip(1, 1), trip(1, 2))
        Assert.assertEquals("Wrong result for 'findFakeDrivers()'." + tp.display(),
                drivers(2, 3).toSet(), tp.findFakeDrivers())
    }

    @Test
    fun testFaithfulPassengers() {
        val tp = taxiPark(1..3, 1..5, trip(1, 1), trip(2, 1), trip(1, 4), trip(3, 4), trip(1, 5), trip(2, 5), trip(2, 2))
        Assert.assertEquals("Wrong result for 'findFaithfulPassengers()'. MinTrips: 2." + tp.display(),
                passengers(1, 4, 5), tp.findFaithfulPassengers(2))
    }

    /*
    Wrong result for 'findFaithfulPassengers()'. MinTrips: 0.
Taxi park:
Drivers: D-0, D-1, D-2, D-3
Passengers: P-0, P-1, P-2, P-3, P-4, P-5, P-6, P-7, P-8, P-9, P-10, P-11
Trips: (D-3, [P-9], 4, 26.0, 0.3), (D-0, [P-7], 16, 34.0, 0.2), (D-2, [P-9], 19, 16.0), (D-1, [P-4, P-6, P-3], 0, 3.0), (D-3, [P-6, P-11], 33, 10.0), (D-3, [P-11, P-9], 20, 22.0), (D-1, [P-3, P-4], 18, 19.0), (D-3, [P-4, P-7], 0, 31.0, 0.3), (D-0, [P-8, P-7], 7, 14.0), (D-0, [P-11, P-7, P-5, P-8], 4, 1.0, 0.4), (D-3, [P-4, P-8, P-1], 35, 2.0), (D-3, [P-1], 35, 30.0), (D-2, [P-6, P-1], 23, 33.0), (D-3, [P-7, P-6], 38, 9.0), (D-1, [P-3, P-4, P-5], 2, 34.0, 0.2), (D-1, [P-4, P-8, P-7], 5, 31.0, 0.1), (D-0, [P-11, P-4, P-6], 15, 2.0), (D-3, [P-9, P-8, P-6], 24, 17.0), (D-3, [P-0], 37, 3.0, 0.1), (D-1, [P-5, P-7], 0, 15.0, 0.4)
 expected:<[Passenger(name=P-0), Passenger(name=P-1), Passenger(name=P-2), Passenger(name=P-3), Passenger(name=P-4), Passenger(name=P-5), Passenger(name=P-6), Passenger(name=P-7), Passenger(name=P-8), Passenger(name=P-9), Passenger(name=P-10), Passenger(name=P-11)]> but was:<[Passenger(name=P-9), Passenger(name=P-7), Passenger(name=P-4), Passenger(name=P-6), Passenger(name=P-3), Passenger(name=P-11), Passenger(name=P-8), Passenger(name=P-5), Passenger(name=P-1), Passenger(name=P-0)]>
     */
    @Test
    fun testFaithfulPassengersC1() {
        val tp = taxiPark(1..3, 1..5, trip(1, 1), trip(2, 1), trip(1, 4), trip(3, 4), trip(1, 5), trip(2, 5), trip(2, 2))
        Assert.assertEquals("Wrong result for 'findFaithfulPassengers()'. MinTrips: 2." + tp.display(),
                passengers(1, 2, 3, 4, 5), tp.findFaithfulPassengers(0))
    }

    @Test
    fun testFrequentPassengers() {
        val tp = taxiPark(1..2, 1..4, trip(1, 1), trip(1, 1), trip(1, listOf(1, 3)), trip(1, 3), trip(1, 2), trip(2, 2))
        Assert.assertEquals("Wrong result for 'findFrequentPassengers()'. Driver: ${driver(1).name}." + tp.display(),
                passengers(1, 3), tp.findFrequentPassengers(driver(1)))
    }

    @Test
    fun testSmartPassengers() {
        val tp = taxiPark(1..2, 1..2, trip(1, 1, discount = 0.1), trip(2, 2))
        Assert.assertEquals("Wrong result for 'findSmartPassengers()'." + tp.display(),
                passengers(1), tp.findSmartPassengers())
    }

    /*
Wrong result for 'findSmartPassengers()'.
Taxi park:
Drivers: D-0, D-1, D-2, D-3, D-4, D-5
Passengers: P-0, P-1, P-2, P-3, P-4, P-5, P-6, P-7, P-8, P-9
Trips: (D-5, [P-2], 33, 18.0), (D-3, [P-5, P-9], 29, 16.0, 0.3), (D-2, [P-5, P-3, P-8], 0, 3.0), (D-1, [P-4, P-8], 32, 6.0), (D-0, [P-1], 37, 28.0), (D-2, [P-0, P-8], 0, 7.0), (D-2, [P-9, P-4], 25, 35.0), (D-0, [P-3, P-7], 30, 35.0), (D-2, [P-2, P-3], 17, 27.0, 0.4), (D-1, [P-9, P-5, P-4], 5, 7.0)
 expected:<[]> but was:<[Passenger(name=P-2)]>
     */
    @Test
    fun testSmartPassengersC1() {
        val tp = taxiPark(0..5, 0..9,
                trip(5, 2, 33, 18.0),
                trip(3, 5, 29, 16.0, 0.3),
                trip(3, 9, 29, 16.0, 0.3),
                trip(2, 5, 0, 3.0),
                trip(2, 3, 0, 3.0),
                trip(2, 8, 0, 3.0),
                trip(1, 4, 32, 6.0),
                trip(1, 8, 32, 6.0),
                trip(0, 1, 37, 28.0),
                trip(2, 0, 0, 7.0),
                trip(2, 8, 0, 7.0),
                trip(2, 9, 25, 35.0),
                trip(2, 4, 25, 35.0),
                trip(0, 3, 30, 35.0),
                trip(0, 7, 30, 35.0),
                trip(2, 2, 17, 27.0, 0.4),
                trip(2, 3, 17, 27.0, 0.4),
                trip(1, 9, 5, 7.0),
                trip(1, 5, 5, 7.0),
                trip(1, 4, 5, 7.0)
        )

        Assert.assertEquals("Wrong result for 'findSmartPassengers()'." + tp.display(),
                passengers(), tp.findSmartPassengers())
    }

    @Test
    fun testTheMostFrequentTripDuration() {
        val tp = taxiPark(1..3, 1..5, trip(1, 1, duration = 10), trip(3, 4, duration = 30),
                trip(1, 2, duration = 20), trip(2, 3, duration = 35))
        // The period 30..39 is the most frequent since there are two trips (duration 30 and 35)
        Assert.assertEquals("Wrong result for 'findTheMostFrequentTripDurationPeriod()'.",
                30..39, tp.findTheMostFrequentTripDurationPeriod())
    }

    /*
    Wrong result for 'checkParetoPrinciple()'.
Taxi park:
Drivers: D-0, D-1, D-2, D-3, D-4
Passengers: P-0, P-1, P-2, P-3, P-4, P-5, P-6, P-7, P-8, P-9
Trips: (D-4, [P-2], 3, 23.0, 0.3), (D-4, [P-4], 27, 8.0, 0.1), (D-4, [P-7], 25, 29.0, 0.2), (D-3, [P-7], 1, 8.0, 0.4), (D-0, [P-2], 18, 2.0, 0.3), (D-4, [P-7], 26, 27.0), (D-4, [P-9], 11, 23.0), (D-4, [P-4, P-2, P-0], 5, 20.0, 0.1), (D-2, [P-6, P-7], 4, 13.0), (D-4, [P-2, P-8, P-1, P-4], 19, 24.0, 0.3), (D-4, [P-6], 17, 19.0), (D-0, [P-9], 15, 7.0, 0.2), (D-0, [P-7, P-3], 0, 10.0, 0.2), (D-4, [P-9, P-3], 15, 13.0), (D-3, [P-0], 11, 3.0, 0.2)
 expected:<true> but was:<false>
     */
    @Test
    fun testParetoPrincipleSucceedsC1() {
        val tp = taxiPark(0..4, 0..9,
                trip(4, 2, 3, 23.0, 0.3),
                trip(4, 4, 27, 8.0, 0.1),
                trip(4, 7, 25, 29.0, 0.2),
                trip(3, 7, 1, 8.0, 0.4),
                trip(0, 2, 18, 2.0, 0.3),
                trip(4, 7, 26, 27.0),
                trip(4, 9, 11, 23.0),
                trip(4, 4, 5, 20.0, 0.1),
                trip(4, 2, 5, 20.0, 0.1),
                trip(4, 0, 5, 20.0, 0.1),
                trip(2, 6, 4, 13.0),
                trip(2, 7, 4, 13.0),
                trip(4, 2, 19, 24.0, 0.3),
                trip(4, 8, 19, 24.0, 0.3),
                trip(4, 1, 19, 24.0, 0.3),
                trip(4, 4, 19, 24.0, 0.3),
                trip(0, 9, 15, 7.0, 0.2),
                trip(0, 7, 0, 10.0, 0.2),
                trip(0, 3, 0, 10.0, 0.2),
                trip(4, 9, 15, 13.0),
                trip(4, 3, 15, 13.0),
                trip(3, 0, 11, 3.0, 0.2)
        )
        // The income of driver #1: 160.0;
        // the total income of drivers #2..5: 40.0.
        // The first driver constitutes exactly 20% of five drivers
        // and his relative income is 160.0 / 200.0 = 80%.

        Assert.assertEquals(
                "Wrong result for 'checkParetoPrinciple()'." + tp.display(),
                true, tp.checkParetoPrinciple())
    }

    @Test
    fun testParetoPrincipleSucceeds() {
        val tp = taxiPark(1..5, 1..4,
                trip(1, 1, 20, 20.0),
                trip(1, 2, 20, 20.0),
                trip(1, 3, 20, 20.0),
                trip(1, 4, 20, 20.0),
                trip(2, 1, 20, 20.0))
        // The income of driver #1: 160.0;
        // the total income of drivers #2..5: 40.0.
        // The first driver constitutes exactly 20% of five drivers
        // and his relative income is 160.0 / 200.0 = 80%.

        Assert.assertEquals(
                "Wrong result for 'checkParetoPrinciple()'." + tp.display(),
                true, tp.checkParetoPrinciple())
    }

    @Test
    fun testParetoPrincipleFails() {
        val tp = taxiPark(1..5, 1..4,
                trip(1, 1, 20, 20.0),
                trip(1, 2, 20, 20.0),
                trip(1, 3, 20, 20.0),
                trip(2, 4, 20, 20.0),
                trip(3, 1, 20, 20.0))
        // The income of driver #1: 120.0;
        // the total income of drivers #2..5: 80.0.
        // The first driver constitutes 20% of five drivers
        // but his relative income is 120.0 / 200.0 = 60%
        // which is less than 80%.

        Assert.assertEquals(
                "Wrong result for 'checkParetoPrinciple()'." + tp.display(),
                false, tp.checkParetoPrinciple())
    }

    @Test
    fun testParetoPrincipleNoTrips() {
        val tp = taxiPark(1..5, 1..4)
        Assert.assertEquals(
                "Wrong result for 'checkParetoPrinciple()'." + tp.display(),
                false, tp.checkParetoPrinciple())
    }
}