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
    updateExistingGoal,
    completeExistingGoal,
  } = useGoals();

  if (loading) return <p>Carregando...</p>;

  return (
    <div>
      <Navbar />
      <h1>Metas</h1>
      {error && <p style={{ color: "red" }}>{error}</p>}
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
              onUpdate={updateExistingGoal}
              onDelete={removeGoal}
              onComplete={completeExistingGoal}
              loadingGoalId={loadingGoalId}
            />
          ))
        )}
      </div>
    </div>
  );
}
