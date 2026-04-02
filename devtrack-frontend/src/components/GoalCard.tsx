import { useState } from "react";
import type { Goal } from "../services/goal";
import type { UpdateGoalData } from "../hooks/useGoals";
import Card from "./Card";

interface Props {
  goal: Goal;
  onUpdate: (id: string, data: UpdateGoalData) => void;
  onDelete: (id: string) => void;
  onComplete: (id: string) => void;
  loadingGoalId: string | null;
}

export default function GoalCard({
  goal,
  onUpdate,
  onDelete,
  onComplete,
  loadingGoalId,
}: Props) {
  const [editing, setEditing] = useState(false);
  const [editTitle, setEditTitle] = useState(goal.title);
  const [editDescription, setEditDescription] = useState(
    goal.description || "",
  );

  return (
    <Card>
      {editing ? (
        <>
          <input
            value={editTitle}
            onChange={(e) => setEditTitle(e.target.value)}
          />
          <textarea
            value={editDescription}
            onChange={(e) => setEditDescription(e.target.value)}
          />
          <button
            onClick={() => {
              onUpdate(goal.id, {
                title: editTitle,
                description: editDescription,
              });
              setEditing(false);
            }}
          >
            Salvar
          </button>
          <button onClick={() => setEditing(false)}>Cancelar</button>
        </>
      ) : (
        <>
          <h3>{goal.title}</h3>
          <p>{goal.description}</p>
          <p>
            Data alvo: {new Date(goal.targetDate).toLocaleDateString("pt-BR")}
          </p>
          <p>Status: {goal.status}</p>
          <button onClick={() => setEditing(true)}>Editar</button>
          {goal.status === "ATIVO" && (
            <button
              disabled={loadingGoalId === goal.id}
              onClick={() => onComplete(goal.id)}
            >
              Concluir
            </button>
          )}
          <button
            style={{ color: "red" }}
            onClick={() => {
              if (confirm("Tem certeza que deseja deletar esta meta?"))
                onDelete(goal.id);
            }}
          >
            Deletar
          </button>
        </>
      )}
    </Card>
  );
}
