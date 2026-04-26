package app

interface CounterObserver {
    fun incEvent(value: Int) {}
    fun decEvent(value: Int) {}
}


class CounterObservable {
    var value = 0
        private set

    private val observers = mutableListOf<CounterObserver>()


    fun addObserver(o: CounterObserver) = observers.add(o)


    fun removeObserver(o: CounterObserver) = observers.remove(o)


    fun inc() {
        value++
        observers.forEach { obs ->
            obs.incEvent(value)
        }
    }


    fun dec() {
        value--
        observers.forEach { obs ->
            obs.decEvent(value)
        }
    }
}
