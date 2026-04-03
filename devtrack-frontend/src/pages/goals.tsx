import Navbar from "../components/Navbar";
import GoalForm from "../components/GoalForm";
import GoalCard from "../components/GoalCard";
import { useGoals } from "../hooks/useGoals";

export default function Goals() {
  const {
    goals,
    loading,
    error,
    loadingGoalId,
    addGoal,
    removeGoal,
    updateGoal,
    completeGoal,
  } = useGoals();

  return (
    <div>
      <Navbar />
      <h1>Metas</h1>
      {error && <p style={{ color: "red" }}>{error}</p>}
      {loading ? (
        <p>Carregando...</p>
      ) : (
        <>
          <GoalForm onCreate={addGoal} />
          <div
            style={{
              display: "grid",
              gridTemplateColumns: "repeat(auto-fit, minmax(250px, 1fr))",
              gap: 20,
              marginTop: 20,
            }}
          >
            {goals.length === 0 ? (
              <p>Nenhuma meta encontrada</p>
            ) : (
              goals.map((goal) => (
                <GoalCard
                  key={goal.id}
                  goal={goal}
                  onUpdate={updateGoal}
                  onDelete={removeGoal}
                  onComplete={completeGoal}
                  loadingGoalId={loadingGoalId}
                />
              ))
            )}
          </div>
        </>
      )}
    </div>
  );
}
