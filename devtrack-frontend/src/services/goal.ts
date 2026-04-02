import { apiFetch } from "./api";

export type Goal = {
  id: string;
  title: string;
  description?: string;
  targetDate: string;
  status: string;
};

export async function getGoals(): Promise<Goal[]> {
  const res = await apiFetch("goals");

  if (!res.ok) throw new Error("Erro ao buscar metas");

  return res.json();
}

export async function createGoal(data: {
  title: string;
  description?: string;
  targetDate: string;
}): Promise<Goal> {
  const res = await apiFetch("goals", {
    method: "POST",
    body: JSON.stringify(data),
  });

  if (!res.ok) throw new Error("Erro ao criar meta");

  return res.json();
}

export async function deleteGoal(id: string) {
  const res = await apiFetch(`goals/${id}`, {
    method: "DELETE",
  });

  if (!res.ok) throw new Error("Erro ao deletar meta");
}

export async function updateGoal(
  id: string,
  data: { title: string; description?: string },
): Promise<Goal> {
  const res = await apiFetch(`goals/${id}`, {
    method: "PUT",
    body: JSON.stringify(data),
  });

  if (!res.ok) throw new Error("Erro ao modificar meta");

  return res.json();
}

export async function completeGoal(id: string): Promise<Goal> {
  const res = await apiFetch(`goals/${id}`, {
    method: "PATCH",
    body: JSON.stringify({ status: "COMPLETO" }),
  });

  if (!res.ok) throw new Error("Erro ao concluir meta");

  return res.json();
}
