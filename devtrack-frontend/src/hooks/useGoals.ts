import { useState, useEffect } from "react";
import {
  getGoals,
  createGoal,
  updateGoal,
  deleteGoal,
  completeGoal,
} from "../services/goal";
import type { Goal } from "../services/goal";

export type CreateGoalData = {
  title: string;
  description?: string;
  targetDate: string;
};

export type UpdateGoalData = {
  title: string;
  description?: string;
};

export const useGoals = () => {
  const [goals, setGoals] = useState<Goal[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [loadingGoalId, setLoadingGoalId] = useState<string | null>(null);

  useEffect(() => {
    async function fetch() {
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
    fetch();
  }, []);

  const addGoal = async (goal: CreateGoalData) => {
    setError("");
    try {
      const newGoal = await createGoal(goal);
      setGoals((prev) => [...prev, newGoal]);
    } catch (err) {
      console.error(err);
      setError("Erro ao criar meta");
    }
  };

  const removeGoal = async (id: string) => {
    try {
      await deleteGoal(id);
      setGoals((prev) => prev.filter((g) => g.id !== id));
    } catch (err) {
      console.error(err);
      setError("Erro ao deletar meta");
    }
  };

  const updateExistingGoal = async (id: string, data: UpdateGoalData) => {
    setError("");
    try {
      const updated = await updateGoal(id, data);
      setGoals((prev) =>
        prev.map((g) => (g.id === id ? { ...g, ...updated } : g)),
      );
    } catch (err) {
      console.error(err);
      setError("Erro ao atualizar meta");
    }
  };

  const completeExistingGoal = async (id: string) => {
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
  };

  return {
    goals,
    loading,
    error,
    loadingGoalId,
    addGoal,
    removeGoal,
    updateExistingGoal,
    completeExistingGoal,
  };
};
