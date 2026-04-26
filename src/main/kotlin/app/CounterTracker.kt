package app

class CounterTracker(counter: CounterObservable) {
    var modifications = 0
        private set
    var maxValue = 0
        private set

    init {
        counter.addObserver(object : CounterObserver {
            override fun incEvent(value: Int) {
                modifications++
                maxValue++
            }

            override fun decEvent(value: Int) {
                modifications++
            }
        })
    }
}

fun main() {
    val counter = CounterObservable()
    val ct = CounterTracker(counter)

    counter.inc()
    counter.inc()
    counter.dec()
    counter.inc()
    println(ct.modifications)
    println(ct.maxValue)
}