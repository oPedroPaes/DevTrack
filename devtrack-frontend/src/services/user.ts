import { apiFetch } from "./api";

export type User = {
  id: string; // temporário
  name: string;
  email: string;
};

export async function getUserData(): Promise<User> {
  const response = await apiFetch("users/me");

  if (!response.ok) {
    throw new Error("Erro ao buscar usuário");
  }

  const data = await response.json();
  return data;
}
