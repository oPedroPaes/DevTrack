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

  const startEditing = () => {
    setEditTitle(goal.title);
    setEditDescription(goal.description || "");
    setEditing(true);
  };

  const handleSave = async () => {
    //TODO: handle error antes de fechar edit mode pra evitar falso sucesso.
    await onUpdate(goal.id, {
      title: editTitle,
      description: editDescription,
    });
    setEditing(false);
  };

  const handleCancel = () => {
    setEditTitle(goal.title);
    setEditDescription(goal.description || "");
    setEditing(false);
  };

  const handleDelete = () => {
    if (confirm("Tem certeza que deseja deletar esta meta?")) onDelete(goal.id);
  };

  const handleComplete = () => {
    onComplete(goal.id);
  };

  return (
    <Card>
      {editing ? (
        <>
          <input
            value={editTitle}
            onChange={(e) => setEditTitle(e.target.value)}
            placeholder="Título"
          />
          <textarea
            value={editDescription}
            onChange={(e) => setEditDescription(e.target.value)}
            placeholder="Descrição"
          />
          <button onClick={handleSave}>Salvar</button>
          <button onClick={handleCancel}>Cancelar</button>
        </>
      ) : (
        <>
          <h3>{goal.title}</h3>
          <p>{goal.description || "Sem descrição"}</p>
          <p>
            Data alvo: {new Date(goal.targetDate).toLocaleDateString("pt-BR")}
          </p>
          <p>Status: {goal.status}</p>
          <button onClick={startEditing}>Editar</button>
          {goal.status === "ATIVO" && (
            <button
              disabled={loadingGoalId === goal.id}
              onClick={handleComplete}
            >
              Concluir
            </button>
          )}
          <button style={{ color: "red" }} onClick={handleDelete}>
            Deletar
          </button>
        </>
      )}
    </Card>
  );
}
