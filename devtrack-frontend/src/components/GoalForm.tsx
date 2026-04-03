import { useState } from "react";
import type { CreateGoalData } from "../hooks/useGoals";
interface Props {
  onCreate: (goal: CreateGoalData) => void;
}

export default function GoalForm({ onCreate }: Props) {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [targetDate, setTargetDate] = useState("");

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onCreate({ title, description, targetDate });
    setTitle("");
    setDescription("");
    setTargetDate("");
  };

  return (
    <form onSubmit={handleSubmit}>
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
  );
}
