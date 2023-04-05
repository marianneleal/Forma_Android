import com.example.forma.Habit
import com.example.forma.Repository

class HabitUseCase(private val habitRepository: Repository) {

    suspend fun getAllHabits(): List<Habit> {
        return habitRepository.getAllHabits().value ?: emptyList()
    }

    suspend fun addHabit(habit: Habit) {
        habitRepository.addHabit(habit)
    }


    suspend fun updateHabit(habit: Habit) {
        habitRepository.updateHabit(habit)
    }

    suspend fun deleteHabit(habit: Habit) {
        habitRepository.deleteHabit(habit)
    }

    fun onDestroy() {
        // cleanup code here
    }
}