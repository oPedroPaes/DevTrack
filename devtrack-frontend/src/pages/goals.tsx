import { useEffect, useState } from "react";
import {
  getGoals,
  createGoal,
  deleteGoal,
  updateGoal,
  completeGoal,
} from "../services/goal";
import type { Goal } from "../services/goal";

import Card from "../components/Card";
import Navbar from "../components/Navbar";

export default function Goals() {
  const [goals, setGoals] = useState<Goal[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [loadingGoalId, setLoadingGoalId] = useState<string | null>(null);
  const [editingGoalId, setEditingGoalId] = useState<string | null>(null);

  //form
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [targetDate, setTargetDate] = useState("");

  //edit form
  const [editTitle, setEditTitle] = useState("");
  const [editDescription, setEditDescription] = useState("");

  useEffect(() => {
    async function fetchGoals() {
      try {
        const data = await getGoals();
        setGoals(data);
      } catch (err) {
        console.error(err);
        setError("Erro ao carregar metas");
      } finally {
        setLoading(false);
      }
    }

    fetchGoals();
  }, []);

  async function handleCreateGoal(e: React.FormEvent) {
    e.preventDefault();
    setError("");

    try {
      const newGoal = await createGoal({
        title,
        description,
        targetDate,
      });

      setGoals((prev) => [...prev, newGoal]);

      setTitle("");
      setDescription("");
      setTargetDate("");
    } catch (err) {
      console.error(err);
      setError("Erro ao criar meta");
    }
  }

  async function handleDeleteGoal(id: string) {
    try {
      await deleteGoal(id);

      setGoals((prev) => prev.filter((goal) => goal.id !== id));
    } catch (err) {
      console.error(err);
      setError("Erro ao deletar meta");
    }
  }
  async function handleUpdateGoal(id: string) {
    if (!editingGoalId) return;
    setError("");

    try {
      const updatedGoal = await updateGoal(id, {
        title: editTitle,
        description: editDescription,
      });

      setGoals((prev) =>
        prev.map((g) => (g.id === id ? { ...g, ...updatedGoal } : g)),
      );

      setEditingGoalId(null);
    } catch (err) {
      console.error(err);
      setError("Erro ao atualizar meta");
    }
  }

  async function handleCompleteGoal(id: string) {
    if (loadingGoalId === id) return;
    setLoadingGoalId(id);
    try {
      const updated = await completeGoal(id);
      setGoals((prev) =>
        prev.map((g) => (g.id === id ? { ...g, status: updated.status } : g)),
      );
    } catch (err) {
      console.error(err);
      setError("Erro ao concluir meta");
    } finally {
      setLoadingGoalId(null);
    }
  }

  if (loading) return <p>Carregando...</p>;

  return (
    <div>
      <Navbar />
      <h1>Metas</h1>

      {error && <p style={{ color: "red" }}>{error}</p>}

      {/* FORM */}
      <form onSubmit={handleCreateGoal}>
        <input
          type="text"
          placeholder="Título"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          required
        />

        <textarea
          placeholder="Descrição"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
        />

        <input
          type="date"
          value={targetDate}
          onChange={(e) => setTargetDate(e.target.value)}
          required
        />

        <button type="submit">Criar meta</button>
      </form>

      <div style={gridStyle}>
        {goals.length === 0 ? (
          <p>Nenhuma meta encontrada</p>
        ) : (
          goals.map((goal) => (
            <Card key={goal.id}>
              {editingGoalId === goal.id ? (
                <>
                  <input
                    value={editTitle}
                    onChange={(e) => setEditTitle(e.target.value)}
                  />
                  <textarea
                    value={editDescription}
                    onChange={(e) => setEditDescription(e.target.value)}
                  />
                  <button onClick={() => handleUpdateGoal(goal.id)}>
                    Salvar
                  </button>
                  <button onClick={() => setEditingGoalId(null)}>
                    Cancelar
                  </button>
                </>
              ) : (
                <>
                  <h3>{goal.title}</h3>
                  <p>{goal.description || "Sem descrição"}</p>
                  <p>
                    Data alvo:{" "}
                    {new Date(goal.targetDate).toLocaleDateString("pt-BR")}
                  </p>
                  <p>Status: {goal.status}</p>

                  <button
                    onClick={() => {
                      setEditingGoalId(goal.id);
                      setEditTitle(goal.title);
                      setEditDescription(goal.description || "");
                    }}
                  >
                    Editar
                  </button>

                  {goal.status === "ATIVO" && (
                    <button
                      disabled={loadingGoalId === goal.id}
                      onClick={() => handleCompleteGoal(goal.id)}
                    >
                      Concluir
                    </button>
                  )}

                  <button
                    style={{ marginTop: "10px", color: "red" }}
                    onClick={() => {
                      if (
                        confirm("Tem certeza que deseja deletar essa meta?")
                      ) {
                        handleDeleteGoal(goal.id);
                      }
                    }}
                  >
                    Deletar
                  </button>
                </>
              )}
            </Card>
          ))
        )}
      </div>
    </div>
  );
}

const gridStyle: React.CSSProperties = {
  display: "grid",
  gridTemplateColumns: "repeat(auto-fit, minmax(250px, 1fr))",
  gap: "20px",
  marginTop: "20px",
};
